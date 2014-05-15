package net.brucejillis.blocks;

import net.brucejillis.MailboxMod;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import sun.org.mozilla.javascript.internal.ast.Block;

import java.util.List;

public class BlockMailbox extends BlockContainer {

    public BlockMailbox() {
        super(Material.wood);
        setBlockName("blockMailbox");
        setBlockTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setCreativeTab(MailboxMod.mailboxTab);
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
        if (te == null) {
            return false;
        }
        player.openGui(MailboxMod.instance, MailboxMod.GUI_MAILBOX, world, x, y, z);
        return true;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.65f, 1.0f);
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
}
