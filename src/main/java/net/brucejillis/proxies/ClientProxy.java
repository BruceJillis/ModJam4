package net.brucejillis.proxies;

import cpw.mods.fml.client.registry.ClientRegistry;
import net.brucejillis.renderers.RenderBlockMailbox;
import net.brucejillis.tileentities.TileEntityMailbox;

public class ClientProxy extends CommonProxy {
    @Override
    public void registerRenderers() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMailbox.class, new RenderBlockMailbox());
    }
}
