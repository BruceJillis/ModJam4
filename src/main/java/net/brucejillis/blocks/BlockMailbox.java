package net.brucejillis.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import sun.org.mozilla.javascript.internal.ast.Block;

public class BlockMailbox extends BlockContainer {

    protected BlockMailbox() {
        super(Material.rock);
    }

    @Override
    public TileEntity createNewTileEntity(World var1, int var2) {
        return new TileEntityMailbox();
    }
}
