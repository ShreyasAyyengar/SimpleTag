package me.shreyasayyengar.simpletag.objects;

import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

public class Arena {

    private final Collection<TagPlayer> players = new HashSet<>();

    public void addPlayer(TagPlayer player) {
        players.add(player);
    }

    public void removePlayer(TagPlayer player) {
        players.remove(player);
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
