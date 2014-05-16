package net.brucejillis.containers;

import net.brucejillis.containers.slots.MailboxSlot;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMailbox extends Container {
    private final TileEntityMailbox entity;
    private final InventoryPlayer inventory;

    public ContainerMailbox(InventoryPlayer inventory, TileEntityMailbox entity) {
        this.entity = entity;
        this.inventory = inventory;
        // create and bind slots for mailbox queue
        for (int i = 0; i < 6; i++) {
            addSlotToContainer(new MailboxSlot(entity, i, 9 + (i * 18), 17));
        }
        bindPlayerInventory(inventory, 9, 54, 9, 112);

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
        return entity.isUseableByPlayer(player);
    }
}
