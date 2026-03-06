package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListenerClaims implements Listener {
    private final ClaimManager claimManager;

    public PlayerListenerClaims(ClaimManager claimManager) {
        this.claimManager = claimManager;
    }

    private boolean isPlayerNotAllowedToBuild(Player player, Chunk chunk) {
        return claimManager.isChunkClaimed(chunk) && !claimManager.isChunkClaimedBy(player, chunk);
    }

    private <T extends Event & Cancellable> void handleSimpleCancel(Chunk chunk, Player player, T e) {
        if (isPlayerNotAllowedToBuild(player, chunk))
            e.setCancelled(true);
    }

    // PlayerChangeBeaconEvent

    // PlayerFlowerpotManipulateEvent

    // PlayerInsertLecternBookEvent

    // PlayerItemFrameChangeEvent

    // PlayerLecternPageChangeEvent

    // PlayerNameEntityEvent

    // PlayerOpenSignEvent

    // PlayerTradeEvent *

    // PrePlayerAttackEntityEvent *

    // PlayerArmorStandManipulateEvent

    // PlayerBucketEmptyEvent ?

    // PlayerBucketEntityEvent

    // PlayerBucketEvent ?

    // PlayerBucketFillEvent ?

    // PlayerDropItemEvent *

    // PlayerEggThrowEvent *

    // PlayerFishEvent *

    // PlayerHarvestBlockEvent *

    // PlayerInteractAtEntityEvent *

    // PlayerInteractEvent
    final void playerInteractEvent(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Chunk chunk = e.getClickedBlock().getChunk();

        handleSimpleCancel(chunk, player, e);
    }

    // PlayerPortalEvent

    // PlayerShearEvent

    // PlayerTakeLecternBookEvent

    // PlayerSetSpawnEvent *
}
