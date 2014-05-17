package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class ItemWrittenLetter extends Item {
    public ItemWrittenLetter() {
        setUnlocalizedName("itemWrittenLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setMaxStackSize(1);
    }

    public static NBTTagCompound ensureTagCompound(ItemStack stack) {
        if (stack.stackTagCompound == null)
            stack.setTagCompound(new NBTTagCompound());
        return stack.stackTagCompound;
    }
}
