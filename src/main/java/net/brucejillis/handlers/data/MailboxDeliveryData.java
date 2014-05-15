package net.brucejillis.handlers.data;

import net.brucejillis.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class MailboxDeliveryData extends WorldSavedData {
    private static final String KEY = "mailboxdelivery";

    private int day;
    private int hour;
    private String state = "0.null";

    public MailboxDeliveryData(String par1Str, World world) {
        super(par1Str);
        day = (int)Math.floor(world.getTotalWorldTime() / 24000.0f);
        hour = (int)(world.getWorldTime() / 1000.0f);
        LogHelper.log("day %d hour %d", day, hour);
    }

    public static MailboxDeliveryData forWorld(World world) {
        MapStorage storage = world.perWorldStorage;
        MailboxDeliveryData result = (MailboxDeliveryData)storage.loadData(MailboxDeliveryData.class, KEY);
        if (result == null) {
            result = new MailboxDeliveryData(KEY, world);
            storage.setData(KEY, result);
        }
        return result;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        day = tag.getInteger("day");
        hour = tag.getInteger("hour");
        state = tag.getString("state");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("day", day);
        tag.setInteger("hour", hour);
        tag.setString("state", state);
    }
}
