package net.brucejillis.handlers;

import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class GuiMailbox extends GuiScreen {
    private final EntityPlayer player;
    private final TileEntityMailbox entity;

    public GuiMailbox(EntityPlayer player, TileEntityMailbox entity) {
        this.player = player;
        this.entity = entity;
    }

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        drawDefaultBackground();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
