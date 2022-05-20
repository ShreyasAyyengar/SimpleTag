package me.shreyasayyengar.simpletag.objects;

import me.shreyasayyengar.simpletag.utils.ConfigManger;
import me.shreyasayyengar.simpletag.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Objects;
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
        handleSetTag(true);

        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 1, 1);
    }

    public void untagForcefully() {
        handleSetTag(false);
    }

    private void handleSetTag(boolean isTagged) {
        this.isTagged = isTagged;

        {
            ItemStack[] armor = {
                    new ItemStack(Material.LEATHER_BOOTS),
                    new ItemStack(Material.LEATHER_LEGGINGS),
                    new ItemStack(Material.LEATHER_CHESTPLATE),
                    new ItemStack(Material.LEATHER_HELMET)
            };

            LeatherArmorMeta meta = (LeatherArmorMeta) armor[0].getItemMeta();
            meta.setColor(Color.RED);

            for (ItemStack itemStack : armor) {
                itemStack.setItemMeta(meta);
            }

            getPlayer().getInventory().setArmorContents(armor);
        }
    }

    public boolean tagPlayer(TagPlayer toTag) {

        boolean success = false;
        TagResult result;

        if (!isTagged) {
            result = TagResult.NOT_TAGGED;
        } else if (toTag.isTagged()) {
            result = TagResult.ALREADY_TAGGED;
        } else if (toTag.getLastTaggedBy() == this) {
            result = TagResult.TAG_BACK;
        } else {
            result = TagResult.SUCCESS;
        }

        if (result == TagResult.SUCCESS) {
            this.isTagged = false;
            ConfigManger.getUntaggedCommands(getPlayer()).forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));

            ItemStack[] air = {
                    new ItemStack(Material.AIR),
                    new ItemStack(Material.AIR),
                    new ItemStack(Material.AIR),
                    new ItemStack(Material.AIR)
            };

            getPlayer().getInventory().setArmorContents(air);

            // ---------------------------------------------------------------------------------------------------------

            ConfigManger.getTaggedCommands(toTag.getPlayer()).forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
            toTag.getPlayer().sendMessage(Util.colourise("&cYou have been tagged by &6" + Bukkit.getPlayer(uuid).getName() + "&c!"));
            toTag.setTagged();
            toTag.setLastTaggedBy(this);

            success = true;
        }

        String s = result.get(getPlayer());
        getPlayer().sendMessage(s);

        return success;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagPlayer tagPlayer = (TagPlayer) o;
        return Objects.equals(uuid, tagPlayer.uuid);
    }
}
