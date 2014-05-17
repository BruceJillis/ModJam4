package net.brucejillis.handlers.packets;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.world.World;

import java.io.IOException;

public class PacketChangeInventory {
    public static FMLProxyPacket createButtonPacket(byte id, EntityPlayer player) throws IOException {
        ByteBufOutputStream dataStream = new ByteBufOutputStream(Unpooled.buffer());
        int playerId = player.getEntityId();

        dataStream.writeByte(1);
        dataStream.writeByte(id);
        dataStream.writeInt(playerId);

        FMLProxyPacket thePacket = new FMLProxyPacket(dataStream.buffer(), Tolkienaddon.networkChannelName);
        dataStream.close();
        return thePacket;
    }

    public static void processPacketOnClientSide(ByteBuf parBB, Side parSide) throws IOException {
        if (parSide == Side.CLIENT) {
            // DEBUG
            System.out.println("Received Packet Client Side");

            @SuppressWarnings("unused") World theWorld = Minecraft.getMinecraft().theWorld;
            ByteBufInputStream bbis = new ByteBufInputStream(parBB);

            // process data stream
            // first read packet type
            int packetTypeID = bbis.readByte();

            switch (packetTypeID) {
                case 1: {
                    byte data = bbis.readByte();

                    if (!theWorld.isRemote) System.out.println("Server recived:" + data);
                    else System.out.println("Client recived:" + data);

                    break;
                }

            }

            // don't forget to close stream to avoid memory leak
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
                    System.out.println("Server recived:" + player.getCurrentEquippedItem().getItem().getUnlocalizedName());
                    break;
                }
            }
            bbis.close();
        }
    }
}
}
