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
        return type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return true;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        GL11.glPushMatrix();
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        mc.renderEngine.bindTexture(textureLocation);
        model.renderAll();
        GL11.glPopMatrix();
    }
}
