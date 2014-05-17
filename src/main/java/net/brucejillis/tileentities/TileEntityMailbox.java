package net.brucejillis.tileentities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TileEntityMailbox extends TileEntity implements IInventory {
    private ItemStack[] inventory;

    // position of the primary multiblock block
    private int baseX = -1;
    private int baseY = -1;
    private int baseZ = -1;

    private String name = "";

    public TileEntityMailbox() {
        inventory = new ItemStack[6];
    }

    public void setBasePosition(int baseX, int baseY, int baseZ) {
        this.baseX = baseX;
        this.baseY = baseY;
        this.baseZ = baseZ;
    }

    public TileEntityMailbox getPrimaryEntity(World world) {
        if (baseX == -1) {
            return this;
        }
        return (TileEntityMailbox) world.getTileEntity(baseX, baseY, baseZ);
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
        return "MailBox";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player) {
        return (worldObj.getTileEntity(xCoord, yCoord, zCoord) == this) && (player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64);
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        return false;
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        writeToNBT(nbtTag);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        readFromNBT(pkt.func_148857_g());
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        // read inventory from nbt
        NBTTagList tags = tag.getTagList("inventory", tag.getId());
        for(int i = 0; i < tags.tagCount(); i++) {
            NBTTagCompound compound = tags.getCompoundTagAt(i);
            short slot = compound.getShort("slot");
            if ((slot >= 0) && (slot < inventory.length)) {
                inventory[slot] = ItemStack.loadItemStackFromNBT(compound);
            }
        }
        // read base block position
        baseX = tag.getInteger("baseX");
        baseY = tag.getInteger("baseY");
        baseZ = tag.getInteger("baseZ");
        // read name
        name = tag.getString("name");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
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
        // base block position
        tag.setInteger("baseX", baseX);
        tag.setInteger("baseY", baseY);
        tag.setInteger("baseZ", baseZ);
        // store name
        tag.setString("name", name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
