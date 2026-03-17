package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import com.destroystokyo.paper.event.block.BeaconEffectEvent;
import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import io.papermc.paper.event.block.VaultChangeStateEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockListenerClaims implements Listener {
    private final ClaimManager claimManager;
    private final JavaPlugin plugin;

    public BlockListenerClaims(ClaimManager claimManager, JavaPlugin plugin) {
        this.claimManager = claimManager;
        this.plugin = plugin;
    }

    private boolean playerHasNoPerms(Player player, Chunk chunk) {
        return claimManager.isChunkClaimed(chunk) && !claimManager.isChunkClaimedBy(player, chunk);
    }

    private <T extends Event & Cancellable> void handleSimpleCancel(Chunk chunk, Player player, T e) {
        if (playerHasNoPerms(player, chunk)) {
            e.setCancelled(true);
            player.sendActionBar(Component.text("fuck you").color(NamedTextColor.BLUE));
        }
    }

    @EventHandler
    final void beaconEffectEvent(BeaconEffectEvent e) {
        /* Configurations
        claims:
          defaultClaimSettings:
            beaconEffect: bool
            beaconEffectMultiplier: uDouble < 1.0
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.beaconEffect")) return;

        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);

//      currently it doesn't really work
//        if (e.isCancelled()) return;
//
//        e.setEffect(
//                e.getEffect().withAmplifier(
//                        (int) Math.round(
//                                e.getEffect().getAmplifier() * Math.max(Math.abs(plugin.getConfig().getDouble("claims.defaultSettings.beaconEffectMultiplier")), 1.0)
//                        )
//                )
//        );
    }

    @EventHandler
    final void tntPrimeEvent(TNTPrimeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            tntPrime: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.tntPrime") || !(e.getPrimingEntity() instanceof Player player)) return;

        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    @EventHandler
    final void vaultChangeStateEvent(VaultChangeStateEvent e) {
        /*
        claims:
          defaultClaimSettings:
            vaultChangeState: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.vaultChangeState")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void blockBreakEvent(BlockBreakEvent e) {
        /*
        claims:
          defaultClaimSettings:
            blockBreak: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.blockBreak")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void blockCanBuildEvent(BlockCanBuildEvent e) {
        /*
        claims:
          defaultClaimSettings:
            blockCanBuild: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.blockCanBuild")) return;

        if (playerHasNoPerms(e.getPlayer(), e.getBlock().getChunk()))
            e.setBuildable(false);
    }

    @EventHandler
    final void blockFertilizeEvent(BlockFertilizeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            blockFertilize: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.blockFertilize")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void blockIgniteEvent(BlockIgniteEvent e) {
        /*
        claims:
          defaultClaimSettings:
            blockIgnite: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.blockIgnite")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void blockPlaceEvent(BlockPlaceEvent e) {
        /*
        claims:
          defaultClaimSettings:
            blockPlace: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.blockPlace")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void signChangeEvent(SignChangeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            signChange: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }
}
