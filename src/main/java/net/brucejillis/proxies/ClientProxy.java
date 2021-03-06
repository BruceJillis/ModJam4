package net.brucejillis.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.brucejillis.MailboxMod;
import net.brucejillis.renderers.RenderBlockMailbox;
import net.brucejillis.renderers.RenderItemMailBox;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMailbox.class, new RenderBlockMailbox());
        MinecraftForgeClient.registerItemRenderer(MailboxMod.itemMailbox, new RenderItemMailBox());
    }
}
