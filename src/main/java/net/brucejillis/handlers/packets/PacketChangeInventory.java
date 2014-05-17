package net.brucejillis.handlers.packets;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.brucejillis.MailboxMod;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import java.io.IOException;

public class PacketChangeInventory {
    private static final int PACKET_WRITE_LETTER = 1;

    public static void processPacketOnClientSide(ByteBuf parBB, Side parSide) throws IOException {
        if (parSide == Side.CLIENT) {
            System.out.println("Received Packet Client Side");
            @SuppressWarnings("unused") World theWorld = Minecraft.getMinecraft().theWorld;
            ByteBufInputStream bbis = new ByteBufInputStream(parBB);
            int packetTypeID = bbis.readByte();
            switch (packetTypeID) {
                case 1: {
                    byte data = bbis.readByte();
                    if (!theWorld.isRemote)
                        System.out.println("Server recived:" + data);
                    else
                        System.out.println("Client recived:" + data);
                    break;
                }
            }
            bbis.close();
        }
    }

    public static void processPacketOnServerSide(ByteBuf payload, Side parSide) throws IOException {
        if (parSide == Side.SERVER) {
            // DEBUG
            System.out.println("Received Packet Server Side");

            World theWorld = Minecraft.getMinecraft().theWorld;
            ByteBufInputStream bbis = new ByteBufInputStream(payload);

            // process data stream
            // first read packet type
            int packetTypeID = bbis.readByte();

            switch (packetTypeID) {
                case 1: {
                    byte buttonId = bbis.readByte();
                    int playerId = bbis.readInt();
                    EntityPlayer player = (EntityPlayer) theWorld.getEntityByID(playerId);
                    Container container = player.openContainer;
                    System.out.println("Server recived:" + player.getCurrentEquippedItem().getItem());
                    break;
                }
            }
            bbis.close();
        }
    }

    public static FMLProxyPacket createWriteLetterPacket(EntityPlayer player, String displayName, String text) {
        ByteBufOutputStream dataStream = new ByteBufOutputStream(Unpooled.buffer());
        try {
            dataStream.writeByte(PACKET_WRITE_LETTER);
            dataStream.writeUTF(displayName);
            dataStream.writeUTF(text);
            dataStream.writeInt(player.getEntityId());
            FMLProxyPacket packet = new FMLProxyPacket(dataStream.buffer(), MailboxMod.CHANNEL);
            dataStream.close();
            return packet;
        } catch (IOException e) {
            LogHelper.error(e.getMessage());
        }
        return null;
    }
}
