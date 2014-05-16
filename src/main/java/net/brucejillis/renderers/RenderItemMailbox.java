package net.brucejillis.renderers;

import net.brucejillis.MailboxMod;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderItemMailbox implements IItemRenderer {
    private final ResourceLocation modelLocation = new ResourceLocation(MailboxMod.ID, "/textures/models/mailbox.obj");
    private final ResourceLocation textureLocation = new ResourceLocation(MailboxMod.ID, "/textures/models/mailbox.png");

    private final IModelCustom model;
    private final Minecraft mc;

    public RenderItemMailbox() {
        mc = Minecraft.getMinecraft();
        model = AdvancedModelLoader.loadModel(modelLocation);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPopMatrix();
        GL11.glScalef(-1.0f, -1.0f, 1.0f);
        mc.renderEngine.bindTexture(textureLocation);

        GL11.glPushMatrix();
    }
}
