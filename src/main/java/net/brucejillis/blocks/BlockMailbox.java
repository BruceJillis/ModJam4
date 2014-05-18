package net.brucejillis.blocks;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.brucejillis.MailboxMod;
import net.brucejillis.events.MailBoxPlacedEvent;
import net.brucejillis.events.MailBoxRemovedEvent;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockMailbox extends BlockContainer {

    public BlockMailbox() {
        super(Material.wood);
        setBlockName("blockMailbox");
        setBlockTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setStepSound(soundTypeWood);
        setHardness(0.8f);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        if (player.isSneaking() || world.isRemote) {
            return false;
        }
        TileEntity te = world.getTileEntity(x, y, z);
        if ((te != null) && (te instanceof TileEntityMailbox)) {
            world.playSoundEffect((double) te.xCoord + 0.5D, (double) te.yCoord + 0.5D, (double) te.zCoord + 0.5D, MailboxMod.ID + ":mailbox_open", 1.0f, 1.0f);
            TileEntityMailbox entity = (TileEntityMailbox)te;
            if (entity.getPrimaryEntity(world).getName().equals("")) {
                // open the mailbox's naming gui
                player.openGui(MailboxMod.instance, MailboxMod.GUI_NAME_MAILBOX, world, x, y, z);
            } else {
                // open the mailbox's gui
                player.openGui(MailboxMod.instance, MailboxMod.GUI_MAILBOX, world, x, y, z);
            }
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity == null || !(entity instanceof TileEntityMailbox)) {
            return;
        }
        // drop inventory
        Random rand = new Random();
        float factor = 0.05F;
        IInventory inventory = (IInventory) entity;
        for (int i = 0; i < inventory.getSizeInventory(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (stack != null && stack.stackSize > 0) {
                float rx = rand.nextFloat() * 0.8F + 0.1F;
                float ry = rand.nextFloat() * 0.8F + 0.1F;
                float rz = rand.nextFloat() * 0.8F + 0.1F;
                EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(stack.getItem(), stack.stackSize, stack.getItemDamage()));
                if (stack.hasTagCompound()) {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) stack.getTagCompound().copy());
                }
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                stack.stackSize = 0;
            }
        }
        super.breakBlock(world, x, y, z, block, metadata);
    }


    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
        // erase extra block if the player harvest the primary (base)block
        if (isMailboxBase(metadata)) {
            if (world.getBlock(x, y, z) == this) {
                world.setBlockToAir(x, y + 1, z);
            }
        } else {
            if (world.getBlock(x, y, z) == this) {
                world.setBlockToAir(x, y - 1, z);
            }
        }
        // remove mailbox address
        TileEntityMailbox te = (TileEntityMailbox)world.getTileEntity(x, y, z);
        te = te.getPrimaryEntity(world);
        if (te != null && !te.getName().equals(""))
            FMLCommonHandler.instance().bus().post(new MailBoxRemovedEvent(te.getName()));
    }

    public static boolean isMailboxBase(int metadata) {
        return metadata == 1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityMailbox();
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World world, int x, int y, int z) {
        return MailboxMod.itemMailbox;
    }
}
