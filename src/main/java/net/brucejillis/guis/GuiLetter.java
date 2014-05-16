package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.containers.ContainerMailbox;
import net.brucejillis.items.ItemLetter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiLetter extends GuiScreen {
    private int xSize;
    private int ySize;
    private int guiLeft;
    private int guiTop;

    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/letter.png");
    private EntityPlayer player;
    private GuiTextField subject;
    private String DEFAULT_SUBJECT = "Subject..";

    public GuiLetter(EntityPlayer player, ItemLetter letter) {
        this.player = player;

    }

    public void initGui() {
        super.initGui();
        xSize = 178;
        ySize = 212;
        guiLeft = (int) ((width - xSize) / 2.0f);
        guiTop = (int) ((height - ySize) / 2.0f);
        Keyboard.enableRepeatEvents(true);
        subject = new GuiTextField(this.fontRendererObj, guiLeft + 39, guiTop + 34, 131, 12);
        subject.setTextColor(-1);
        subject.setMaxStringLength(45);
        subject.setText(DEFAULT_SUBJECT);
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    protected void keyTyped(char par1, int par2) {
        super.keyTyped(par1, par2);
        this.subject.textboxKeyTyped(par1, par2);
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        if (!subject.isFocused() && subject.getText().equals(DEFAULT_SUBJECT)) {
            subject.setText("");
        }
        subject.mouseClicked(par1, par2, par3);
    }

    public void drawScreen(int mouseX, int mouseY, float partial) {
        this.drawDefaultBackground();
        this.drawGuiContainerBackgroundLayer(partial, mouseX, mouseY);
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        super.drawScreen(mouseX, mouseY, partial);

        RenderHelper.enableGUIStandardItemLighting();
        GL11.glPushMatrix();

        GL11.glTranslatef((float)guiLeft, (float)guiTop, 0.0F);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glDisable(GL11.GL_LIGHTING);
        this.drawGuiContainerForegroundLayer(mouseX, mouseY);

        GL11.glPopMatrix();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        RenderHelper.enableStandardItemLighting();
    }

    protected void drawGuiContainerBackgroundLayer(float var1, int mouseX, int mouseY) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        renderEntityWithYawAndPitch(guiLeft + 21, guiTop + 47, 16, (float)(guiLeft + 21) - mouseX, (float)(guiTop + 31) - mouseY, mc.thePlayer);
        this.subject.drawTextBox();
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        fontRendererObj.drawString(I18n.format("container.letter", new Object[0]), 8, 6, 0x00000);
    }

    public static void renderEntityWithYawAndPitch(int x, int y, int scale, float yaw, float pitch, EntityLivingBase entity) {
        GL11.glEnable(GL11.GL_COLOR_MATERIAL);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) x, (float) y, 50.0F);
        GL11.glScalef((float) (-scale), (float) scale, (float) scale);
        GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
        float f2 = entity.renderYawOffset;
        float f3 = entity.rotationYaw;
        float f4 = entity.rotationPitch;
        float f5 = entity.prevRotationYawHead;
        float f6 = entity.rotationYawHead;
        GL11.glRotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GL11.glRotatef(-135.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-((float) Math.atan((double) (pitch / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        entity.renderYawOffset = (float) Math.atan((double) (yaw / 40.0F)) * 20.0F;
        entity.rotationYaw = (float) Math.atan((double) (yaw / 40.0F)) * 40.0F;
        entity.rotationPitch = -((float) Math.atan((double) (pitch / 40.0F))) * 20.0F;
        entity.rotationYawHead = entity.rotationYaw;
        entity.prevRotationYawHead = entity.rotationYaw;
        GL11.glTranslatef(0.0F, entity.yOffset, 0.0F);
        RenderManager.instance.playerViewY = 180.0F;
        RenderManager.instance.renderEntityWithPosYaw(entity, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
        entity.renderYawOffset = f2;
        entity.rotationYaw = f3;
        entity.rotationPitch = f4;
        entity.prevRotationYawHead = f5;
        entity.rotationYawHead = f6;
        GL11.glPopMatrix();
        RenderHelper.disableStandardItemLighting();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
