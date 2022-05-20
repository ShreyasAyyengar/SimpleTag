package me.shreyasayyengar.simpletag.commands;

import me.shreyasayyengar.simpletag.TagPlugin;
import me.shreyasayyengar.simpletag.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetTagCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            sender.sendMessage("You cannot execute this command from the console");
            return false;
        }

        if (!player.hasPermission("simpletag.set")) {
            return false;
        }

        if (args.length != 2) {
            player.sendMessage("&cUsage: /tagger <add|subtract> <player>");
            return false;
        }

        if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("subtract")) {
            player.sendMessage("&cUsage: /tagger <add|subtract> <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("&cThat player is not online!");
            return false;
        }

        switch (args[0].toLowerCase()) {
            case "add" -> {
                TagPlugin.getInstance().getArena().getPlayers().forEach(tagPlayer -> {
                    if (tagPlayer.getUUID().equals(target.getUniqueId())) {
                        tagPlayer.setTagged();
                    }
                });
                player.sendMessage(Util.colourise("&aYou have tagged &e" + target.getName()));
            }

            case "subtract" -> {
                TagPlugin.getInstance().getArena().getPlayers().forEach(tagPlayer -> {
                    if (tagPlayer.getUUID().equals(target.getUniqueId())) {
                        tagPlayer.untagForcefully();
                    }
                });
                player.sendMessage(Util.colourise("&aYou have untagged " + target.getName()));
            }
        }

        TagPlugin.getInstance().getArena().getPlayer(target.getUniqueId()).setTagged();
        player.sendMessage(Util.colourise("&aForcefully tagged &e" + target.getName() + "&a."));


        return false;
    }
}
