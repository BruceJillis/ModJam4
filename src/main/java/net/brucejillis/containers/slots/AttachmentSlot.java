package net.brucejillis.containers.slots;

import net.brucejillis.items.ItemUnwrittenLetter;
import net.brucejillis.items.ItemWrittenLetter;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AttachmentSlot extends Slot {
    public AttachmentSlot(IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return !(stack.getItem() instanceof ItemUnwrittenLetter);
    }

    @Override
    public int getSlotStackLimit() {
        return 64;
    }
}
