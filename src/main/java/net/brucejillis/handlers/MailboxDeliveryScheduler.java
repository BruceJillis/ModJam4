package net.brucejillis.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
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
        MailboxDeliveryData deliveryData = MailboxDeliveryData.forWorld(mc.theWorld);
    }
}
