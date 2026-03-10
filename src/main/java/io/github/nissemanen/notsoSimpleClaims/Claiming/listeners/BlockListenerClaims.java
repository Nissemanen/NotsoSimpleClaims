package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import com.destroystokyo.paper.event.block.BeaconEffectEvent;
import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
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

public class BlockListenerClaims implements Listener {
    private final ClaimManager claimManager;
    private final JavaPlugin plugin;

    public BlockListenerClaims(ClaimManager claimManager, JavaPlugin plugin) {
        this.claimManager = claimManager;
        this.plugin = plugin;
    }

    private boolean isPlayerNotAllowedToBuild(Player player, Chunk chunk) {
        return claimManager.isChunkClaimed(chunk) && !claimManager.isChunkClaimedBy(player, chunk);
    }

    private <T extends Event & Cancellable> void handleSimpleCancel(Chunk chunk, Player player, T e) {
        if (isPlayerNotAllowedToBuild(player, chunk)) {
            e.setCancelled(true);
            player.sendActionBar(Component.text("fuck you").color(NamedTextColor.BLUE));
        }
    }

    // BeaconEffectEvent *
    @EventHandler
    final void beaconEffectEvent(BeaconEffectEvent e) {
        /*
        settings:
        claims:
          defaultClaimSettings:
            beaconEffect: bool
            beaconEffectMultiplier: ufloat < 1.0
         */

        boolean beaconEffect = plugin.getConfig().getBoolean("claims.defaultClaimSettings.beaconEffect");



        if (!())
    }

    // TNTPrimeEvent
    @EventHandler
    final void tntPrimeEvent(TNTPrimeEvent e) {
        if (!(e.getPrimingEntity() instanceof Player player))
            return;

        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // BlockBreakBlockEvent *

    // VaultChangeStateEvent *

    // EntityCompostItemEvent
    @EventHandler
    final void entityCompostItemEvent(EntityCompostItemEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return;
        }

        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // BlockBreakEvent
    @EventHandler
    final void blockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // BlockCanBuildEvent (+BlockPlaceEvent)
    @EventHandler
    final void blockCanBuildEvent(BlockCanBuildEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();

        if (isPlayerNotAllowedToBuild(player, chunk))
            e.setBuildable(false);
    }

    // BlockFertilizeEvent
    @EventHandler
    final void blockFertilizeEvent(BlockFertilizeEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
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
