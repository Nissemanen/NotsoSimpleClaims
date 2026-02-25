package io.github.nissemanen.notsoSimpleClaims.commands;

import io.papermc.paper.command.brigadier.Commands;

import java.util.Objects;

public class DevCommands {
    public void register(Commands registrar) {
        registrar.register(
                Commands.literal("DevClaims").requires(cmd -> Objects.requireNonNull(cmd.getExecutor()).hasPermission("NotsoSimpleClaims.DevCommands"))
                        .then(Commands.literal("claim"))
                        .then(Commands.literal("abandon"))
                        .build()
        );
    }
}
