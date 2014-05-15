package net.brucejillis;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.brucejillis.blocks.BlockMailbox;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

@Mod(modid = MailboxMod.ID, version = MailboxMod.VERSION)
public class MailboxMod {
    public static final String ID = "mailboxmod";
    public static final String VERSION = "0.1";

    // tab where all the items and blocks are placed
    public static CreativeTabs mailboxTab;

    // blocks
    public static Block blockMailbox;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // create mod specific tab
        mailboxTab = new CreativeTabs("mailboxTab") {
            @Override
            public Item getTabIconItem() {
                return Item.getItemFromBlock(blockMailbox);
            }
        };
        // register mailbox block
        blockMailbox = new BlockMailbox();
        GameRegistry.registerBlock(blockMailbox, ItemBlock.class, "blockMailbox");
        GameRegistry.registerTileEntity(TileEntityMailbox.class, "tileEntityMailbox");
        // register letter item
    }
}
