package io.github.nissemanen.notsoSimpleClaims.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@SuppressWarnings("UnstableApiUsage")
public class PlayerListener implements Listener {
    private final Map<Location, UUID> capitalBlocks = new HashMap<>();

    @EventHandler
    final void onRightClickBlock(PlayerInteractEvent e) {
        if (e.getAction() != Action.RIGHT_CLICK_AIR) return;

        ItemStack currentItem = e.getPlayer().getInventory().getItemInMainHand();

        e.getPlayer().sendMessage("current item hold is: "+currentItem.effectiveName());
    }
}
