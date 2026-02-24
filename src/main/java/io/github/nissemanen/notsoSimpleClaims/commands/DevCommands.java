package io.github.nissemanen.notsoSimpleClaims.commands;

import com.mojang.brigadier.Command;
import io.papermc.paper.command.brigadier.Commands;

public class DevCommands {
    public void register(Commands registrar) {
        registrar.register(
                Commands.literal("DevClaims")
                        .then(Commands.literal("claim"))
                        .then(Commands.literal("abandon"))
                        .build()
        );
    }
}
