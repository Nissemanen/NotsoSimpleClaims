package io.github.nissemanen.notsoSimpleClaims.commands;

import com.mojang.brigadier.Command;
import io.github.nissemanen.notsoSimpleClaims.items.CapitalMarkerBlock;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.entity.Player;

public class DevCommands {
    public void register(Commands registrar) {
        registrar.register(
                Commands.literal("DevClaims").requires(cmd -> cmd.getSender().hasPermission("NotsoSimpleClaims.DevCommands"))
                        .then(Commands.literal("claim"))
                        .then(Commands.literal("abandon"))
                        .then(Commands.literal("getCapitalClaim")
                                .executes(cmd -> {
                                    if (!(cmd.getSource().getExecutor() instanceof Player executor)) {
                                        if (!(cmd.getSource().getSender() instanceof Player sender)) return 0;

                                        sender.getInventory().addItem(CapitalMarkerBlock.get());
                                        return Command.SINGLE_SUCCESS;
                                    }

                                    executor.getInventory().addItem(CapitalMarkerBlock.get());

                                    return Command.SINGLE_SUCCESS;
                                }))
                        .build()
        );
    }
}
