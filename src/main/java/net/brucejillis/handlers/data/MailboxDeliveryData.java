package net.brucejillis.handlers.data;

import net.brucejillis.tileentities.TileEntityMailbox;
import net.brucejillis.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

// constants in this class are based on the vanilla day/night cycle: http://minecraft.gamepedia.com/Day-night_cycle
public class MailboxDeliveryData extends WorldSavedData {
    private static final String KEY = "mailboxdelivery";

    private static final String MORNING = "morning";
    private static final String AFTERNOON = "afternoon";

    private World world;
    private int prev = -1;
    private String state = MORNING;

    private NBTTagList boxes;

    public MailboxDeliveryData(String name, World world) {
        super(name);
        this.world = world;
        if (boxes == null) {
            boxes = new NBTTagList();
        }
    }

    public void tick() {
        int hour = timeToHours(getDayTime(world));
        if (hour != prev) {
            LogHelper.log("hour: %d", hour);
            prev = hour;
        }
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

    private static long getDayTime(World world) {
        return (world.getTotalWorldTime() % 24000);
    }

    private void doDelivery() {
    }

    public static int hoursUntilDelivery(World world) {
        int hour = timeToHours(getDayTime(world));
        // time to morning delivery
        if (hour >= 10) {
            return Math.abs(hour - 24) + 2;
        }
        if (hour <= 2) {
            return Math.abs(hour - 2);
        }
        // time to afternoon delivery
        return Math.abs(hour - 10);
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
        boxes = tag.getTagList("boxes", Constants.NBT.TAG_COMPOUND);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        tag.setString("state", state);
        tag.setTag("boxes", boxes);
    }

    public void registerMailbox(String name, int x, int y, int z) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("x", x);
        tag.setInteger("y", y);
        tag.setInteger("z", z);
        tag.setString("name", name);
        boxes.appendTag(tag);
        saveAllData();
    }
}
