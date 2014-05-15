package net.brucejillis;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.brucejillis.blocks.BlockMailbox;
import net.minecraft.item.ItemBlock;
import sun.org.mozilla.javascript.internal.ast.Block;

@Mod(modid = MailboxMod.ID, version = MailboxMod.VERSION)
public class MailboxMod {
    public static final String ID = "mailboxmod";
    public static final String VERSION = "0.1";

    // blocks
    public static Block blockMailbox;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // register mailbox block
        blockMailbox = new BlockMailbox();
        GameRegistry.registerBlock(blockMailbox, ItemBlock.class, "blockMailbox");
        // register letter item
    }
}
