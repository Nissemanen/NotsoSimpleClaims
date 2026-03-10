package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import com.destroystokyo.paper.event.block.BeaconEffectEvent;
import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import io.papermc.paper.event.block.VaultChangeStateEvent;
import io.papermc.paper.event.entity.EntityCompostItemEvent;
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
import org.bukkit.potion.PotionEffect;

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

        if (e.isCancelled()) return;

        e.setEffect(
                e.getEffect().withAmplifier(
                        (int) Math.round(
                                e.getEffect().getAmplifier() * Math.max(Math.abs(plugin.getConfig().getDouble("claims.defaultSettings.beaconEffectMultiplier")), 1.0)
                        )
                )
        );
    }

    @EventHandler
    final void tntPrimeEvent(TNTPrimeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            tntPriming: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.tntPriming") || !(e.getPrimingEntity() instanceof Player player)) return;

        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    @EventHandler
    final void vaultChangeStateEvent(VaultChangeStateEvent e) {
        /*
        claims:
          defaultClaimSettings:
            changeVaultState: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.changeVaultState")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void blockBreakEvent(BlockBreakEvent e) {
        /*
        claims:
          defaultClaimSettings:
            breakBlocks: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.breakBlocks")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void blockCanBuildEvent(BlockCanBuildEvent e) {
        /*
        claims:
          defaultClaimSettings:
            buildBlocks: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.buildBlocks")) return;

        if (playerHasNoPerms(e.getPlayer(), e.getBlock().getChunk()))
            e.setBuildable(false);
    }

    @EventHandler
    final void blockFertilizeEvent(BlockFertilizeEvent e) {
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.fertilizeBlocks")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    // BlockIgniteEvent
    @EventHandler
    final void blockIgniteEvent(BlockIgniteEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // BlockPlaceEvent
    @EventHandler
    final void blockPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // NotePlayEvent *

    // SignChangeEvent ?

}
