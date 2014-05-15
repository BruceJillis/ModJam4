package net.brucejillis.handlers;

import net.brucejillis.MailboxMod;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiMailbox extends GuiScreen {
    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/mailbox.png");

    private final EntityPlayer player;
    private final TileEntityMailbox entity;

    private int xSize = 0;
    private int ySize = 0;

    public GuiMailbox(EntityPlayer player, TileEntityMailbox entity) {
        this.player = player;
        this.entity = entity;
        xSize = 195;
        ySize = 136;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        drawDefaultBackground();
        drawGUIBackground();
    }

    private void drawGUIBackground() {
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect((width - xSize)/2, (height - ySize)/2, 0, 0, xSize, ySize);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
