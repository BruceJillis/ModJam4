package net.brucejillis.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

public class ContainerUnwrittenLetter extends Container {
    private EntityPlayer player;

    public ContainerUnwrittenLetter(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
