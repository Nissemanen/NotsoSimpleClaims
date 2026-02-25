package io.github.nissemanen.notsoSimpleClaims;

import io.github.nissemanen.notsoSimpleClaims.listeners.PlayerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class NotsoSimpleClaims extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
