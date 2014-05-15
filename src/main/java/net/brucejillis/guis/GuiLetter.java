package net.brucejillis.guis;

import net.brucejillis.MailboxMod;
import net.brucejillis.containers.ContainerLetter;
import net.brucejillis.containers.ContainerMailbox;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiLetter extends GuiContainer {
    private ResourceLocation background = new ResourceLocation(MailboxMod.ID, "/textures/gui/letter.png");
    private EntityPlayer player;

    public GuiLetter(EntityPlayer player) {
        super(new ContainerLetter(player.inventory, ));
        this.player = player;
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}
