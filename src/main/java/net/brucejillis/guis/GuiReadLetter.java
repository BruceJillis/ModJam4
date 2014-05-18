package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.handlers.packets.PacketManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiReadLetter extends GuiLetter {

    private static final int BUTTON_OK = 6;

    public GuiReadLetter(EntityPlayer player, IInventory inventory, ItemStack stack) {
        super(player, inventory, stack);
    }

    @Override
    public void initGui() {
        super.initGui();
        xSize = 178;
        ySize = 256;
        guiLeft = (int) ((width - xSize) / 2.0f);
        guiTop = (int) ((height - ySize) / 2.0f);
        Keyboard.enableRepeatEvents(true);
        // to field
        to = new GuiTextField(fontRendererObj, guiLeft + 23, guiTop + 9, 133, 12);
        to.setTextColor(-1);
        to.setMaxStringLength(40);
        to.setEnableBackgroundDrawing(true);
        // add buttons
        buttonList.clear();
        buttonList.add(new GuiButton(BUTTON_OK, guiLeft + 138, guiTop + 150, 32, 20, "OK"));
        buttonList.add(new GuiLetter.NextPageButton(BUTTON_NEXT_PAGE, true, guiLeft + 136, guiTop + 136));
        buttonList.add(new GuiLetter.NextPageButton(BUTTON_PREV_PAGE, false, guiLeft + 40, guiTop + 136));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        switch (guibutton.id) {
            case BUTTON_OK:
                player.closeScreen();
                break;
            case BUTTON_NEXT_PAGE:
                if (currPage < totalPages - 1) {
                    ++currPage;
                }
                break;
            case BUTTON_PREV_PAGE:
                if (currPage > 0) {
                    --currPage;
                }
                break;
            default:
                super.actionPerformed(guibutton);
                break;
        }
    }
}