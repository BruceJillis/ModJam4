package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class ItemWrittenLetter extends Item {
    public ItemWrittenLetter() {
        setUnlocalizedName("itemWrittenLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4) {
        NBTTagCompound tag = ensureTagCompound(stack);
        String sender = tag.getString("Sender");
        NBTTagList pages = tag.getTagList("Pages", Constants.NBT.TAG_STRING);
        par3List.add(String.format("Written by %s (%d pages) / Unsent", sender, pages.tagCount()));
        par3List.add(pages.getStringTagAt(0));
    }

    public static NBTTagCompound ensureTagCompound(ItemStack stack) {
        if (stack.stackTagCompound == null)
            stack.setTagCompound(new NBTTagCompound());
        return stack.stackTagCompound;
    }
}
