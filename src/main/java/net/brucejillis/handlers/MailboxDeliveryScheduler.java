package net.brucejillis.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.brucejillis.events.MailBoxPlacedEvent;
import net.brucejillis.events.MailBoxRemovedEvent;
import net.brucejillis.handlers.data.MailboxDeliveryData;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.Minecraft;

public class MailboxDeliveryScheduler {
    private final Minecraft mc;

    public MailboxDeliveryScheduler() {
        mc = Minecraft.getMinecraft();
    }

    @SubscribeEvent
    public void serverTickHandler(TickEvent.ServerTickEvent event) {
        if (mc.theWorld == null)
            return;
        MailboxDeliveryData data = MailboxDeliveryData.forWorld(mc.theWorld);
        data.tick();
    }

    @SubscribeEvent
    public void mailboxPlaced(MailBoxPlacedEvent event) {
        MailboxDeliveryData data = MailboxDeliveryData.forWorld(mc.theWorld);
        data.registerMailbox(event.getName(), event.getX(), event.getY(), event.getZ());
    }

    @SubscribeEvent
    public void mailboxRemoved(MailBoxRemovedEvent event) {
        MailboxDeliveryData data = MailboxDeliveryData.forWorld(mc.theWorld);
        data.unregisterMailbox(event.getName());
    }
}
