package io.github.nissemanen.notsoSimpleClaims.Blocks.listeners;

import io.github.nissemanen.notsoSimpleClaims.Blocks.items.CapitalMarkerItem;
import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ListenerForCapitalBlockRelated implements Listener {
    private final Map<Block, UUID> capitalBlocks = new HashMap<>();
    private final Plugin plugin;
    private final ClaimManager claimManager;

    public ListenerForCapitalBlockRelated(Plugin plugin, ClaimManager claimManager) {
        this.plugin = plugin;
        this.claimManager = claimManager;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    final void onBlockPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;

        PersistentDataContainer persistentDataContainer = e.getItemInHand().getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "is_capital_block");

        if (!(persistentDataContainer.has(key) && Boolean.TRUE.equals(persistentDataContainer.get(key, PersistentDataType.BOOLEAN)))) return;

        capitalBlocks.put(e.getBlock(), e.getPlayer().getUniqueId());
        claimManager.claimChunk(e.getPlayer(), e.getBlock().getChunk());
    }

    @EventHandler
    final void onBlockDropItem(BlockDropItemEvent e) {
        /*
        capitalBlockSettings:
            dropCapitalBlockOnBreak: bool
            capitalBlockAutoPickupOnDrop: bool
         */

        Block block = e.getBlock();

        if (!capitalBlocks.containsKey(block)) return;

        if (!plugin.getConfig().getBoolean("capitalBlockSettings.dropCapitalBlockOnBreak")) {
            e.setCancelled(true);
        }

        boolean capitalBlockAutoPickupOnDrop = plugin.getConfig().getBoolean("capitalBlockSettings.capitalBlockAutoPickupOnDrop");

        claimManager.abandonChunk(e.getPlayer(), block.getChunk());
        capitalBlocks.remove(block);

        if (!capitalBlockAutoPickupOnDrop) {
            e.setCancelled(true);

            e.getPlayer().getInventory().addItem(new CapitalMarkerItem(plugin).get());

            return;
        }

        Item tempItem = e.getItems().getFirst();

        tempItem.setItemStack(new CapitalMarkerItem(plugin).get());

        e.getItems().set(0, tempItem);
    }
}
