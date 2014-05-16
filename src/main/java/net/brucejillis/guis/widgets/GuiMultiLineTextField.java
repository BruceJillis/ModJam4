package net.brucejillis.guis.widgets;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.opengl.GL11;

import java.util.Iterator;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiMultiLineTextField {
    private static final int FONT_HEIGHT = 12;
    private int lines;
    private int line_height;
    private boolean enableBackgroundDrawing = true;
    private FontRenderer fontRenderer;
    private int width;
    private int height;
    private int x;
    private int y;
    private int textColor = 0x000000;
    private String text = "";
    private boolean canLoseFocus = true;
    private boolean isFocused = false;
    private int cursorColor = 0xffffff;
    private int borderColor = -6250336;
    private int backgroundColor = -16777216;
    private boolean isEnabled = true;
    private int cursorPosition = 0;

    public GuiMultiLineTextField(FontRenderer fontRendererObj, int x, int y, int width, int line_height, int lines) {
        this.fontRenderer = fontRendererObj;
        this.x = x;
        this.y = y;
        this.width = width;
        this.line_height = line_height;
        this.lines = lines;
        this.height = line_height * lines;
    }

    public void drawTextField() {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if (isEnabledBackgroundDrawing()) {
            drawRect(x - 1, y - 1, x + width + 1, y + height + 1, borderColor);
            drawRect(x, y, x + width, y + height, backgroundColor);
        }
        fontRenderer.drawSplitString(getCurrentPageOfText(text, width, height), x + 4, y + 2, width - 4, textColor);
        if (isFocused)
            fontRenderer.drawStringWithShadow("_", x + 4, y + 2, cursorColor);
    }

    private String getCurrentPageOfText(String text, int width, int height) {
        List list = fontRenderer.listFormattedStringToWidth(text, width);
        int current = 0;
        String page = "";
        for (Iterator iterator = list.iterator(); iterator.hasNext(); current += FONT_HEIGHT) {
            page += (String) iterator.next() + "\n";
            if (current >= height) break;
        }
        return page;
    }

    /**
     * Draws a solid color rectangle with the specified coordinates and color. Args: x1, y1, x2, y2, color
     */
    public static void drawRect(int x1, int y1, int x2, int y2, int color) {
        int j1;

        if (x1 < x2) {
            j1 = x1;
            x1 = x2;
            x2 = j1;
        }

        if (y1 < y2) {
            j1 = y1;
            y1 = y2;
            y2 = j1;
        }

        float f3 = (float) (color >> 24 & 255) / 255.0F;
        float f = (float) (color >> 16 & 255) / 255.0F;
        float f1 = (float) (color >> 8 & 255) / 255.0F;
        float f2 = (float) (color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(f, f1, f2, f3);
        tessellator.startDrawingQuads();
        tessellator.addVertex((double) x1, (double) y2, 0.0D);
        tessellator.addVertex((double) x2, (double) y2, 0.0D);
        tessellator.addVertex((double) x2, (double) y1, 0.0D);
        tessellator.addVertex((double) x1, (double) y1, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public void setBackgroundDrawing(boolean enable) {
        enableBackgroundDrawing = enable;
    }

    public boolean isEnabledBackgroundDrawing() {
        return enableBackgroundDrawing;
    }

    public void setEnableBackgroundDrawing(boolean enableBackgroundDrawing) {
        this.enableBackgroundDrawing = enableBackgroundDrawing;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void mouseClicked(int x, int y, int button) {
        boolean flag = x >= this.x && x < this.x + this.width && y >= this.y && y < this.y + this.height;
        if (canLoseFocus) {
            setFocused(flag);
        }

        if (isFocused && button == 0) {
            int l = x - this.x;

            if (this.enableBackgroundDrawing) {
                l -= 4;
            }
            //String s = fontRenderer.trimStringToWidth(text.substring(this.lineScrollOffset), width);
            //setCursorPosition(fontRenderer.trimStringToWidth(s, l).length() + this.lineScrollOffset);
        }
    }

    public void setCursorPosition(int x) {
        cursorPosition = x;
    }

    private void setFocused(boolean flag) {
        isFocused = flag;
    }

    public void textboxKeyTyped(char c, int ext) {
        if (!this.isFocused) {
            return;
        } else {
            if (ChatAllowedCharacters.isAllowedCharacter(c)) {
                if (isEnabled) {
                    writeText(Character.toString(c));
                }
            }
        }
    }

    private void writeText(String s) {
        text = text + ChatAllowedCharacters.filerAllowedCharacters(s);
        moveCursorBy(1);
    }

    private void moveCursorBy(int i) {
        setCursorPosition(cursorPosition + i);
    }
}
