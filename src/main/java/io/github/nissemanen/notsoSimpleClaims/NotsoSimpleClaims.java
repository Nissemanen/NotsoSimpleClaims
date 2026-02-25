package io.github.nissemanen.notsoSimpleClaims;

import io.github.nissemanen.notsoSimpleClaims.commands.DevCommands;
import io.github.nissemanen.notsoSimpleClaims.listeners.PlayerListener;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class NotsoSimpleClaims extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> new DevCommands().register(commands.registrar()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
