package net.brucejillis.handlers.packets;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.internal.FMLProxyPacket;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import net.brucejillis.MailboxMod;
import net.brucejillis.events.MailBoxPlacedEvent;
import net.brucejillis.handlers.data.MailboxDeliveryData;
import net.brucejillis.items.ItemWrittenLetter;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

import java.io.IOException;

// i'm sorry, new stuff is new
public class PacketManager {
    private static final int PACKET_WRITE_LETTER = 1;
    private static final int PACKET_NAME_MAILBOX = 2;

    public static void processPacketOnClientSide(ByteBuf parBB, Side parSide) throws IOException {
    }

    public static void processPacketOnServerSide(World world, EntityPlayer player, ByteBuf payload) throws IOException {
        ByteBufInputStream stream = new ByteBufInputStream(payload);
        switch (stream.readByte()) {
            case PACKET_WRITE_LETTER:
                NBTTagCompound pages = ByteBufUtils.readTag(payload);
                // handle packet for real
                player.inventory.consumeInventoryItem(player.inventory.getCurrentItem().getItem());
                ItemStack letter = new ItemStack(MailboxMod.itemWrittenLetter, 1, 0);
                NBTTagCompound tag = ItemWrittenLetter.ensureTagCompound(letter);
                tag.setString("Sender", player.getDisplayName());
                tag.setString("To", pages.getString("To"));
                tag.setTag("Pages", pages.getTagList("Pages", Constants.NBT.TAG_STRING));
                player.inventory.addItemStackToInventory(letter);
                player.inventory.markDirty();
                break;
            case PACKET_NAME_MAILBOX:
                int x = stream.readInt();
                int y = stream.readInt();
                int z = stream.readInt();
                String name = stream.readUTF();
                TileEntity te = world.getTileEntity(x, y, z);
                if ((te != null) && (te instanceof TileEntityMailbox) && MailboxDeliveryData.isNameFree(name)) {
                    TileEntityMailbox entity = (TileEntityMailbox)te;
                    entity.setName(name);
                    FMLCommonHandler.instance().bus().post(new MailBoxPlacedEvent(name, x, y, z));
                } else {
                    // todo: name was taken
                }
                world.markBlockForUpdate(x, y, z);
                break;
        }
        stream.close();
    }

    public static FMLProxyPacket createWriteLetterPacket(EntityPlayer player, NBTTagCompound pages) {
        ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
        try {
            stream.writeByte(PACKET_WRITE_LETTER);
            ByteBufUtils.writeTag(stream.buffer(), pages);
            FMLProxyPacket packet = new FMLProxyPacket(stream.buffer(), MailboxMod.CHANNEL);
            stream.close();
            return packet;
        } catch (IOException e) {
            LogHelper.error(e.getMessage());
        }
        return null;
    }

    public static FMLProxyPacket createNameMailboxPacket(TileEntityMailbox entity, String text) {
        ByteBufOutputStream stream = new ByteBufOutputStream(Unpooled.buffer());
        try {
            stream.writeByte(PACKET_NAME_MAILBOX);
            stream.writeInt(entity.xCoord);
            stream.writeInt(entity.yCoord);
            stream.writeInt(entity.zCoord);
            stream.writeUTF(text);
            FMLProxyPacket packet = new FMLProxyPacket(stream.buffer(), MailboxMod.CHANNEL);
            stream.close();
            return packet;
        } catch (IOException e) {
            LogHelper.error(e.getMessage());
        }
        return null;
    }
}
