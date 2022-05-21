package me.shreyasayyengar.simpletag.objects;

import java.util.Collection;
import java.util.HashSet;

public class RegisteredTagPair {

    public static final Collection<RegisteredTagPair> TAG_PAIRS = new HashSet<>();

    private final TagPlayer victim;
    private final TagPlayer tagger;

    public RegisteredTagPair(TagPlayer victim, TagPlayer tagger) {
        this.victim = victim;
        this.tagger = tagger;

        TAG_PAIRS.add(this);
    }

    public TagPlayer getVictim() {
        return victim;
    }

    public TagPlayer getTagger() {
        return tagger;
    }
}

