package net.brucejillis.events;

import cpw.mods.fml.common.eventhandler.Event;

public class MailBoxPlacedEvent extends Event {
    private String name;
    private int x;
    private int y;
    private int z;

    public MailBoxPlacedEvent(String name, int x, int y, int z) {
        super();
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "MailBoxPlacedEvent{" +
                "name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
