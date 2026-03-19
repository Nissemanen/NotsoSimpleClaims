package io.github.nissemanen.notsoSimpleClaims.Blocks.menus;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.MenuType;
import org.bukkit.plugin.Plugin;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("UnstableApiUsage")
public class CapitalBlockMenu implements InventoryHolder {
    private Inventory inventory;

    public CapitalBlockMenu(Plugin plugin, Player player) {
        /*
        what menu is this?
        this is supposed to be the menu that opens when a capital block that has an owner gets right-clicked,
        aka where you see and expand your territory
         */
        if (plugin.getConfig().getBoolean("capitalBlockSettings.largeClaimDisplay")) {
            InventoryView inventoryView = MenuType.GENERIC_9X6.builder().title(Component.text("Capital")).build(player);
            /*
            first add the lodestone in the middle
             */

        }

        this.inventory = plugin.getServer().createInventory(this, 9*6);
    }

    @Override
    public @NonNull Inventory getInventory() {
        return inventory;
    }
}
