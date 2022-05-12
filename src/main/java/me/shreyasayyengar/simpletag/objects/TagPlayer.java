package me.shreyasayyengar.simpletag.objects;

import me.shreyasayyengar.simpletag.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.UUID;

public class TagPlayer {

    private final UUID uuid;
    private TagPlayer lastTaggedBy;
    private boolean isTagged;

    public TagPlayer(UUID uuid) {
        this.uuid = uuid;
        this.isTagged = false;
        this.lastTaggedBy = null;
    }

    public void setTagged() {
        this.isTagged = true;
        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 1, 1);
    }

    public boolean tagPlayer(TagPlayer toTag) {
        this.isTagged = false;

        if (toTag.getLastTaggedBy() == this) {
            getPlayer().playSound(getPlayer().getLocation(), Sound.ENTITY_SHULKER_HURT, 1, 1);
            getPlayer().sendMessage("&cYou cannot perform tag-backs!");
            return false;
        } else if (!isTagged) {
            getPlayer().sendMessage("&cYou cannot tag players if you are not tagged!!");
            return false;
        } else {
            toTag.getPlayer().sendMessage(Util.colourise("&cYou have been tagged by &6" + Bukkit.getPlayer(uuid).getName() + "&c!"));
            toTag.setLastTaggedBy(this);
            return true;
        }
    }

    public void runUnsafeActions(Runnable runnable) {
        if (Bukkit.getPlayer(uuid) != null) {
            runnable.run();
        }
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public TagPlayer getLastTaggedBy() {
        return lastTaggedBy;
    }

    public void setLastTaggedBy(TagPlayer lastTaggedBy) {
        this.lastTaggedBy = lastTaggedBy;
    }

    public boolean isTagged() {
        return isTagged;
    }

    public UUID getUUID() {
        return uuid;
    }
}
