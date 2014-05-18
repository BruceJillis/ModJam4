package net.brucejillis.renderers;

import net.brucejillis.MailboxMod;
import net.brucejillis.blocks.BlockMailbox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderBlockMailbox extends TileEntitySpecialRenderer {
    private final ResourceLocation modelLocation = new ResourceLocation(MailboxMod.ID, "models/mailbox2.obj");
    private final ResourceLocation textureLocation = new ResourceLocation(MailboxMod.ID, "textures/models/texturemap.png");

    private final IModelCustom model;
    private final Minecraft mc;

    public RenderBlockMailbox() {
        mc = Minecraft.getMinecraft();
        model = AdvancedModelLoader.loadModel(modelLocation);
    }

    @Override
    public void renderTileEntityAt(TileEntity entity, double x, double y, double z, float var8) {
        if (BlockMailbox.isMailboxBase(entity.getBlockMetadata())) {
            GL11.glPushMatrix();
            GL11.glTranslated(x, y, z);
            // maybe make a comment
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            mc.renderEngine.bindTexture(textureLocation);
            model.renderAll();
            GL11.glPopMatrix();
        }
    }


}
