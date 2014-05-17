package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.List;

public class ItemUnwrittenLetter extends Item {
    public ItemUnwrittenLetter() {
        setUnlocalizedName("itemUnwrittenLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setCreativeTab(MailboxMod.mailboxTab);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!player.isSneaking() && world.isRemote) {
            player.openGui(MailboxMod.instance, MailboxMod.GUI_LETTER, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4) {
        par3List.add("Unwritten / Unsent");
    }
}
