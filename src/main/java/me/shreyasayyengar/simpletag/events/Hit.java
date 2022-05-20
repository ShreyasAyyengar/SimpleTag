package me.shreyasayyengar.simpletag.events;

import me.shreyasayyengar.simpletag.TagPlugin;
import me.shreyasayyengar.simpletag.objects.Arena;
import me.shreyasayyengar.simpletag.objects.TagPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class Hit implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player victim) {
            handlePossibleTag(event, damager, victim);
        }
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {

        if (event.getEntity().getShooter() instanceof Player shooter) {
            if (event.getHitEntity() == null) return;

            if (event.getHitEntity() instanceof Player victim) {

                handlePossibleTag(event, shooter, victim);
            }
        }
    }

    private void handlePossibleTag(Cancellable cancellable, Player damager, Player victim) {
        Arena arena = TagPlugin.getInstance().getArena();
        if (arena.isPlaying(damager.getUniqueId()) && arena.isPlaying(victim.getUniqueId())) {

            TagPlayer tagDamager = arena.getPlayer(damager.getUniqueId());
            TagPlayer tagVictim = arena.getPlayer(victim.getUniqueId());

            if (!tagDamager.tagPlayer(tagVictim)) {
                cancellable.setCancelled(true);
            }
        }
    }
}
