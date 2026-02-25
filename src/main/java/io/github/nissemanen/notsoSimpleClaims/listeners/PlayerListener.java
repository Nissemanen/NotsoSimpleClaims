package io.github.nissemanen.notsoSimpleClaims.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class PlayerListener implements Listener {
    private final Map<Location, UUID> capitalBlocks = new HashMap<>();

    @EventHandler
    final void onRightClickBlock(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        Bukkit.getLogger().info("idk");
    }
}
