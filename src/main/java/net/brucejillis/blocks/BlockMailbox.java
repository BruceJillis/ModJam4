package net.brucejillis.blocks;

import net.brucejillis.MailboxMod;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
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
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z) {
        setBlockBounds(0.0f, 0.0f, 0.0f, 1.0f, 1.65f, 1.0f);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
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
