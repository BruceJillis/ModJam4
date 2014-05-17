package net.brucejillis.handlers.packets;

import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.brucejillis.MailboxMod;
import net.brucejillis.items.ItemWrittenLetter;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.io.IOException;

public class PacketChangeInventory {
    private static final int PACKET_WRITE_LETTER = 1;

    public static void processPacketOnClientSide(ByteBuf parBB, Side parSide) throws IOException {
    }

    public static void processPacketOnServerSide(World world, EntityPlayer player, ByteBuf payload) throws IOException {
        ByteBufInputStream bbis = new ByteBufInputStream(payload);
        switch (bbis.readByte()) {
            case PACKET_WRITE_LETTER: {
                String text = bbis.readUTF();
                // handle packet for real
                player.inventory.consumeInventoryItem(player.inventory.getCurrentItem().getItem());
                ItemStack letter = new ItemStack(MailboxMod.itemWrittenLetter, 1, 0);
                NBTTagCompound tag = ItemWrittenLetter.ensureTagCompound(letter);
                tag.setString("Sender", player.getDisplayName());
                tag.setString("Text", text);
                player.inventory.addItemStackToInventory(letter);
                player.inventory.markDirty();
                break;
            }
        }
        bbis.close();
    }

    public static FMLProxyPacket createWriteLetterPacket(EntityPlayer player, String text) {
        ByteBufOutputStream dataStream = new ByteBufOutputStream(Unpooled.buffer());
        try {
            dataStream.writeByte(PACKET_WRITE_LETTER);
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
