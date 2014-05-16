package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMailBox extends Item {
    public ItemMailBox() {
        setCreativeTab(MailboxMod.mailboxTab);
        setUnlocalizedName("itemMailbox");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int metadata, float par8, float par9, float par10) {
        return false;
    }
}
