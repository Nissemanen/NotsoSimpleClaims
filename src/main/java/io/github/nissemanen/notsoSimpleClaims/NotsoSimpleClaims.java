package io.github.nissemanen.notsoSimpleClaims;

import com.zaxxer.hikari.HikariDataSource;
import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import io.github.nissemanen.notsoSimpleClaims.commands.DevCommands;
import io.github.nissemanen.notsoSimpleClaims.Blocks.listeners.PlayerListenerCapitalBlock;
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class NotsoSimpleClaims extends JavaPlugin {
    ClaimManager claimManager;

    @Override
    public void onEnable() {
        HikariDataSource ds = new HikariDataSource();
                         ds.setJdbcUrl("jdbc:sqlite:" + new File(getDataFolder(), "data.db"));

        claimManager = new ClaimManager(ds);
        getServer().getPluginManager().registerEvents(new PlayerListenerCapitalBlock(this), this);

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands -> new DevCommands(this).register(commands.registrar()));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
