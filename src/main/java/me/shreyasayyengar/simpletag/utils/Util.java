package me.shreyasayyengar.simpletag.utils;

import org.bukkit.ChatColor;

public class Util {

    public static String colourise(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
