package net.brucejillis.containers;

import com.sun.xml.internal.ws.api.message.Attachment;
import net.brucejillis.containers.slots.AttachmentSlot;
import net.brucejillis.containers.slots.MailboxSlot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerLetter extends Container {
    public ContainerLetter(InventoryPlayer inventory, ItemStack item) {
        super();
        // bind attachment slots
        for (int i = 0; i < 4; i++) {
            addSlotToContainer(new AttachmentSlot(inventory, i, 9, 17 + (i * 18)));
        }
        bindPlayerInventory(inventory, 9, 159, 9, 217);
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

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
