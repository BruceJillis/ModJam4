package net.brucejillis.events;

import cpw.mods.fml.common.eventhandler.Event;

public class MailBoxRemovedEvent extends Event {
    private final String name;

    public MailBoxRemovedEvent(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
