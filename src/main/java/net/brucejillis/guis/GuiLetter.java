package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.handlers.packets.PacketChangeInventory;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiLetter extends GuiContainer {
    private static final int BUTTON_SIGN = 1;
    private final EntityPlayer player;
    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/letter.png");
    private GuiTextField subject;

    public GuiLetter(EntityPlayer player) {
        super(new ContainerLetter(player.inventory, player.getCurrentEquippedItem()));
        this.player = player;
    }

    public void initGui() {
        super.initGui();
        xSize = 178;
        ySize = 242;
        guiLeft = (int) ((width - xSize) / 2.0f);
        guiTop = (int) ((height - ySize) / 2.0f);
        Keyboard.enableRepeatEvents(true);
        // add buttons
        buttonList.add(new GuiButton(BUTTON_SIGN, guiLeft + 138, guiTop + 138, 32, 20, "Sign"));
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton) {
        switch(guibutton.id) {
            case BUTTON_SIGN:
                MailboxMod.channel.sendToServer(PacketChangeInventory.createWriteLetterPacket(player, "etxt"));
                player.closeScreen();
                break;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }
}
