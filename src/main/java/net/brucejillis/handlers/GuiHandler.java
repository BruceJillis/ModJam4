package net.brucejillis.handlers;

import cpw.mods.fml.common.network.IGuiHandler;
import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerMailbox;
import net.brucejillis.guis.GuiMailbox;
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
        TileEntity entity = world.getTileEntity(x, y, z);
        if (entity instanceof TileEntityMailbox)
            return new ContainerMailbox(player.inventory, (TileEntityMailbox) entity);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // play nice with disabling the ui via F1
        if (!Minecraft.isGuiEnabled())
            return null;
        TileEntity entity = world.getTileEntity(x, y, z);
        switch (ID) {
            case MailboxMod.GUI_MAILBOX:
                return new GuiMailbox(player, (TileEntityMailbox) entity);
        }
        return null;
    }
}
