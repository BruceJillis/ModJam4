package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.handlers.packets.PacketChangeInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class GuiLetter extends GuiContainer {
    private static ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/letter.png");

    private static final int BUTTON_SIGN = 1;
    private static final int BUTTON_NEXT_PAGE = 2;
    private static final int BUTTON_PREV_PAGE = 3;

    private final EntityPlayer player;
    private final ItemStack letter;
    private NBTTagList pages;

    private int currPage = 0;
    private int totalPages = 1;

    public GuiLetter(EntityPlayer player, ItemStack stack) {
        super(new ContainerLetter(player.inventory, stack));
        this.player = player;
        this.letter = stack;
        if (stack.hasTagCompound()) {
            NBTTagCompound nbttagcompound = stack.getTagCompound();
            pages = nbttagcompound.getTagList("pages", 8);
            if (pages != null) {
                pages = (NBTTagList) pages.copy();
                totalPages = pages.tagCount();
                if (totalPages < 1) {
                    totalPages = 1;
                }
            }
        }
        if (pages == null) {
            pages = new NBTTagList();
            pages.appendTag(new NBTTagString(""));
            totalPages = 1;
        }
    }

    public void initGui() {
        super.initGui();
        xSize = 178;
        ySize = 242;
        guiLeft = (int) ((width - xSize) / 2.0f);
        guiTop = (int) ((height - ySize) / 2.0f);
        Keyboard.enableRepeatEvents(true);
        // add buttons
        buttonList.clear();
        buttonList.add(new GuiButton(BUTTON_SIGN, guiLeft + 137, guiTop + 137, 32, 20, "Sign"));
        buttonList.add(new GuiLetter.NextPageButton(BUTTON_NEXT_PAGE, true, guiLeft + 136, guiTop + 124));
        buttonList.add(new GuiLetter.NextPageButton(BUTTON_PREV_PAGE, false, guiLeft + 40, guiTop + 124));
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton) {
        switch (guibutton.id) {
            case BUTTON_SIGN:
                MailboxMod.channel.sendToServer(PacketChangeInventory.createWriteLetterPacket(player, "etxt"));
                player.closeScreen();
                break;
            case BUTTON_NEXT_PAGE:
                if (currPage < totalPages - 1) {
                    ++currPage;
                } else {
                    addNewPage();
                    if (currPage < totalPages - 1) {
                        ++currPage;
                    }
                }
                break;
            case BUTTON_PREV_PAGE:

                break;
        }
    }

    private void addNewPage() {
        if (pages != null && pages.tagCount() < 50) {
            pages.appendTag(new NBTTagString(""));
            ++totalPages;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    private class NextPageButton extends GuiButton {
        private boolean direction;

        public NextPageButton(int id, boolean direction, int x, int y) {
            super(id, x, y, 18, 10, "");
            this.direction = direction;
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY) {
            if (this.visible) {
                boolean flag = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                mc.getTextureManager().bindTexture(GuiLetter.background);
                int ux = 178;
                int uy = 0;
                if (!direction) {
                    // switch direction
                    uy += 10;
                }
                if (flag) {
                    // mouse over
                    ux += 18;
                }
                drawTexturedModalRect(this.xPosition, this.yPosition, ux, uy, 18, 10);
            }
        }
    }
}
