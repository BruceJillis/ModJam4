package net.brucejillis.items;

import net.brucejillis.MailboxMod;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ItemWrittenLetter extends Item {
    public ItemWrittenLetter() {
        setUnlocalizedName("itemWrittenLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5));
        setMaxStackSize(1);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        NBTTagCompound tag = ensureTagCompound(stack);
        String sender = tag.getString("Sender");
        String to = tag.getString("To");
        NBTTagList pages = tag.getTagList("Pages", Constants.NBT.TAG_STRING);
        if (pages.tagCount() == 1)
            list.add(String.format("From %s To %s (1 page) / Unsent", sender, to));
        else
            list.add(String.format("From %s To %s (%d pages) / Unsent", sender, to, pages.tagCount()));
        String sneak = pages.getStringTagAt(0).split("\n")[0];
        list.add(StringUtils.abbreviate(sneak, 16));
    }

    public static NBTTagCompound ensureTagCompound(ItemStack stack) {
        if (stack.stackTagCompound == null)
            stack.setTagCompound(new NBTTagCompound());
        return stack.stackTagCompound;
    }
}
