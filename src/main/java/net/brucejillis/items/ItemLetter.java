package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemLetter extends Item {
    public ItemLetter() {
        setUnlocalizedName("itemLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5) + "_blank");
        setCreativeTab(MailboxMod.mailboxTab);
        setMaxStackSize(16);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4) {
        if (stack.stackTagCompound == null)
            stack.setTagCompound(new NBTTagCompound());
        NBTTagCompound tag = stack.stackTagCompound;
        if (tag.hasKey("Sender"))
            par3List.add("Sender: " + tag.getString("Sender"));
        else
            par3List.add("Unwritten");
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.openGui(MailboxMod.instance, MailboxMod.GUI_LETTER, world, (int)player.posX, (int)player.posY, (int)player.posZ);
        return stack;
    }
}
