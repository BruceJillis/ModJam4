package net.brucejillis.util;

import net.brucejillis.MailboxMod;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHelper {
    private static Logger logger = Logger.getLogger(MailboxMod.ID);

    public static void log(String message, Object ... args) {
        logger.log(Level.INFO, String.format(message, args));
    }

    public static void error(String message, Object ... args) {
        logger.log(Level.SEVERE, "Error: " + String.format(message, args));
    }
}
