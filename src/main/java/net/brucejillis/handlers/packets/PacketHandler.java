package net.brucejillis.handlers.packets;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.brucejillis.MailboxMod;
import net.brucejillis.handlers.packets.PacketManager;
import net.brucejillis.util.LogHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.world.World;

import java.io.IOException;

// i'm sorry, new stuff is new
public class PacketHandler {
    @SubscribeEvent
    public void onServerPacket(FMLNetworkEvent.ServerCustomPacketEvent event) {
        if (event.packet.channel().equals(MailboxMod.CHANNEL)) {
            EntityPlayer player = ((NetHandlerPlayServer) event.handler).playerEntity;
            World world = player.worldObj;
            try {
                PacketManager.processPacketOnServerSide(world, player, event.packet.payload());
            } catch (IOException e) {
                LogHelper.error(e.getMessage());
            }
        }
    }
}
