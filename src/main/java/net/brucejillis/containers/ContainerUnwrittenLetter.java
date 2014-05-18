package net.brucejillis.containers;

import net.brucejillis.containers.inventories.UnwrittenLetterInventory;
import net.brucejillis.containers.slots.AttachmentSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerUnwrittenLetter extends Container {
    private UnwrittenLetterInventory inventory;

    private static final int INV_START = UnwrittenLetterInventory.INV_SIZE, INV_END = INV_START + 26, HOTBAR_START = INV_END + 1, HOTBAR_END = HOTBAR_START + 8;

    public ContainerUnwrittenLetter(EntityPlayer player, InventoryPlayer inventoryPlayer, UnwrittenLetterInventory inventoryItem) {
        super();
        this.inventory = inventoryItem;
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

            // If item is in our custom Inventory or armor slot
            if (par2 < INV_START) {
                // try to place in player inventory / action bar
                if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            // Item is in inventory / hotbar, try to place in custom inventory or armor slots
            else {
                /* If your inventory only stores certain instances of Items,
                * you can implement shift-clicking to your inventory like this:

                // Check that the item is the right type

//                if (itemstack1.getItem() instanceof ItemCustom) {
//                    // Try to merge into your custom inventory slots
//                    // We use 'InventoryItem.INV_SIZE' instead of INV_START just in case
//                    // you also add armor or other custom slots
//                    if (!this.mergeItemStack(itemstack1, 0, InventoryItem.INV_SIZE, false)) {
//                        return null;
//                    }
//                }

                // If you added armor slots, check them here as well:
                // Item being shift-clicked is armor - try to put in armor slot

//                if (itemstack1.getItem() instanceof ItemArmor) {
//                    int type = ((ItemArmor) itemstack1.getItem()).armorType;
//                    if (!this.mergeItemStack(itemstack1, ARMOR_START + type, ARMOR_START + type + 1, false)) {
//                        return null;
//                    }
//                }


                * Otherwise, you have basically 2 choices:
                * 1. shift-clicking between action bar and inventory
                * 2. shift-clicking between player inventory and custom inventory
                * I've implemented number 1:
                */

                // item is in player's inventory, but not in action bar
                if (par2 >= INV_START && par2 < HOTBAR_START) {
                    // place in action bar
                    if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
                        return null;
                    }
                }
                // item in action bar - place in player inventory
                else if (par2 >= HOTBAR_START && par2 < HOTBAR_END + 1) {
                    if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
                        return null;
                    }
                }
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
//        ItemStack itemstack = null;
//        Slot slot = (Slot) this.inventorySlots.get(par2);
//        if (slot != null && slot.getHasStack()) {
//            ItemStack itemstack1 = slot.getStack();
//            itemstack = itemstack1.copy();
//            if (par2 < inventory.getSizeInventory()) {
//                if (!this.mergeItemStack(itemstack1, inventory.getSizeInventory(), this.inventorySlots.size(), true)) {
//                    return null;
//                }
//            } else if (!this.mergeItemStack(itemstack1, 0, inventory.getSizeInventory(), false)) {
//                return null;
//            }
//            if (itemstack1.stackSize == 0) {
//                slot.putStack((ItemStack) null);
//            } else {
//                slot.onSlotChanged();
//            }
//        }
//        return itemstack;
    }

    // NOTE: The following is necessary if you want to prevent the player from moving the item while the
    // inventory is open; if you don't and the player moves the item, the inventory will not be able to save properly
    @Override
    public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
        // this will prevent the player from interacting with the item that opened the inventory:
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItem()) {
            return null;
        }
        return super.slotClick(slot, button, flag, player);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return inventory.isUseableByPlayer(player);
    }
}
