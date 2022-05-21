package me.shreyasayyengar.simpletag;

import me.shreyasayyengar.simpletag.commands.SetTagCommand;
import me.shreyasayyengar.simpletag.events.Hit;
import me.shreyasayyengar.simpletag.events.InventoryClick;
import me.shreyasayyengar.simpletag.events.Join;
import me.shreyasayyengar.simpletag.objects.Arena;
import me.shreyasayyengar.simpletag.utils.ConfigManger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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
        ConfigManger.init(this);
    }

    private void registerEvents() {
        Stream.of(
                new Hit(),
                new Join(),
                new InventoryClick()
        ).forEach(event -> getServer().getPluginManager().registerEvents(event, this));

    }

    private void registerCommands() {
        this.getCommand("simpletag").setExecutor(new SetTagCommand());
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

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        arena.getPlayers().forEach(tagPlayer -> {
            System.out.println(arena.getPlayers().size());
            if (tagPlayer.getUUID() == ((Player) sender).getUniqueId()) {
                tagPlayer.setTagged();
            }
        });

        return false;
    }
}
