package net.brucejillis.containers;

import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerMailbox extends Container {
    private final TileEntityMailbox entity;

    public ContainerMailbox(InventoryPlayer inventory, TileEntityMailbox entity) {
        this.entity = entity;
        // create and bind slots for mailbox queue
        for(int i = 0; i < 6; i++) {
            addSlotToContainer(new Slot(entity, i, 32 + (i * 18), 10));
        }
        // bind inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 17 + j * 18, 53 + i * 18));
            }
        }
        // bind hotbar
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 111));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return entity.isUseableByPlayer(player);
    }
}
