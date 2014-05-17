package net.brucejillis.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.brucejillis.MailboxMod;
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
        if (player.isSneaking()) {
            return false;
        }
        TileEntity te = world.getTileEntity(x, y, z);
        if ((te != null) && (te instanceof TileEntityMailbox)) {
            world.playSoundEffect((double) te.xCoord + 0.5D, (double) te.yCoord + 0.5D, (double) te.zCoord + 0.5D, MailboxMod.ID + ":mailbox_open", 1.0f, 1.0f);
            // open the mailbox's gui
            player.openGui(MailboxMod.instance, MailboxMod.GUI_MAILBOX, world, x, y, z);
        }
        return true;
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

//    @Override
//    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List pool, Entity entity) {
//        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.925f, 1.0f);
//        super.addCollisionBoxesToList(world, x, y, z, aabb, pool, entity);
//    }
//
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
//        float f = 0.0625F;
//        return AxisAlignedBB.getAABBPool().getAABB((double) ((float) p_149668_2_ + f), (double) p_149668_3_, (double) ((float) p_149668_4_ + f), (double) ((float) (p_149668_2_ + 1) - f), (double) ((float) (p_149668_3_ + 1) - f), (double) ((float) (p_149668_4_ + 1) - f));
//    }
//
//    /**
//     * Returns the bounding box of the wired rectangular prism to render.
//     */
//    @SideOnly(Side.CLIENT)
//    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_) {
//        float f = 0.0625F;
//        return AxisAlignedBB.getAABBPool().getAABB((double) ((float) p_149633_2_ + f), (double) p_149633_3_, (double) ((float) p_149633_4_ + f), (double) ((float) (p_149633_2_ + 1) - f), (double) (p_149633_3_ + 1), (double) ((float) (p_149633_4_ + 1) - f));
//    }
//

//

//
//    public static boolean isMailboxBase(int metadata) {
//        if (metadata == 1) {
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
//        // erase extra block if the player harvest the primary (base)block
//        if (isMailboxBase(metadata)) {
//            if (world.getBlock(x, y, z) == this) {
//                world.setBlockToAir(x, y + 1, z);
//            }
//        } else {
//            if (world.getBlock(x, y, z) == this) {
//                world.setBlockToAir(x, y - 1, z);
//            }
//        }
//    }
//
//    @Override
    //    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
//        if (isMailboxBase(access.getBlockMetadata(x, y, z))) {
//            //setBlockBounds(0.1f, 0.1f, 0.1f, 0.9f, 0.9f, 0.9f);
//            setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.65f, 1.0f);
//        } else {
//            //setBlockBounds(0.1f, 0.1f, 0.1f, 0.9f, 0.9f, 0.9f);
//            setBlockBounds(0.0f, -1.0f, 0.0f, 1.0f, 0.65f, 1.0f);
//        }
//    }
//

//
//    @Override
//    public int getMobilityFlag() {
//        return 1;
//    }
//
}
