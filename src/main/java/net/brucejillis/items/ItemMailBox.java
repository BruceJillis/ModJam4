package net.brucejillis.items;

import cpw.mods.fml.common.FMLCommonHandler;
import net.brucejillis.MailboxMod;
import net.brucejillis.events.MailBoxPlacedEvent;
import net.brucejillis.tileentities.TileEntityMailbox;
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
                // base block
                world.setBlock(x, y + 1, z, MailboxMod.blockMailbox, 1, 3);
                // extension
                world.setBlock(x, y + 2, z, MailboxMod.blockMailbox, 2, 3);
                // set base position
                TileEntityMailbox te = (TileEntityMailbox)world.getTileEntity(x, y + 2, z);
                te.setBasePosition(x, y + 1, z);
                // use the item
                --stack.stackSize;
                // publish mailbox to scheduler
                FMLCommonHandler.instance().bus().post(new MailBoxPlacedEvent(x, y + 1, z));
                return true;
            }
        }
        return false;
    }
}
