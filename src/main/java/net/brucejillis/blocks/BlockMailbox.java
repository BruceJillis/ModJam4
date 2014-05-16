package net.brucejillis.blocks;

import net.brucejillis.MailboxMod;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
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
        //setBlockTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        //setCreativeTab(MailboxMod.mailboxTab);
        setStepSound(soundTypeWood);
        setHardness(0.8f);
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List pool, Entity entity) {
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.925f, 1.0f);
        super.addCollisionBoxesToList(world, x, y, z, aabb, pool, entity);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
        TileEntity te = world.getTileEntity(x, y, z);
        if (te == null || player.isSneaking()) {
            return false;
        }
        player.openGui(MailboxMod.instance, MailboxMod.GUI_MAILBOX, world, x, y, z);
        return true;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int metadata) {
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity == null || !(entity instanceof TileEntityMailbox)) {
            return;
        }
        Random rand = new Random();
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
                float factor = 0.05F;
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
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
        int metadata = world.getBlockMetadata(x, y, z);
        if (isMailboxBase(metadata)) {

        } else {

        }
    }

    public static boolean isMailboxBase(int metadata) {
        if (metadata == 1) {
            return true;
        }
        return false;
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int metadata, EntityPlayer player) {
        // erase extra block if the player harvest the primary (base)block
        if (isMailboxBase(metadata)) {
            if (world.getBlock(x, y, z) == this) {
                world.setBlockToAir(x, y + 1, z);
            }
        }
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        if (isMailboxBase(access.getBlockMetadata(x, y, z))) {
            setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.65f, 1.0f);
        } else {
            setBlockBounds(0.0f, -1.0f, 0.0f, 1.0f, 0.65f, 1.0f);
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityMailbox();
    }

    @Override
    public int getMobilityFlag() {
        return 1;
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
}
