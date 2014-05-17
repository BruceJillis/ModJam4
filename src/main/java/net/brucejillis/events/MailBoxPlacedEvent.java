package net.brucejillis.events;

import cpw.mods.fml.common.eventhandler.Event;

public class MailBoxPlacedEvent extends Event {
    private final int x;
    private final int y;
    private final int z;

    public MailBoxPlacedEvent(int x, int y, int z) {
        super();
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

    @Override
    public String toString() {
        return "MailBoxPlacedEvent{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
