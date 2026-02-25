package io.github.nissemanen.notsoSimpleClaims.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {
    @EventHandler
    final void onRightClickBlock(PlayerInteractEvent e) {
        Bukkit.getLogger().info("clicked block: "+e.getClickedBlock());
    }
}
