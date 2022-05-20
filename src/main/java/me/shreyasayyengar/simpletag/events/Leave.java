package me.shreyasayyengar.simpletag.events;

import me.shreyasayyengar.simpletag.TagPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Leave implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        TagPlugin.getInstance().getArena().getPlayers().forEach(tagPlayer -> {
            if (tagPlayer.getUUID() == event.getPlayer().getUniqueId()) {
                TagPlugin.getInstance().getArena().removePlayer(tagPlayer);
            }
        });
    }
}
