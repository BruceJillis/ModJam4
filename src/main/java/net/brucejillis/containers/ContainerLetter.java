package net.brucejillis.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;

public class ContainerLetter extends Container {
    public ContainerLetter(InventoryPlayer inventory, ItemStack item) {
        super();
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
