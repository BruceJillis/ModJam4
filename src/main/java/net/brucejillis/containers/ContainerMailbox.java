package net.brucejillis.containers;

import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMailbox extends Container {
    private final TileEntityMailbox entity;

    public ContainerMailbox(InventoryPlayer inventory, TileEntityMailbox entity) {
        this.entity = entity;
        // create and bind slots for mailbox queue
        for(int i = 0; i < 6; i++) {
            addSlotToContainer(new Slot(entity, i, 9 + (i * 18), 17));
        }
        // bind inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 9 + j * 18, 54 + i * 18));
            }
        }
        // bind hotbar
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventory, i, 9 + i * 18, 112));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
        ItemStack stack = null;
        Slot slotObject = (Slot) inventorySlots.get(slot);
        // null checks and checks if the item can be stacked (maxStackSize > 1)
        if (slotObject != null && slotObject.getHasStack()) {
            ItemStack stackInSlot = slotObject.getStack();
            stack = stackInSlot.copy();

            // merges the item into player inventory since its in the tileEntity
            if (slot < 9) {
                if (!this.mergeItemStack(stackInSlot, 0, 35, true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(stackInSlot, 0, 9, false)) {
                // places it into the tileEntity is possible since its in the player inventory
                return null;
            }
            if (stackInSlot.stackSize == 0) {
                slotObject.putStack(null);
            } else {
                slotObject.onSlotChanged();
            }
            if (stackInSlot.stackSize == stack.stackSize) {
                return null;
            }
            slotObject.onPickupFromSlot(player, stackInSlot);
        }
        return stack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return entity.isUseableByPlayer(player);
    }
}
