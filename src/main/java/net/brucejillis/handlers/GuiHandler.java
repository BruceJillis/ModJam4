package net.brucejillis.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.containers.ContainerMailbox;
import net.brucejillis.containers.inventories.UnwrittenLetterInventory;
import net.brucejillis.guis.GuiLetter;
import net.brucejillis.guis.GuiMailbox;
import net.brucejillis.guis.GuiNameMailbox;
import net.brucejillis.guis.GuiReadLetter;
import net.brucejillis.items.ItemUnwrittenLetter;
import net.brucejillis.items.ItemWrittenLetter;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // play nice with disabling the ui via F1
        if (!Minecraft.isGuiEnabled())
            return null;
        boolean slots = true;
        switch (ID) {
            case MailboxMod.GUI_NAME_MAILBOX:
                slots = false;
            case MailboxMod.GUI_MAILBOX:
                TileEntity entity = world.getTileEntity(x, y, z);
                if ((entity != null) && (entity instanceof TileEntityMailbox)) {
                    TileEntityMailbox te = (TileEntityMailbox)entity;
                    return new ContainerMailbox(player.inventory, te.getPrimaryEntity(world), slots);
                }
                break;
            case MailboxMod.GUI_LETTER:
                return new ContainerLetter(player.inventory, new UnwrittenLetterInventory(), player.getCurrentEquippedItem());
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // play nice with disabling the ui via F1
        if (!Minecraft.isGuiEnabled())
            return null;
        TileEntity entity = world.getTileEntity(x, y, z);
        switch (ID) {
            case MailboxMod.GUI_NAME_MAILBOX:
                if ((entity != null) && (entity instanceof TileEntityMailbox)) {
                    TileEntityMailbox te = (TileEntityMailbox)entity;
                    return new GuiNameMailbox(player, te.getPrimaryEntity(world));
                }
            case MailboxMod.GUI_MAILBOX:
                if ((entity != null) && (entity instanceof TileEntityMailbox)) {
                    TileEntityMailbox te = (TileEntityMailbox)entity;
                    return new GuiMailbox(player, te.getPrimaryEntity(world));
                }
                break;
            case MailboxMod.GUI_LETTER:
                if (player.inventory.getCurrentItem().getItem() instanceof ItemUnwrittenLetter) {
                    return new GuiLetter(player, new UnwrittenLetterInventory(), player.getCurrentEquippedItem());
                }
                break;
            case MailboxMod.GUI_READ_LETTER:
                if (player.inventory.getCurrentItem().getItem() instanceof ItemWrittenLetter) {
                    return new GuiReadLetter(player, new UnwrittenLetterInventory(), player.getCurrentEquippedItem());
                }
                break;
        }
        return null;
    }
}
