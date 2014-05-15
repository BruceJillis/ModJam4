package net.brucejillis.containers;

import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;

public class MailboxContainer extends Container {
    private final InventoryPlayer inventory;
    private final TileEntityMailbox entity;

    public MailboxContainer(InventoryPlayer inventory, TileEntityMailbox entity) {
        this.inventory = inventory;
        this.entity = entity;
        for(int i = 0; i < 6; i++) {

        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return entity.isUseableByPlayer(player);
    }
}
