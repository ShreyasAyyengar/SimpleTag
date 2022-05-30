package me.shreyasayyengar.simpletag.events;

import me.shreyasayyengar.simpletag.TagPlugin;
import me.shreyasayyengar.simpletag.objects.TagPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.atomic.AtomicBoolean;

public class Join implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        AtomicBoolean alreadyIn = new AtomicBoolean(false);

        TagPlugin.getInstance().getArena().getPlayers().forEach(tagPlayer -> {

            if (tagPlayer.getPlayer().getUniqueId().equals(event.getPlayer().getUniqueId())) {
                alreadyIn.set(true);
            }

        });

        if (!alreadyIn.get()) {
            TagPlugin.getInstance().getArena().addPlayer(new TagPlayer(event.getPlayer().getUniqueId()));
        }
    }
}
