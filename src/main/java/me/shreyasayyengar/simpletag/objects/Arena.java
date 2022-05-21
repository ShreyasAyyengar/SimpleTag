package me.shreyasayyengar.simpletag.objects;

import me.shreyasayyengar.simpletag.utils.ConfigManger;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Arena {

    public final boolean isVirus = ConfigManger.isVirus();
    private final Collection<TagPlayer> players = new HashSet<>();

    public void addPlayer(TagPlayer player) {
        players.add(player);
    }

    public void removePlayer(TagPlayer player) {
        players.remove(player);
    }

    public void removePlayer(UUID uuid) {
        players.removeIf(tagPlayer -> tagPlayer.getUUID().equals(uuid));
    }

    public Collection<TagPlayer> getPlayers() {
        return players;
    }

    public boolean isPlaying(UUID uuid) {
        return players.stream().anyMatch(tagPlayer -> tagPlayer.getUUID().equals(uuid));
    }

    public TagPlayer getPlayer(UUID uuid) {
        return players.stream().filter(tagPlayer -> tagPlayer.getUUID().equals(uuid)).findAny().orElse(null);
    }
}
