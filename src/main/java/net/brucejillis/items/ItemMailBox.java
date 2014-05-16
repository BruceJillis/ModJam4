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
        if (world.isRemote) {
            return true;
        }
        if (player.canPlayerEdit(x, y, z, metadata, stack)) {
            if (world.isAirBlock(x, y + 1, z) && World.doesBlockHaveSolidTopSurface(world, x, y, z)) {
                world.setBlock(x, y + 1, z, MailboxMod.blockMailbox, 1, 3);
                world.setBlock(x, y + 2, z, MailboxMod.blockMailbox, 2, 3);
                --stack.stackSize;
                return true;
            }
        }
        return false;
    }
}
