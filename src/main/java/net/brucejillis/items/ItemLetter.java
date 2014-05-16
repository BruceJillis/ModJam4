package net.brucejillis.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.brucejillis.MailboxMod;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import java.util.List;

public class ItemLetter extends Item {
    private IIcon writtenIcon;

    public ItemLetter() {
        setUnlocalizedName("itemLetter");
        setTextureName(MailboxMod.ID + ":" + getUnlocalizedName().substring(5) + "_blank");
        setCreativeTab(MailboxMod.mailboxTab);
        setMaxStackSize(16);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        NBTTagCompound tag = getStackTagCompound(stack);
        if (!isBlank(stack) && player.isSneaking()) {
            // erase letter
            return new ItemStack(MailboxMod.itemLetter, 1, 0);
        }
        if (isBlank(stack) && !player.isSneaking()) {
            player.openGui(MailboxMod.instance, MailboxMod.GUI_LETTER, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return stack;
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List par3List, boolean par4) {
        NBTTagCompound tag = getStackTagCompound(stack);
        if (tag.hasKey("Sender"))
            par3List.add(String.format("'%s' - %s ", tag.getString("Subject"), tag.getString("Sender")));
        else
            par3List.add("Unwritten");
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        NBTTagCompound tag = getStackTagCompound(stack);
        if (tag.hasKey("Sender"))
            return ("" + StatCollector.translateToLocal("item.itemLetterWritten.name")).trim();
        return ("" + StatCollector.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
    }

    @Override
    public IIcon getIconIndex(ItemStack stack) {
        NBTTagCompound tag = getStackTagCompound(stack);
        if (tag.hasKey("Sender")) return writtenIcon;
        return itemIcon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister) {
        itemIcon = par1IconRegister.registerIcon(MailboxMod.ID + ":" + getUnlocalizedName().substring(5) + "_blank");
        writtenIcon = par1IconRegister.registerIcon(MailboxMod.ID + ":" + getUnlocalizedName().substring(5) + "_written");
    }

    private NBTTagCompound getStackTagCompound(ItemStack stack) {
        if (stack.stackTagCompound == null) stack.setTagCompound(new NBTTagCompound());
        return stack.stackTagCompound;
    }

    public boolean isBlank(ItemStack stack) {
        NBTTagCompound tag = getStackTagCompound(stack);
        return !tag.hasKey("Sender");
    }
}
