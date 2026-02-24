package io.github.nissemanen.notsoSimpleClaims;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NotsoSimpleClaims extends JavaPlugin {
    private HikariDataSource dataSource;

    @Override
    public void onEnable() {
        if (getDataFolder().mkdirs())
            getLogger().info("Data folder has been created");
        this.dataSource = new HikariDataSource();
        this.dataSource.setJdbcUrl("jdbc:sqlite:" + new File(getDataFolder(), "data.db"));

        this.initTables();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
