package net.brucejillis.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import io.netty.buffer.ByteBufInputStream;
import net.brucejillis.MailboxMod;
import net.brucejillis.handlers.packets.PacketChangeInventory;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

import java.io.IOException;

public class PacketHandler {
    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        if (event.packet.channel().equals(MailboxMod.CHANNEL)) {
            LogHelper.log(event.packet.getTarget().name());
            try {
                PacketChangeInventory.processPacketOnServerSide(event.packet.payload(), event.packet.getTarget());
            } catch (IOException e) {
                LogHelper.error(e.getMessage());
            }
        }
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        LogHelper.log(event.toString());
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
    }
}
