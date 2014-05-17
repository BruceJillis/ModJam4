package net.brucejillis;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.FMLEventChannel;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.brucejillis.blocks.BlockMailbox;
import net.brucejillis.handlers.GuiHandler;
import net.brucejillis.handlers.MailboxDeliveryScheduler;
import net.brucejillis.handlers.PacketHandler;
import net.brucejillis.items.ItemMailBox;
import net.brucejillis.items.ItemUnwrittenLetter;
import net.brucejillis.items.ItemWrittenLetter;
import net.brucejillis.proxies.CommonProxy;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.brucejillis.util.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;

@Mod(modid = MailboxMod.ID, version = MailboxMod.VERSION)
public class MailboxMod {
    // internal name and version
    public static final String ID = "mailboxmod";
    public static final String VERSION = "0.1";
    public static final String CHANNEL = "mailchannel";
    // net
    public static FMLEventChannel channel;

    // instance
    @Mod.Instance(MailboxMod.ID)
    public static MailboxMod instance;

    // GUI constants
    public static final int GUI_MAILBOX      = 1;
    public static final int GUI_LETTER       = 2;
    public static final int GUI_NAME_MAILBOX = 3;

    // proxies
    @SidedProxy(serverSide = "net.brucejillis.proxies.CommonProxy", clientSide = "net.brucejillis.proxies.ClientProxy")
    public static CommonProxy proxy;


    // tab where all the items and blocks are placed
    public static CreativeTabs mailboxTab;

    // blocks
    public static Block blockMailbox;

    // items
    public static Item itemUnwrittenLetter;
    public static Item itemWrittenLetter;
    public static Item itemMailbox;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LogHelper.log("initializing %s (v %s)", ID, VERSION);
        // create mod specific tab
        mailboxTab = new CreativeTabs("mailboxTab") {
            @Override
            public Item getTabIconItem() {
                return itemMailbox;
            }
        };
        // register mailbox block and item (so we can place a multiblock like Item/BlockBed)
        itemMailbox = new ItemMailBox();
        GameRegistry.registerItem(itemMailbox, "itemMailbox");
        blockMailbox = new BlockMailbox();
        GameRegistry.registerBlock(blockMailbox, ItemBlock.class, "blockMailbox");
        GameRegistry.registerTileEntity(TileEntityMailbox.class, "tileEntityMailbox");
        // register letter items
        itemUnwrittenLetter = new ItemUnwrittenLetter();
        GameRegistry.registerItem(itemUnwrittenLetter, "itemUnwrittenLetter");
        itemWrittenLetter = new ItemWrittenLetter();
        GameRegistry.registerItem(itemWrittenLetter, "itemWrittenLetter");
        // register various stuff
        proxy.registerRenderers();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        FMLCommonHandler.instance().bus().register(new MailboxDeliveryScheduler());
        channel = NetworkRegistry.INSTANCE.newEventDrivenChannel(CHANNEL);
        channel.register(new PacketHandler());
    }
}
