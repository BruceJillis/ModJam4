package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.item.Item;

public class ItemLetter extends Item {
    public ItemLetter() {
        setUnlocalizedName("itemLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setCreativeTab(MailboxMod.mailboxTab);
        setMaxStackSize(16);
        set
    }
}
