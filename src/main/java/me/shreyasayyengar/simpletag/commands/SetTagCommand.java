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
            player.sendMessage(Util.colourise("&cYou do not have permission to execute this command"));
            return false;
        }

        if (args.length != 2) {
            player.sendMessage(Util.colourise("&cUsage: /simpletag <add|remove> <player>"));
            return false;
        }

        if (!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("remove")) {
            player.sendMessage("&cUsage: /simpletag <add|remove> <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[1]);

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
                player.sendMessage(Util.colourise("&aForcefully tagged &e" + target.getName() + "&a."));
            }

            case "remove" -> {
                TagPlugin.getInstance().getArena().getPlayers().forEach(tagPlayer -> {
                    if (tagPlayer.getUUID().equals(target.getUniqueId())) {
                        tagPlayer.untagForcefully();
                    }
                });
                player.sendMessage(Util.colourise("&aYou have untagged " + target.getName()));
            }
        }

        return false;
    }
}
