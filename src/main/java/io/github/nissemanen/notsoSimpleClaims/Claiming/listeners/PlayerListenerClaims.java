package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import io.papermc.paper.event.block.PlayerShearBlockEvent;
import io.papermc.paper.event.player.*;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Chunk;
import org.bukkit.entity.*;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.plugin.java.JavaPlugin;

import javax.management.monitor.Monitor;
import java.util.Objects;

public class PlayerListenerClaims implements Listener {
    private final ClaimManager claimManager;
    private final JavaPlugin plugin;

    public PlayerListenerClaims(ClaimManager claimManager, JavaPlugin plugin) {
        this.claimManager = claimManager;
        this.plugin = plugin;
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

    @EventHandler
    final void playerFlowerpotManipulateEvent(PlayerFlowerPotManipulateEvent e) {
        /*
        claims:
          defaultClaimSettings:
            flowerpotManipulate: bool (false)
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.flowerpotManipulate")) return;

        handleSimpleCancel(e.getFlowerpot().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void playerInsertLecternBookEvent(PlayerInsertLecternBookEvent e) {
        /*
        claims:
          defaultClaimSettings:
            insertLecternBook: bool (false)
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.insertLecternBook")) return;

        handleSimpleCancel(e.getLectern().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void playerItemFrameChangeEvent(PlayerItemFrameChangeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            itemFrameChange: bool (false)
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.itemFrameChange")) return;

        handleSimpleCancel(e.getItemFrame().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void playerLecternPageChangeEvent(PlayerLecternPageChangeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            lecternPageChange: bool (false)
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.lecternPageChange")) return;

        handleSimpleCancel(e.getLectern().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void playerNameEntityEvent(PlayerNameEntityEvent e) {
        /*
        claims:
          defaultClaimSettings:
            nameEntity: bool (false)
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.nameEntity")) return;

        handleSimpleCancel(e.getEntity().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void playerOpenSignEvent(PlayerNameEntityEvent e) {
        /*
        claims:
          defaultClaimSettings:
            openSignEvent: bool (false)
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.openSignEvent")) return;

        handleSimpleCancel(e.getEntity().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void playerTradeEvent(PlayerTradeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            trade: bool (false)
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.trade")) return;

        handleSimpleCancel(e.getMerchant().getChunk(), e.getPlayer(), e);
    }

    // PrePlayerAttackEntityEvent *
    @EventHandler
    final void prePlayerAttackEntityEvent(PrePlayerAttackEntityEvent e) {
        /*
        claims:
          defaultClaimSettings:
            attackHarmfulEntities: bool
            attackFriendlyEntities: bool
            attackTamedEntities: bool
            attackPlayers: bool
         */
        if (!plugin.getConfig().getBoolean("claims.defaultClaimSettings.attackHarmfullEntities") && e.getAttacked() instanceof Monster) {
            handleSimpleCancel(e.getAttacked().getChunk(), e.getPlayer(), e);
            return;
        }

        if(!plugin.getConfig().getBoolean("claims.defaultClaimSettings.attackFriendlyEntities") && (
                e.getAttacked() instanceof Animals ||
                e.getAttacked() instanceof Tameable ||
                e.getAttacked() instanceof NPC ||
                e.getAttacked() instanceof Villager)) {
            handleSimpleCancel(e.getAttacked().getChunk(), e.getPlayer(), e);
            return;
        }

        if(!plugin.getConfig().getBoolean("claims.defaultClaimSettings.attackTamedEntities") && e.getAttacked() instanceof Tameable) {
            e.setCancelled(Objects.equals(((Tameable) e.getAttacked()).getOwnerUniqueId(), claimManager.getOwnerOf(e.getAttacked().getChunk())));
        }
    }

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
