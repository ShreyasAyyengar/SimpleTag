package me.shreyasayyengar.simpletag.events;

import me.shreyasayyengar.simpletag.TagPlugin;
import me.shreyasayyengar.simpletag.objects.TagPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Join implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        TagPlugin.getInstance().getArena().getPlayers().forEach(tagPlayer -> {

            boolean alreadyIn = tagPlayer.getUUID().equals(event.getPlayer().getUniqueId());

            if (!alreadyIn) {
                TagPlayer player = new TagPlayer(event.getPlayer().getUniqueId());
                TagPlugin.getInstance().getArena().addPlayer(player);
            }
        });
    }
}
