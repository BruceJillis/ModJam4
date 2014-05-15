package net.brucejillis.containers;

import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class MailboxContainer extends Container {
    private final InventoryPlayer inventory;
    private final TileEntityMailbox entity;

    public MailboxContainer(InventoryPlayer inventory, TileEntityMailbox entity) {
        this.inventory = inventory;
        this.entity = entity;
        // create and bind slots for mailbox queue
        for(int i = 0; i < 6; i++) {
            addSlotToContainer(new Slot(entity, i,  42 + i * 16, 16));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return entity.isUseableByPlayer(player);
    }
}
