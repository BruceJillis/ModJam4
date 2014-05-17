package net.brucejillis.guis;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.handlers.packets.PacketChangeInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.EnumChatFormatting;
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
        pages = new NBTTagList();
        pages.appendTag(new NBTTagString(""));
        totalPages = 1;
    }

    public void initGui() {
        super.initGui();
        xSize = 178;
        ySize = 256;
        guiLeft = (int) ((width - xSize) / 2.0f);
        guiTop = (int) ((height - ySize) / 2.0f);
        Keyboard.enableRepeatEvents(true);
        // add buttons
        buttonList.clear();
        buttonList.add(new GuiButton(BUTTON_SIGN, guiLeft + 138, guiTop + 150, 32, 20, "Sign"));
        buttonList.add(new GuiLetter.NextPageButton(BUTTON_NEXT_PAGE, true, guiLeft + 136, guiTop + 136));
        buttonList.add(new GuiLetter.NextPageButton(BUTTON_PREV_PAGE, false, guiLeft + 40, guiTop + 136));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton) {
        switch (guibutton.id) {
            case BUTTON_SIGN:
                NBTTagCompound tag = new NBTTagCompound();
                tag.setTag("Pages", pages);
                MailboxMod.channel.sendToServer(PacketChangeInventory.createWriteLetterPacket(player, tag));
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
                if (currPage > 0) {
                    --currPage;
                }
                break;
        }
    }

    private void addNewPage() {
        if (pages != null && pages.tagCount() < 50) {
            pages.appendTag(new NBTTagString(""));
            ++totalPages;
        }
    }

    protected void keyTyped(char par1, int par2) {
        if (par2 == 1) {
            player.closeScreen();
        }
        keyTypedInLetter(par1, par2);
    }

    private void keyTypedInLetter(char c, int ext) {
        switch (c) {
            default:
                switch (ext) {
                    case 14:
                        String s = getCurrentPage();
                        if (s.length() > 0) {
                            this.setCurrentPage(s.substring(0, s.length() - 1));
                        }
                        return;
                    case 28:
                    case 156:
                        appendToCurrentPage("\n");
                        return;
                    default:
                        if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                            appendToCurrentPage(Character.toString(c));
                        }
                }
        }
    }

    private String getCurrentPage() {
        return pages != null && currPage >= 0 && currPage < pages.tagCount() ? pages.getStringTagAt(currPage) : "";
    }

    private void setCurrentPage(String text) {
        if (pages != null && currPage >= 0 && currPage < pages.tagCount()) {
            pages.func_150304_a(currPage, new NBTTagString(text));
        }
    }

    private void appendToCurrentPage(String str) {
        String s1 = getCurrentPage();
        String s2 = s1 + str;
        int i = this.fontRendererObj.splitStringWidth(s2 + "" + EnumChatFormatting.BLACK + "_", 118);
        if (i <= 108 && s2.length() < 256) {
            setCurrentPage(s2);
        }
    }

    private boolean mouseInRect(int mouseX, int mouseY, int posX, int posY, int sizeX, int sizeY) {
        return (mouseX >= posX) && (mouseX < (posX + sizeX)) && (mouseY >= posY) && (mouseY < (posY + sizeY));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.renderEngine.bindTexture(background);
        drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        String text = "";
        if (pages != null && currPage >= 0 && currPage < pages.tagCount()) {
            text = pages.getStringTagAt(currPage);
        }
        text = text + "" + EnumChatFormatting.BLACK + "_";
        this.fontRendererObj.drawSplitString(text, 42, 18, 118, 0);
    }

    @SideOnly(Side.CLIENT)
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
