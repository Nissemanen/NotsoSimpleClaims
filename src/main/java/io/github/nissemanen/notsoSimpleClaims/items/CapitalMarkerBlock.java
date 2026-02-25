package io.github.nissemanen.notsoSimpleClaims.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import io.papermc.paper.datacomponent.item.ItemLore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.common.value.qual.StaticallyExecutable;

import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class CapitalMarkerBlock {
    @StaticallyExecutable
    public static ItemStack get() {
        ItemStack block = ItemStack.of(Material.LODESTONE);

        block.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);
        block.setData(DataComponentTypes.LORE,
                ItemLore.lore(List.of(
                        Component.text("One claim to rule them all")
                )));
        block.setData(DataComponentTypes.ITEM_NAME,
                Component.text("Capital Marker")
                        .color(NamedTextColor.GOLD)
                        .decorate(TextDecoration.ITALIC)
        );

        return block;
    }
}
