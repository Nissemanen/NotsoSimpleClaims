package io.github.nissemanen.notsoSimpleClaims.items;

import io.papermc.paper.datacomponent.DataComponentTypes;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.common.value.qual.StaticallyExecutable;

@SuppressWarnings("UnstableApiUsage")
public class CapitalMarkerBlock {
    @StaticallyExecutable
    public static ItemStack get() {
        ItemStack block = ItemStack.of(Material.LODESTONE);

        block.setData(DataComponentTypes.ENCHANTMENT_GLINT_OVERRIDE, true);

        return block;
    }
}
