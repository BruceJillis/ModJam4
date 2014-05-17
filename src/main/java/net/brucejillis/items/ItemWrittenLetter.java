package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.item.Item;

public class ItemWrittenLetter extends Item {
    public ItemWrittenLetter() {
        setUnlocalizedName("itemWrittenLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setMaxStackSize(1);
    }
}
