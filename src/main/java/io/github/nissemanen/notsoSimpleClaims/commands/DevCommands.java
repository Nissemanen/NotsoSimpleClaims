package io.github.nissemanen.notsoSimpleClaims.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.arguments.StringArgumentType;
import io.github.nissemanen.notsoSimpleClaims.Blocks.items.CapitalMarkerItem;
import io.github.nissemanen.notsoSimpleClaims.Blocks.listeners.ListenerForCapitalBlockRelated;
import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class DevCommands {
    private final Plugin plugin;
    private final ClaimManager claimManager;
    private final ListenerForCapitalBlockRelated playerListenerCapitalBlock;

    public DevCommands(Plugin plugin, ClaimManager claimManager, ListenerForCapitalBlockRelated playerListenerCapitalBlock) {
        this.plugin = plugin;
        this.claimManager = claimManager;
        this.playerListenerCapitalBlock = playerListenerCapitalBlock;
    }

    public void register(Commands registrar) {
        registrar.register(
                Commands.literal("DevClaims").requires(cmd -> cmd.getSender().hasPermission("NotsoSimpleClaims.DevCommands"))
                        .then(Commands.literal("claim")
                                .then(Commands.argument("name", StringArgumentType.greedyString()).requires(cxt -> (cxt.getSender() instanceof Player)).executes(cmd -> {
                                    Player player = Bukkit.getPlayer(cmd.getArgument("name", String.class));

                                    if (player == null) return 0;

                                    claimManager.claimChunk(player, ((Player) cmd.getSource().getSender()).getChunk());

                                    return Command.SINGLE_SUCCESS;
                                })))
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
                        .then(Commands.literal("print_to_console").executes(cmd -> {
                            claimManager.printToConsole(plugin);

                            return Command.SINGLE_SUCCESS;
                        }))
                        .then(Commands.literal("print_capital_blocks").executes(cmd -> {
                            plugin.getLogger().info(playerListenerCapitalBlock.getCapitalBlocks().toString());

                            return Command.SINGLE_SUCCESS;
                        }))
                        .build()
        );
    }
}
