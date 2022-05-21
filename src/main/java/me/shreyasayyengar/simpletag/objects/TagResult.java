package me.shreyasayyengar.simpletag.objects;

import me.shreyasayyengar.simpletag.utils.Util;
import org.bukkit.entity.Player;

public enum TagResult {

    NOT_TAGGED(Util.colourise("&cYou are not a tagger!")),
    TAG_BACK(Util.colourise("&cYou cannot perform tag-backs!")),
    ALREADY_TAGGED(Util.colourise("&6This player is already tagged!")),
    SUCCESS(Util.colourise("&aYou tagged &2&l%player%&r&a!"));
    private final String message;

    TagResult(String message) {
        this.message = message;
    }

    public String get(Player player) {
        if (this == SUCCESS) {
            return Util.colourise(message.replace("%player%", player.getName()));
        } else {
            return Util.colourise(message);
        }
    }
}
