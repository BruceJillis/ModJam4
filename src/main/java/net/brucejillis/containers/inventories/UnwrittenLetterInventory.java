package net.brucejillis.containers.inventories;

import net.brucejillis.items.ItemUnwrittenLetter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

public class UnwrittenLetterInventory implements IInventory {
    public static final int INV_SIZE = 4;
    private ItemStack inventoryItem;
    private ItemStack[] inventory;
    private boolean dirty = false;

    public UnwrittenLetterInventory(ItemStack itemStack) {
        this.inventoryItem = itemStack;
        this.inventory = new ItemStack[INV_SIZE];
        NBTTagCompound tag = ItemUnwrittenLetter.ensureTagCompound(itemStack);
        readFromNBT(tag);
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
//        ItemStack stack = getStackInSlot(i);
//        if (stack != null) {
//            if (stack.stackSize <= amount) {
//                setInventorySlotContents(i, null);
//            } else {
//                stack = stack.splitStack(amount);
//                if (stack.stackSize == 0) {
//                    setInventorySlotContents(i, null);
//                }
//            }
//        }
//        return stack;
        ItemStack stack = getStackInSlot(i);
        if (stack != null) {
            if (stack.stackSize > amount) {
                stack = stack.splitStack(amount);
                markDirty();
            } else {
                setInventorySlotContents(i, null);
            }
        }
        return stack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        ItemStack result = getStackInSlot(i);
        if (result != null) {
            setInventorySlotContents(i, null);
        }
        return result;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
//        inventory[i] = itemStack;
//        if ((itemStack != null) && (itemStack.stackSize > getInventoryStackLimit())) {
//            itemStack.stackSize = getInventoryStackLimit();
//        }
        this.inventory[i] = itemStack;
        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }
        markDirty();
    }

    @Override
    public String getInventoryName() {
        return "UnwrittenLetter";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        for (int i = 0; i < getSizeInventory(); ++i) {
            if (getStackInSlot(i) != null && getStackInSlot(i).stackSize == 0) inventory[i] = null;
        }
        // be sure to write to NBT when the inventory changes!
        writeToNBT(inventoryItem.getTagCompound());
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return player.getHeldItem() == inventoryItem;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return !(itemStack.getItem() instanceof ItemUnwrittenLetter);
    }

    public void readFromNBT(NBTTagCompound tag) {
        // Gets the custom taglist we wrote to this compound, if any
        NBTTagList items = tag.getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < items.tagCount(); ++i) {
            NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
            byte slot = item.getByte("Slot");
            // Just double-checking that the saved slot index is within our inventory array bounds
            if (slot >= 0 && slot < getSizeInventory()) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(item);
            }
        }
// /        // read inventory from nbt
//        NBTTagList tags = tag.getTagList("inventory", Constants.NBT.TAG_COMPOUND);
//        for (int i = 0; i < tags.tagCount(); i++) {
//            NBTTagCompound compound = tags.getCompoundTagAt(i);
//            short slot = compound.getShort("slot");
//            if ((slot >= 0) && (slot < inventory.length)) {
//                inventory[slot] = ItemStack.loadItemStackFromNBT(compound);
//            }
//        }
    }

    public void writeToNBT(NBTTagCompound tag) {
//        // write inventory to nbt
//        NBTTagList list = new NBTTagList();
//        for (int i = 0; i < inventory.length; i++) {
//            ItemStack stack = inventory[i];
//            if (stack != null) {
//                NBTTagCompound compound = new NBTTagCompound();
//                compound.setShort("slot", (short) i);
//                stack.writeToNBT(compound);
//                list.appendTag(compound);
//            }
//        }
//        tag.setTag("inventory", list);
        // Create a new NBT Tag List to store itemstacks as NBT Tags
        NBTTagList items = new NBTTagList();
        for (int i = 0; i < getSizeInventory(); ++i) {
            // Only write stacks that contain items
            if (getStackInSlot(i) != null) {
                // Make a new NBT Tag Compound to write the itemstack and slot index to
                NBTTagCompound item = new NBTTagCompound();
                item.setInteger("Slot", i);
                // Writes the itemstack in slot(i) to the Tag Compound we just made
                getStackInSlot(i).writeToNBT(item);
                // add the tag compound to our tag list
                items.appendTag(item);
            }
        }
        // Add the TagList to the ItemStack's Tag Compound with the name "ItemInventory"
        tag.setTag("ItemInventory", items);
    }
}
