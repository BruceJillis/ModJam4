package net.brucejillis.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
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
        int day = (int)Math.floor((float)mc.theWorld.getTotalWorldTime() / 24000.0f);
        int hour = (int)(mc.theWorld.getWorldTime() / 1000.0f);
        LogHelper.log("day %d hour %d", day, hour);
    }
}
