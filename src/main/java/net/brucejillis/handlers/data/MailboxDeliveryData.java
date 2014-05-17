package net.brucejillis.handlers.data;

import net.brucejillis.items.ItemWrittenLetter;
import net.brucejillis.tileentities.TileEntityMailbox;
import net.brucejillis.util.LogHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import net.minecraft.world.storage.MapStorage;
import net.minecraftforge.common.util.Constants;

import java.util.HashMap;
import java.util.Map;

// constants in this class are based on the vanilla day/night cycle: http://minecraft.gamepedia.com/Day-night_cycle
public class MailboxDeliveryData extends WorldSavedData {
    private static final String KEY = "mailboxdelivery";

    private static final String MORNING = "morning";
    private static final String AFTERNOON = "afternoon";

    private static MailboxDeliveryData instance;

    private World world;
    private int prev = -1;
    private String state = MORNING;

    private NBTTagList boxes;

    public MailboxDeliveryData(String name, World world) {
        super(name);
        this.world = world;
    }

    public void tick() {
        int hour = timeToHours(getDayTime(world));
        if (hour != prev) {
            LogHelper.log("hour: %d", hour);
            LogHelper.log("hourly delivery!", hour);
            doDelivery();

            prev = hour;
        }
        if ((hour == 2) && (state == MORNING)) {
            LogHelper.log("morning delivery!", hour);
            doDelivery();
            // mark delivery done
            state = AFTERNOON;
            saveAllData();
        }
        if ((hour == 10) && (state == AFTERNOON)) {
            LogHelper.log("afternoon delivery!", hour);
            doDelivery();
            // mark delivery done
            state = MORNING;
            saveAllData();
        }
    }

    private static long getDayTime(World world) {
        return (world.getTotalWorldTime() % 24000);
    }

    private void doDelivery() {
        // loop over all mailboxes and deliver letters
        NBTTagList boxes = instance.boxes;
        if (boxes == null)
            return;
        Map<String, TileEntityMailbox> boxMap = new HashMap<String, TileEntityMailbox>();
        for(int i = 0; i <= boxes.tagCount(); i++) {
            NBTTagCompound tag = boxes.getCompoundTagAt(i);
            boxMap.put(tag.getString("name"), (TileEntityMailbox)world.getTileEntity(tag.getInteger("x"), tag.getInteger("y"), tag.getInteger("z")));
        }
        for(int i = 0; i <= boxes.tagCount(); i++) {
            NBTTagCompound tag = boxes.getCompoundTagAt(i);
            TileEntityMailbox entity = boxMap.get(tag.getString("name"));
            if (entity != null) {
                for(int j = 0; j < entity.getSizeInventory(); j++) {
                    if ((entity.getStackInSlot(j) != null) && (entity.getStackInSlot(j).getItem() instanceof ItemWrittenLetter)) {
                        NBTTagCompound stack = ItemWrittenLetter.ensureTagCompound(entity.getStackInSlot(j));
                        TileEntityMailbox toEntity = boxMap.get(stack.getString("To"));
                        if (toEntity == entity)
                            continue;
                        LogHelper.log(String.valueOf(entity.getStackInSlot(j)) + " " + stack.getString("To"));
                        if (toEntity != null) {
                            int slot = toEntity.getFirstFreeInventorySlot(entity.getStackInSlot(j));
                            if (slot != -1) {
                                LogHelper.log(String.format("from %s to %s", entity.getName(), toEntity.getName()));
                                toEntity.setInventorySlotContents(slot, entity.getStackInSlot(j));
                                entity.setInventorySlotContents(j, null);
                                world.markBlockForUpdate(entity.xCoord, entity.yCoord, entity.zCoord);
                                world.markBlockForUpdate(toEntity.xCoord, toEntity.yCoord, toEntity.zCoord);
                            }
                        }
                    }
                }
            }
        }
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
        if (MailboxDeliveryData.instance != null)
            return MailboxDeliveryData.instance;
        MapStorage storage = world.perWorldStorage;
        MailboxDeliveryData result = (MailboxDeliveryData)storage.loadData(MailboxDeliveryData.class, KEY);
        if (result == null) {
            result = new MailboxDeliveryData(KEY, world);
            storage.setData(KEY, result);
        }
        MailboxDeliveryData.instance = result;
        return result;
    }

    public void saveAllData() {
        markDirty();
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

    public static boolean isNameFree(String name) {
        NBTTagList boxes = instance.boxes;
        if (boxes == null)
            return true;
        for(int i = 0; i <= boxes.tagCount(); i++) {
            if (boxes.getCompoundTagAt(i).getString("name").equals(name)) {
                return false;
            }
        }
        return true;
    }

    public boolean registerMailbox(String name, int x, int y, int z) {
        if (boxes == null) {
            boxes = new NBTTagList();
        }
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("x", x);
        tag.setInteger("y", y);
        tag.setInteger("z", z);
        tag.setString("name", name);
        boxes.appendTag(tag);
        saveAllData();
        return true;
    }

    public void unregisterMailbox(String name) {
        if (boxes == null) {
            LogHelper.error("a mailbox tried to unregister on null list");
            return;
        }
        for(int i = 0; i <= boxes.tagCount(); i++) {
            if (boxes.getCompoundTagAt(i).getString("name").equals(name)) {
                boxes.removeTag(i);
                return;
            }
        }
    }
}
