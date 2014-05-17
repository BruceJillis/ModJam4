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
import net.minecraft.world.World;

import java.io.IOException;

public class PacketHandler {
    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        if (event.packet.channel().equals(MailboxMod.CHANNEL)) {
            EntityPlayer player = ((NetHandlerPlayServer) event.handler).playerEntity;
            World world = player.worldObj;
            try {
                PacketChangeInventory.processPacketOnServerSide(world, player, event.packet.payload());
            } catch (IOException e) {
                LogHelper.error(e.getMessage());
            }
        }
    }
}
