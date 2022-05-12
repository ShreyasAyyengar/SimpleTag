package me.shreyasayyengar.simpletag.events;

import me.shreyasayyengar.simpletag.TagPlugin;
import me.shreyasayyengar.simpletag.objects.Arena;
import me.shreyasayyengar.simpletag.objects.TagPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityHit implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        // check if both are players
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player damager = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            Arena arena = TagPlugin.getInstance().getArena();
            if (arena.isPlaying(damager.getUniqueId()) && arena.isPlaying(victim.getUniqueId())) {

                TagPlayer tagDamager = arena.getPlayer(damager.getUniqueId());
                TagPlayer tagVictim = arena.getPlayer(victim.getUniqueId());

                if (!tagDamager.tagPlayer(tagVictim)) {
                    event.setCancelled(true);
                }
            }
        }
    }
}
