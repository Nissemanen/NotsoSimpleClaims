package io.github.nissemanen.notsoSimpleClaims.Blocks.listeners;

import io.github.nissemanen.notsoSimpleClaims.Blocks.items.CapitalMarkerItem;
import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
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

    @EventHandler
    final void onBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        PersistentDataContainer persistentDataContainer = item.getItemMeta().getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(plugin, "is_capital_block");

        if (!(persistentDataContainer.has(key) && Boolean.TRUE.equals(persistentDataContainer.get(key, PersistentDataType.BOOLEAN)))) return;

        capitalBlocks.put(e.getBlock(), e.getPlayer().getUniqueId());

        claimManager.claimChunk(e.getPlayer(), e.getBlock().getChunk());

        e.getPlayer().sendMessage("item:\n"+item);
    }

    @EventHandler
    final void onBlockDropItem(BlockDropItemEvent e) {
        /*
        setting:
         - dropCapitalBlockOnBreak: bool
         - capitalBlockAutoPickupOnDrop: bool
         */
        boolean capitalBlockAutoPickupOnDrop = plugin.getConfig().getBoolean("capitalBlockAutoPickupOnDrop"); // todo - actually make this use the config settings

        Block block = e.getBlock();

        if (!capitalBlocks.containsKey(block)) return;

        if (capitalBlockAutoPickupOnDrop) {
            Item tempItem = e.getItems().getFirst();

            tempItem.setItemStack(new CapitalMarkerItem(plugin).get());

            e.getItems().set(0, tempItem);
        } else {
            e.setCancelled(true);

            e.getPlayer().getInventory().addItem(new CapitalMarkerItem(plugin).get());
        }

        claimManager.abandonChunk(e.getPlayer(), block.getChunk());
        capitalBlocks.remove(block);
    }
}
