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
    HikariDataSource dataSource;

    @Override
    public void onLoad() {
        if (getDataFolder().mkdirs()) getLogger().info("Data folder has been created");

        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:sqlite:" + new File(getDataFolder(), "claims.db"));

        claimManager = new ClaimManager(dataSource);


        claimManager.readyDataBase();
    }

    @Override
    public void onEnable() {
        PlayerListenerCapitalBlock playerListenerCapitalBlock = new PlayerListenerCapitalBlock(this, claimManager);
        getServer().getPluginManager().registerEvents(playerListenerCapitalBlock, this);

        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, commands ->
                new DevCommands(this, claimManager, playerListenerCapitalBlock).register(commands.registrar())
        );
    }

    @Override
    public void onDisable() {
        dataSource.close();
    }
}
