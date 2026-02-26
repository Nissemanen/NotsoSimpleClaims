package io.github.nissemanen.notsoSimpleClaims.Blocks.listeners;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerListenerCapitalBlock implements Listener {
    @SuppressWarnings("unused")
    private final Map<Location, UUID> capitalBlocks = new HashMap<>();
    private final Plugin plugin;

    public PlayerListenerCapitalBlock(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    final void onRightClickBlock(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR) return;

        ItemStack currentItem = e.getPlayer().getInventory().getItemInMainHand();

        e.getPlayer().sendMessage("current item hold is: "+currentItem);
    }

    @EventHandler
    final void onBlockPlace(BlockPlaceEvent e) {
        plugin.getLogger().info("placed");

        ItemStack item = e.getItemInHand();

        e.getPlayer().sendMessage("item:\n"+item);
    }
}
