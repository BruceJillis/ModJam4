package net.brucejillis.guis;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;

public class GuiLetter extends GuiScreen {
    private EntityPlayer player;

    public GuiLetter(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partial) {
        super.drawScreen(mouseX, mouseY, partial);
        drawDefaultBackground();

    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
