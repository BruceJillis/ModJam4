package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.containers.ContainerMailbox;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiLetter extends GuiContainer {
    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/letter.png");
    private EntityPlayer player;
    private GuiTextField subject;

    public GuiLetter(EntityPlayer player) {
        super(new ContainerLetter(player.inventory, player.getCurrentEquippedItem()));
        this.player = player;
        xSize = 178;
        ySize = 212;
        guiLeft = (int) ((width - xSize) / 2.0f);
        guiTop = (int) ((width - xSize) / 2.0f);
    }

    public void initGui() {
        super.initGui();
        Keyboard.enableRepeatEvents(true);
        subject = new GuiTextField(this.fontRendererObj, guiLeft + 37, guiTop + 34, 133, 14);
        subject.setTextColor(-1);
        subject.setDisabledTextColour(-1);
        subject.setEnableBackgroundDrawing(false);
        subject.setMaxStringLength(40);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
