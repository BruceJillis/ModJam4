package net.brucejillis.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.containers.ContainerMailbox;
import net.brucejillis.guis.GuiLetter;
import net.brucejillis.guis.GuiMailbox;
import net.brucejillis.items.ItemUnwrittenLetter;
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
        switch (ID) {
            case MailboxMod.GUI_MAILBOX:
                TileEntity entity = world.getTileEntity(x, y, z);
                if ((entity != null) && (entity instanceof TileEntityMailbox)) {
                    TileEntityMailbox te = (TileEntityMailbox)entity;
                    return new ContainerMailbox(player.inventory, te.getPrimaryEntity(world));
                }
                break;
            case MailboxMod.GUI_LETTER:
                return new ContainerLetter(player.inventory, player.getCurrentEquippedItem());
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // play nice with disabling the ui via F1
        if (!Minecraft.isGuiEnabled())
            return null;
        switch (ID) {
            case MailboxMod.GUI_MAILBOX:
                TileEntity entity = world.getTileEntity(x, y, z);
                if ((entity != null) && (entity instanceof TileEntityMailbox)) {
                    TileEntityMailbox te = (TileEntityMailbox)entity;
                    return new GuiMailbox(player, te.getPrimaryEntity(world));
                }
                break;
            case MailboxMod.GUI_LETTER:
                if (player.inventory.getCurrentItem().getItem() instanceof ItemUnwrittenLetter) {
                    return new GuiLetter(player, player.getCurrentEquippedItem());
                }
        }
        return null;
    }
}
