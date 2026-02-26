package io.github.nissemanen.notsoSimpleClaims.commands;

import com.mojang.brigadier.Command;
import io.github.nissemanen.notsoSimpleClaims.Blocks.items.CapitalMarkerItem;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DevCommands {
    private final Plugin plugin;

    public DevCommands(Plugin plugin) {
        this.plugin = plugin;
    }

    public void register(Commands registrar) {
        registrar.register(
                Commands.literal("DevClaims").requires(cmd -> cmd.getSender().hasPermission("NotsoSimpleClaims.DevCommands"))
                        .then(Commands.literal("claim"))
                        .then(Commands.literal("abandon"))
                        .then(Commands.literal("getCapitalClaim")
                                .executes(cmd -> {
                                    if (!(cmd.getSource().getExecutor() instanceof Player executor)) {
                                        if (!(cmd.getSource().getSender() instanceof Player sender)) return 0;

                                        sender.getInventory().addItem(new CapitalMarkerItem(plugin).get());
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    executor.getInventory().addItem(new CapitalMarkerItem(plugin).get());

                                    return Command.SINGLE_SUCCESS;
                                }))
                        .build()
        );
    }
}
