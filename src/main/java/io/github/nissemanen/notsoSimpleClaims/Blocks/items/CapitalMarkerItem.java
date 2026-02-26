package io.github.nissemanen.notsoSimpleClaims.Blocks.items;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CapitalMarkerItem {
    private final Plugin plugin;

    public CapitalMarkerItem(Plugin plugin) {
        this.plugin = plugin;
    }

    public ItemStack get() {
        ItemStack block = ItemStack.of(Material.LODESTONE);
        ItemMeta meta = block.getItemMeta();
        assert meta != null;

        NamespacedKey key = new NamespacedKey(plugin, "is_capital_block");

        meta.getPersistentDataContainer().set(key, PersistentDataType.BOOLEAN, true);

        meta.setEnchantmentGlintOverride(true);
        meta.lore(List.of(
                Component.text("One claim to rule them all")
        ));
        meta.itemName(
                Component.text("Capital Marker")
                        .color(NamedTextColor.GOLD)
        );
        meta.customName(
                Component.text("Capital block")
                        .color(NamedTextColor.GOLD)
        );
        meta.displayName(
                Component.text("Capital")
                        .color(NamedTextColor.GOLD)
        );

        block.setItemMeta(meta);

        return block;
    }
}
