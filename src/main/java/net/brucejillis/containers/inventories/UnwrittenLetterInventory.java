package net.brucejillis.containers.inventories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class UnwrittenLetterInventory implements IInventory {
    private ItemStack[] inventory;
    private boolean dirty = false;

    public UnwrittenLetterInventory() {
        this.inventory = new ItemStack[4];
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
        ItemStack stack = getStackInSlot(i);
        if (stack != null) {
            if (stack.stackSize <= amount) {
                setInventorySlotContents(i, null);
            } else {
                stack = stack.splitStack(amount);
                if (stack.stackSize == 0) {
                    setInventorySlotContents(i, null);
                }
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
        inventory[i] = itemStack;
        if ((itemStack != null) && (itemStack.stackSize > getInventoryStackLimit())) {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }

    @Override
    public String getInventoryName() {
        return "UnwrittenLetter";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        dirty = true;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return true;
    }

    public void readFromNBT(NBTTagCompound tag) {
        // read inventory from nbt
        NBTTagList tags = tag.getTagList("inventory", tag.getId());
        for(int i = 0; i < tags.tagCount(); i++) {
            NBTTagCompound compound = tags.getCompoundTagAt(i);
            short slot = compound.getShort("slot");
            if ((slot >= 0) && (slot < inventory.length)) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(compound);
            }
        }
    }

    public void writeToNBT(NBTTagCompound tag) {
        // write inventory to nbt
        NBTTagList list = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack stack = inventory[i];
            if (stack != null) {
                NBTTagCompound compound = new NBTTagCompound();
                compound.setShort("slot", (short) i);
                stack.writeToNBT(compound);
                list.appendTag(compound);
            }
        }
        tag.setTag("inventory", list);
    }
}
