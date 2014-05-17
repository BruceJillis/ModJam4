package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerMailbox;
import net.brucejillis.handlers.data.MailboxDeliveryData;
import net.brucejillis.handlers.packets.PacketManager;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

public class GuiNameMailbox extends GuiContainer {
    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/name_mailbox.png");

    // buttons
    private static final int BUTTON_OK = 1;

    // width and height of the gui
    private int xSize = 0;
    private int ySize = 0;

    // mailbox primary entity
    private final TileEntityMailbox entity;

    private GuiTextField name;

    public GuiNameMailbox(EntityPlayer player, TileEntityMailbox entity) {
        super(new ContainerMailbox(player.inventory, entity, false));
        this.entity = entity;
        xSize = 195;
        ySize = 136;
        guiLeft = (int)((width - xSize) / 2.0f);
        guiTop = (int)((height - ySize) / 2.0f);
    }

    @Override
    public void initGui() {
        super.initGui();
        // name field
        name = new GuiTextField(fontRendererObj, guiLeft + 25, guiTop + 60, 125, 12);
        name.setEnableBackgroundDrawing(true);
        name.setMaxStringLength(40);
        name.setTextColor(-1);
        // add ok button
        buttonList.clear();
        buttonList.add(new GuiButton(BUTTON_OK, guiLeft + 73, guiTop + 100, 25, 20, "Ok"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partial, int mouseX, int mouseY) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        name.drawTextBox();
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString(StatCollector.translateToLocal("mailbox.title.name"), 30, 45, 0x00000);
    }

    protected void actionPerformed(GuiButton guibutton) {
        switch (guibutton.id) {
            case BUTTON_OK:
                if (!name.getText().equals("")) {
                    entity.setName(name.getText());
                    MailboxMod.channel.sendToServer(PacketManager.createNameMailboxPacket(entity, name.getText()));
                    mc.thePlayer.closeScreen();
                    mc.thePlayer.openGui(MailboxMod.instance, MailboxMod.GUI_MAILBOX, mc.theWorld, entity.xCoord, entity.yCoord, entity.zCoord);
                }
                break;
        }
    }

    protected void keyTyped(char par1, int par2) {
        super.keyTyped(par1, par2);
        name.textboxKeyTyped(par1, par2);
    }

    protected void mouseClicked(int par1, int par2, int par3) {
        super.mouseClicked(par1, par2, par3);
        name.mouseClicked(par1, par2, par3);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
