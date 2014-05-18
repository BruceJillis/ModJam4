package net.brucejillis.renderers;

import net.brucejillis.MailboxMod;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderItemMailBox implements IItemRenderer {
    private final ResourceLocation modelLocation = new ResourceLocation(MailboxMod.ID, "/textures/models/mailbox2.obj");
    private final ResourceLocation textureLocation = new ResourceLocation(MailboxMod.ID, "/textures/models/texturemap.png");

    private final IModelCustom model;
    private final Minecraft mc;

    public RenderItemMailBox() {
        mc = Minecraft.getMinecraft();
        model = AdvancedModelLoader.loadModel(modelLocation);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
            GL11.glScalef(0.5f, 0.5f, 0.5f);
            //GL11.glTranslatef(0f, -0.25f, 0f);
        } else {
            GL11.glScalef(0.5f, 0.40f, 0.5f);
            GL11.glRotatef(130.0f, 0f, 1f, 0f);
            //GL11.glTranslatef(0f, 0.5f, 0f);
        }
        mc.renderEngine.bindTexture(textureLocation);
        model.renderAll();
        GL11.glPopMatrix();
    }
}
