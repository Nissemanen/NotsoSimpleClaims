package io.github.nissemanen.notsoSimpleClaims.listeners;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {
    @EventHandler
    final void onRightClickBlock(PlayerInteractEvent e) {
        Block block = e.getClickedBlock();
        assert block != null;
        BlockState blockState = block.getState();

        if (!(blockState instanceof TileState tileState)) {
            Bukkit.getLogger().info("clicked block is not a TileEntity");
            return;
        }

        Bukkit.getLogger().info("\nclicked block: "+block+"\nblock PDC: "+tileState.getPersistentDataContainer());
    }
}
