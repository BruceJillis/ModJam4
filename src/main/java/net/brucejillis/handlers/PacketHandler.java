package net.brucejillis.handlers;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import io.netty.buffer.ByteBufInputStream;
import net.brucejillis.util.LogHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;

public class PacketHandler {
    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        LogHelper.log(event.toString());
        EntityPlayerMP player = ((NetHandlerPlayServer)event.handler).playerEntity;
        ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent event) {
        LogHelper.log(event.toString());
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        ByteBufInputStream bbis = new ByteBufInputStream(event.packet.payload());
    }
}
