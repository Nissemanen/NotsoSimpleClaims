package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import io.papermc.paper.event.block.PlayerShearBlockEvent;
import io.papermc.paper.event.player.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class PlayerListenerClaims implements Listener {
    private final ClaimManager claimManager;

    public PlayerListenerClaims(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    private boolean isPlayerNotAllowedToBuild(Player player, Chunk chunk) {
        return claimManager.isChunkClaimed(chunk) && !claimManager.isChunkClaimedBy(player, chunk);
    }

    private <T extends Event & Cancellable> void handleSimpleCancel(Chunk chunk, Player player, T e) {
        if (isPlayerNotAllowedToBuild(player, chunk)) {
            e.setCancelled(true);
            player.sendActionBar(Component.text("fuck you").color(NamedTextColor.RED));
        }
    }

    // PlayerChangeBeaconEvent *

    // PlayerFlowerpotManipulateEvent
    @EventHandler
    final void playerFlowerpotManipulateEvent(PlayerFlowerPotManipulateEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getFlowerpot().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerInsertLecternBookEvent
    @EventHandler
    final void playerInsertLecternBookEvent(PlayerInsertLecternBookEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getLectern().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerItemFrameChangeEvent
    @EventHandler
    final void playerItemFrameChangeEvent(PlayerItemFrameChangeEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getItemFrame().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerLecternPageChangeEvent
    @EventHandler
    final void playerLecternPageChangeEvent(PlayerLecternPageChangeEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getLectern().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerNameEntityEvent
    @EventHandler
    final void playerNameEntityEvent(PlayerNameEntityEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getEntity().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerOpenSignEvent
    @EventHandler
    final void playerOpenSignEvent(PlayerNameEntityEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getEntity().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerTradeEvent *

    // PrePlayerAttackEntityEvent *

    // PlayerArmorStandManipulateEvent
    @EventHandler
    final void playerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getRightClicked().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerBucketEmptyEvent ?

    // PlayerBucketEntityEvent
    @EventHandler
    final void playerBucketEntityEvent(PlayerBucketEntityEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getPlayer().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerBucketEvent ?

    // PlayerBucketFillEvent ?

    // PlayerDropItemEvent *

    // PlayerEggThrowEvent *

    // PlayerFishEvent *

    // PlayerHarvestBlockEvent *

    // PlayerInteractAtEntityEvent *

    // PlayerInteractEvent
    @EventHandler
    final void playerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getClickedBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerPortalEvent
    @EventHandler
    final void playerPortalEvent(PlayerPortalEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getTo().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerShearEntityEvent
    @EventHandler
    final void playerShearEntityEvent(PlayerShearEntityEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getEntity().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerShearBlockEvent
    @EventHandler
    final void playerShearBlockEvent(PlayerShearBlockEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerTakeLecternBookEvent
    @EventHandler
    final void playerTakeLecternBookEvent(PlayerTakeLecternBookEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getLectern().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerSetSpawnEvent *
}
