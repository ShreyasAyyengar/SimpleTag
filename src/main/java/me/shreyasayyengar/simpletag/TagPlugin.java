package me.shreyasayyengar.simpletag;

import me.shreyasayyengar.simpletag.commands.EntityHit;
import me.shreyasayyengar.simpletag.ev.ProjectileHit;
import me.shreyasayyengar.simpletag.events.EntityHit;
import me.shreyasayyengar.simpletag.events.Join;
import me.shreyasayyengar.simpletag.events.Leave;
import me.shreyasayyengar.simpletag.events.ProjectileHit;
import me.shreyasayyengar.simpletag.objects.Arena;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Stream;

public final class TagPlugin extends JavaPlugin {

    private Arena arena;

    @Override
    public void onEnable() {
        getLogger().info("SimpleTag plugin enabled!");

        registerCommands();
        registerEvents();
        initConfig();
        this.arena = new Arena();
    }

    private void initConfig() {
    }

    private void registerEvents() {
        Stream.of(
                new EntityHit(),
                new ProjectileHit(),
                new Join(),
                new Leave()
        ).forEach(event -> getServer().getPluginManager().registerEvents(event, this);

    }

    private void registerCommands() {

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static TagPlugin getInstance() {
        return JavaPlugin.getPlugin(TagPlugin.class);
    }

    public Arena getArena() {
        return arena;
    }
}
