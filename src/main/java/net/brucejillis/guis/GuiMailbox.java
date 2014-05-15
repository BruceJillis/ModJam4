package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerMailbox;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiMailbox extends GuiContainer {
    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/mailbox.png");

    private int xSize = 0;
    private int ySize = 0;

    public GuiMailbox(EntityPlayer player, TileEntityMailbox entity) {
        super(new ContainerMailbox(player.inventory, entity));
        xSize = 195;
        ySize = 136;
        guiLeft = (int)((width - xSize) / 2.0f);
        guiTop = (int)((width - xSize) / 2.0f);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(StatCollector.translateToLocal("mailbox.title"), 8, 6, 0x00000);
        //fontRendererObj.drawString(StatCollector.translateToLocal("mailbox.delivery"), 8, 8, 0x00000);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 8, 43, 0x00000);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
