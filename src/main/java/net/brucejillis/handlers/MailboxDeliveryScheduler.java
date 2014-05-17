package net.brucejillis.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.brucejillis.events.MailBoxPlacedEvent;
import net.brucejillis.events.MailBoxRemovedEvent;
import net.brucejillis.handlers.data.MailboxDeliveryData;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;

public class MailboxDeliveryScheduler {
    private final Minecraft mc;

    public MailboxDeliveryScheduler() {
        mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void serverTickHandler(TickEvent.ServerTickEvent event) {
        if (mc.theWorld == null)
            return;
        if (event.side != Side.SERVER)
            return;
        if (event.phase != TickEvent.Phase.START)
            return;
        MailboxDeliveryData data = MailboxDeliveryData.forWorld(mc.theWorld);
        data.tick();
    }

    @SubscribeEvent
    public void mailboxPlaced(MailBoxPlacedEvent event) {
        MailboxDeliveryData data = MailboxDeliveryData.forWorld(mc.theWorld);
        TileEntity te = mc.theWorld.getTileEntity(event.getX(), event.getY(), event.getZ());
        if (te == null)
            return;
        TileEntityMailbox entity = (TileEntityMailbox)te;
        data.registerMailbox(event.getName(), event.getX(), event.getY(), event.getZ());
    }

    @SubscribeEvent
    public void mailboxRemoved(MailBoxRemovedEvent event) {
        MailboxDeliveryData data = MailboxDeliveryData.forWorld(mc.theWorld);
        data.unregisterMailbox(event.getName());
    }
}
