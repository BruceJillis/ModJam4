package net.brucejillis.handlers.data;

import net.brucejillis.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;

public class MailboxDeliveryData extends WorldSavedData {
    private static final String KEY = "mailboxdelivery";

    private static final String NONE = "none";
    private static final String MORNING = "morning";
    private static final String AFTERNOON = "afternoon";

    private World world;
    private String state = "";

    public MailboxDeliveryData(String par1Str, World world) {
        super(par1Str);
        this.world = world;
    }

    public void tick() {
        int day = (int)Math.floor(world.getTotalWorldTime() / 24000.0f);
        int hour = (int)(world.getWorldTime() / 1000.0f);
        String state = NONE;
        if ((hour == 2) && (this.state == NONE)) {
            // morning delivery
            state = MORNING;
            markDirty();
        }
        if ((hour == 10) && (this.state == MORNING)) {
            // morning delivery
            this.state = AFTERNOON;
            markDirty();
            saveAllData();
        }
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
        day = tag.getInteger("day");
        delivery = tag.getString("delivery");
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setInteger("day", day);
        tag.setString("delivery", delivery);
    }
}
