package net.brucejillis.containers;

import net.brucejillis.containers.inventories.UnwrittenLetterInventory;
import net.brucejillis.containers.slots.AttachmentSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLetter extends Container {
    private IInventory inventory;

    public ContainerLetter(InventoryPlayer inventoryPlayer, IInventory inventory, ItemStack item) {
        super();
        this.inventory = inventory;
        // bind attachment slots
        for (int i = 0; i < 4; i++) {
            addSlotToContainer(new AttachmentSlot(inventory, i, 9, 28 + (i * 18)));
        }
        bindPlayerInventory(inventoryPlayer, 9, 172, 9, 230);
    }

    private void bindPlayerInventory(InventoryPlayer inventory, int xOffInv, int yOffInv, int xOffHotbar, int yOffHotbar) {
        // bind inventory
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, xOffInv + j * 18, yOffInv + i * 18));
            }
        }
        // bind hotbar
        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventory, i, xOffHotbar + i * 18, yOffHotbar));
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            if (par2 < inventory.getSizeInventory()) {
                if (!this.mergeItemStack(itemstack1, inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 0, inventory.getSizeInventory(), false)) {
                return null;
            }
            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemstack;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }
}
