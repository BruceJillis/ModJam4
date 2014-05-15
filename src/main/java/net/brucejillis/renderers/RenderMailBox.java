package net.brucejillis.renderers;

import net.brucejillis.MailboxMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderMailBox extends TileEntitySpecialRenderer {
    private final ResourceLocation modelLocation = new ResourceLocation(MailboxMod.ID, "/textures/models/mailbox.obj");
    private final ResourceLocation textureLocation = new ResourceLocation(MailboxMod.ID, "/textures/models/mailbox.png");

    private final IModelCustom model;
    private final Minecraft mc;

    public RenderMailBox() {
        mc = Minecraft.getMinecraft();
        model = AdvancedModelLoader.loadModel(modelLocation);
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float var8) {
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        mc.renderEngine.bindTexture(textureLocation);
        model.renderAll();

    }
}
