package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.brucejillis.handlers.packets.PacketManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ItemWrittenLetter extends Item {
    public ItemWrittenLetter() {
        setUnlocalizedName("itemWrittenLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.isSneaking() && world.isRemote) {
            player.openGui(MailboxMod.instance, MailboxMod.GUI_READ_LETTER, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
            MailboxMod.channel.sendToServer(PacketManager.createOpenGuiPacket(MailboxMod.GUI_READ_LETTER));
        }
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        NBTTagCompound tag = ensureTagCompound(stack);
        String sender = tag.getString("Sender");
        String to = tag.getString("To");
        String sent = tag.getString("Sent");
        if (sent == null || sent.equals("")) {
            sent = "Unsent";
        } else {
            sent = String.format("Sent (reply: %s)", sent);
        }
        NBTTagList pages = tag.getTagList("Pages", Constants.NBT.TAG_STRING);
        if (pages.tagCount() == 1)
            list.add(String.format("From %s To %s (1 page) / %s", sender, to, sent));
        else
            list.add(String.format("From %s To %s (%d pages) / %s", sender, to, pages.tagCount(), sent));
        String sneak = pages.getStringTagAt(0).split("\n")[0];
        list.add(StringUtils.abbreviate(sneak, 16));
    }

    public static NBTTagCompound ensureTagCompound(ItemStack stack) {
        if (stack.stackTagCompound == null)
            stack.setTagCompound(new NBTTagCompound());
        return stack.stackTagCompound;
    }
}
