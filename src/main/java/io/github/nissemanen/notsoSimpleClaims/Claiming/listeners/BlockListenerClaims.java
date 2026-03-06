package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;

public class BlockListenerClaims implements Listener {
    private final ClaimManager claimManager;

    public BlockListenerClaims(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    private boolean isPlayerNotAllowedToBuild(Player player, Chunk chunk) {
        return claimManager.isChunkClaimed(chunk) && !claimManager.isChunkClaimedBy(player, chunk);
    }

    private <T extends Event & Cancellable> void handleSimpleCancel(Chunk chunk, Player player, T e) {
        if (isPlayerNotAllowedToBuild(player, chunk))
            e.setCancelled(true);
    }

    // BeaconEffectEvent *

    // TNTPrimeEvent
    @EventHandler
    final void tntPrimeEvent(TNTPrimeEvent e) {
        Entity primingEntity = e.getPrimingEntity();

        if (!(primingEntity instanceof Player player))
            return;

        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // BlockBreakBlockEvent *

    // VaultChangeStateEvent *

    // EntityCompostItemEvent

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
