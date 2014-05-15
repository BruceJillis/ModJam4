package net.brucejillis.handlers.data;

import net.brucejillis.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class MailboxDeliveryData extends WorldSavedData {
    private static final String KEY = "mailboxdelivery";

    private static final String MORNING = "morning";
    private static final String AFTERNOON = "afternoon";

    private World world;
    private String state = MORNING;

    public MailboxDeliveryData(String name, World world) {
        super(name);
        this.world = world;
    }

    public void tick() {
        int hour = timeToHours(world.getWorldTime());
        if ((hour == 2) && (state == MORNING)) {
            LogHelper.log("morning delivery!", hour);
            doDelivery();
            // mark delivery done
            state = AFTERNOON;
            markDirty();
            saveAllData();
        }
        if ((hour == 10) && (state == AFTERNOON)) {
            LogHelper.log("afternoon delivery!", hour);
            doDelivery();
            // mark delivery done
            state = MORNING;
            markDirty();
            saveAllData();
        }
    }

    private void doDelivery() {
    }

    public static long hoursUntilDelivery(long time) {
        long dMorning = Math.abs(timeToHours(time) - 2);
        long dAfternoon = Math.abs(timeToHours(time) - 10);
        return Math.min(dMorning, dAfternoon);
    }

    private static int timeToHours(long time) {
        return (int)(time / 1000.0f);
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

    public void saveAllData() {
        world.perWorldStorage.saveAllData();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        state = tag.getString("state");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setString("state", state);
    }
}
