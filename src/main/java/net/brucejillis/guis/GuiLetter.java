package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.containers.ContainerMailbox;
import net.brucejillis.guis.widgets.GuiMultiLineTextField;
import net.brucejillis.items.ItemLetter;
import net.brucejillis.items.ItemWrittenLetter;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class GuiLetter extends GuiContainer {
    private static final int BUTTON_SIGN = 1;
    private final EntityPlayer player;
    private final ItemStack letter;
    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/letter.png");
    private GuiTextField subject;

    public GuiLetter(EntityPlayer player) {
        super(new ContainerLetter(player.inventory, player.getCurrentEquippedItem()));
        this.player = player;
        this.letter = new ItemStack(MailboxMod.itemWrittenLetter, 1, 0);
    }

    public void initGui() {
        super.initGui();
        xSize = 178;
        ySize = 229;
        guiLeft = (int) ((width - xSize) / 2.0f);
        guiTop = (int) ((height - ySize) / 2.0f);
        Keyboard.enableRepeatEvents(true);
        // subject line
        subject = new GuiTextField(this.fontRendererObj, guiLeft + 39, guiTop + 18, 131, 12);
        subject.setTextColor(-1);
        subject.setMaxStringLength(45);
        // add buttons
        buttonList.add(new GuiButton(BUTTON_SIGN, guiLeft + 138, guiTop + 122, 32, 20, "Sign"));
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton) {
        switch(guibutton.id) {
            case BUTTON_SIGN:
                NBTTagCompound tag = ItemWrittenLetter.ensureTagCompound(letter);
                tag.setString("Sender", player.getDisplayName());
                tag.setString("Subject", subject.getText());
                player.inventory.addItemStackToInventory(letter);
                player.inventory.consumeInventoryItem(player.inventory.getCurrentItem().getItem());
                player.inventory.markDirty();
                inventorySlots.detectAndSendChanges();
                player.closeScreen();
                break;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
        // render textfields
        subject.drawTextBox();
    }
}
