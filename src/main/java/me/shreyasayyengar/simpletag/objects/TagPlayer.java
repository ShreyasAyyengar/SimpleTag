package me.shreyasayyengar.simpletag.objects;

import me.shreyasayyengar.simpletag.TagPlugin;
import me.shreyasayyengar.simpletag.utils.ConfigManger;
import me.shreyasayyengar.simpletag.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class TagPlayer {

    private final UUID uuid;
    private RegisteredTagPair lastTaggedBy;
    private boolean isTagged;

    public TagPlayer(UUID uuid) {
        this.uuid = uuid;
        this.isTagged = false;
        this.lastTaggedBy = null;
    }

    private void handleSetTag(boolean isTagged) {
        this.isTagged = isTagged;

        {
            ItemStack[] armor = {
                    new ItemStack(Material.LEATHER_BOOTS),
                    new ItemStack(Material.LEATHER_LEGGINGS),
                    new ItemStack(Material.LEATHER_CHESTPLATE),
                    new ItemStack(Material.AIR)
            };

            LeatherArmorMeta meta = (LeatherArmorMeta) armor[0].getItemMeta();
            meta.setColor(Color.RED);

            for (ItemStack itemStack : armor) {
                itemStack.setItemMeta(meta);
            }

            getPlayer().getInventory().setArmorContents(armor);
        }

        if (!isTagged) {
            ItemStack[] air = {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)};
            getPlayer().getInventory().setArmorContents(air);
        }
    }

    private boolean dictateTagBack(TagPlayer toTag) {

        AtomicBoolean allow = new AtomicBoolean(true);

        RegisteredTagPair.TAG_PAIRS.forEach(pair -> {
            if (pair.getTagger() == toTag && pair.getVictim() == this) {
                allow.set(false);
            }
        });

        return allow.get();
    }

    private void clearTagBack() {
        RegisteredTagPair.TAG_PAIRS.removeIf(pair -> pair.getTagger() == this);
    }

    public void setTagged() {
        handleSetTag(true);

        getPlayer().getWorld().playSound(getPlayer().getLocation(), Sound.ENTITY_SHULKER_BULLET_HIT, 1, 1);
    }

    public void untagForcefully() {
        handleSetTag(false);
    }

    public boolean tagPlayer(@Nullable TagPlayer target) {

        boolean success = false;
        TagResult result;

        if (!isTagged) {
            result = TagResult.NOT_TAGGED;
        } else if (target.isTagged()) {
            result = TagResult.ALREADY_TAGGED;
        } else if (!dictateTagBack(target)) {
            result = TagResult.TAG_BACK;
        } else {
            result = TagResult.SUCCESS;
        }

        if (result == TagResult.SUCCESS) {

            ConfigManger.getTaggedCommands(target.getPlayer()).forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
            target.getPlayer().sendMessage(Util.colourise("&cYou have been tagged by &6" + Bukkit.getPlayer(uuid).getName() + "&c!"));
            target.setTagged();
            clearTagBack();
            this.lastTaggedBy = new RegisteredTagPair(target, this);

            if (!TagPlugin.getInstance().getArena().isVirus) {
                this.isTagged = false;
                ConfigManger.getUntaggedCommands(getPlayer()).forEach(command -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command));
                ItemStack[] air = {new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR), new ItemStack(Material.AIR)};
                getPlayer().getInventory().setArmorContents(air);
            }

            success = true;
        }

        String s = result.get(target.getPlayer());
        getPlayer().sendMessage(s);

        return success;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
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
