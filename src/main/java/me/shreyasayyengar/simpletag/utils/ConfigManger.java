package me.shreyasayyengar.simpletag.utils;

import me.shreyasayyengar.simpletag.TagPlugin;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class ConfigManger {

    private static TagPlugin main;

    public static void init(TagPlugin main) {
        ConfigManger.main = main;
        main.getConfig().options().configuration();
        main.saveDefaultConfig();
    }

    public static List<String> getTaggedCommands(Player player) {
        return main.getConfig().getStringList("tag-actions").stream().map(s -> s.replace("{player}", player.getName())).collect(Collectors.toList());
    }

    public static List<String> getUntaggedCommands(Player player) {
        return main.getConfig().getStringList("untag-actions").stream().map(s -> s.replace("{player}", player.getName())).collect(Collectors.toList());
    }
}
