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
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jspecify.annotations.NonNull;

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

    private <T extends Event & Cancellable> void handleHardCancel(Chunk chunk, Player player, T e) {
        if (claimManager.isChunkClaimed(chunk)) {
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
        Entity attacked = e.getAttacked();

        if (!plugin.getConfig().getBoolean("claims.defaultClaimSettings.attackHarmfullEntities") && attacked instanceof Monster) {
            handleSimpleCancel(attacked.getChunk(), e.getPlayer(), e);
            return;
        }

        if(!plugin.getConfig().getBoolean("claims.defaultClaimSettings.attackFriendlyEntities") && (
                attacked instanceof Animals ||
                attacked instanceof Tameable ||
                attacked instanceof NPC ||
                attacked instanceof Villager)) {
            handleSimpleCancel(attacked.getChunk(), e.getPlayer(), e);
            return;
        }

        if(!plugin.getConfig().getBoolean("claims.defaultClaimSettings.attackTamedEntities") && attacked instanceof Tameable) {
            e.setCancelled(
                    Objects.equals(
                            ((Tameable) attacked).getOwnerUniqueId(),
                            claimManager.getOwnerOf(e.getAttacked().getChunk()))
            );
            return;
        }

        if(!plugin.getConfig().getBoolean("claims.defaultClaimSettings.attackPlayers") && attacked instanceof Player)
            handleHardCancel(attacked.getChunk(), e.getPlayer(), e);
    }

    // PlayerArmorStandManipulateEvent
    @EventHandler
    final void playerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent e) {
        /*
        claims:
          defaultClaimSettings:
            armorStandManipulate: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.armorStandManipuilate")) return;

        handleSimpleCancel(e.getRightClicked().getChunk(), e.getPlayer(), e);
    }

    // PlayerBucketEmptyEvent ?

    // PlayerBucketEntityEvent
    @EventHandler
    final void playerBucketEntityEvent(PlayerBucketEntityEvent e) {
        /*
        claims:
          defaultClaimSettings:
            bucketEntity: bool
         */
        if (plugin.getConfig().getBoolean("claims.defauiltClaimSettings.bucketEntity")) return;

        handleSimpleCancel(e.getEntity().getChunk(), e.getPlayer(), e);
    }

    // PlayerBucketEvent ?

    // PlayerBucketFillEvent ?

    // PlayerDropItemEvent *

    // PlayerEggThrowEvent *

    // PlayerFishEvent *

    // PlayerHarvestBlockEvent *

    // PlayerInteractAtEntityEvent *
    @EventHandler
    final void playerInteractAtEntityEvent(PlayerInteractAtEntityEvent e) {
        /*
        claims:
          defaultClaimSettings:
            interactWithEntity: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.interactWithEntity")) return;

        handleSimpleCancel(e.getRightClicked().getChunk(), e.getPlayer(), e);
    }

    // PlayerInteractEvent
    @EventHandler
    final void playerInteractEvent(PlayerInteractEvent e) {
        /*
        claims:
          defaultClaimSettings:
            interact: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.interact")) return;

        handleSimpleCancel(e.getInteractionPoint().getChunk(), e.getPlayer(), e);
    }

    // PlayerPortalEvent
    @EventHandler
    final void playerPortalEvent(PlayerPortalEvent e) {
        /*
        claims:
          defaultClaimSettings:
            useNetherPortal: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.useNetherPortal") && e.getCause() != PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) return;

        handleSimpleCancel(e.getFrom().getChunk(), e.getPlayer(), e);
        handleSimpleCancel(e.getTo().getChunk(), e.getPlayer(), e);
    }

    @EventHandler
    final void portalCreateEvent(PortalCreateEvent e) {
        /*
        claims:
          defaultClaimSettings:
            createNetherPortal: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.createNetherPortal") && e.getReason() != PortalCreateEvent.CreateReason.NETHER_PAIR && !(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();

        e.setCancelled(
                e.getBlocks().stream().anyMatch(
                        blockState -> claimManager.isChunkClaimedBy(player, blockState.getChunk())
                )
        );
    }

    // PlayerShearEntityEvent
    @EventHandler
    final void playerShearEntityEvent(PlayerShearEntityEvent e) {
        /*
        claims:
          defaultClaimSettings:
            shearEntity: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.shearEntity")) return;

        handleSimpleCancel(e.getEntity().getChunk(), e.getPlayer(), e);
    }

    // PlayerShearBlockEvent
    @EventHandler
    final void playerShearBlockEvent(PlayerShearBlockEvent e) {
        /*
        claims:
          defaultClaimSettings:
            shearBlock: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.shearBlock")) return;

        handleSimpleCancel(e.getBlock().getChunk(), e.getPlayer(), e);
    }

    // PlayerTakeLecternBookEvent
    @EventHandler
    final void playerTakeLecternBookEvent(PlayerTakeLecternBookEvent e) {
        /*
        claims:
          defaultClaimSettings:
            takeLecternBook: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.takeLecternBook")) return;

        handleSimpleCancel(e.getLectern().getChunk(), e.getPlayer(), e);
    }

    // PlayerSetSpawnEvent *
}
