package io.github.nissemanen.notsoSimpleClaims.Claiming.listeners;

import io.github.nissemanen.notsoSimpleClaims.Claiming.ClaimManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class EntityListenerClaims implements Listener {
    private final ClaimManager claimManager;
    private final JavaPlugin plugin;

    public EntityListenerClaims(ClaimManager claimManager, JavaPlugin plugin) {
        this.claimManager = claimManager;
        this.plugin = plugin;
    }

    private boolean isPlayerNotAllowedToBuild(Player player, Chunk chunk) {
        return claimManager.isChunkClaimed(chunk) && !claimManager.isChunkClaimedBy(player, chunk);
    }

    private <T extends Event & Cancellable> void handleSimpleCancel(Chunk chunk, Player player, T e) {
        if (isPlayerNotAllowedToBuild(player, chunk)) {
            e.setCancelled(true);
            player.sendActionBar(Component.text("fuck you").color(NamedTextColor.GREEN));
        }
    }

    @EventHandler
    final void entityExplodeEvent(EntityExplodeEvent e) {
        /*
        claims:
          defaultClaimSettings:
            entityExplode: bool
         */
        if (plugin.getConfig().getBoolean("claims.defaultClaimSettings.entityExplode")) return;

        if (!(e.getEntity() instanceof Creeper creeper)) return;

        Chunk chunk = e.getEntity().getChunk();

        if (!claimManager.isChunkClaimed(chunk)) return;

        int radius = creeper.isPowered()? 7 : 4;
        List<Entity> nearbyEntities = e.getEntity().getNearbyEntities(radius, radius, radius);

        boolean ownerIsTarget = nearbyEntities.stream()
                .filter(entity -> e instanceof Player)
                .map(entity -> (Player) entity)
                .anyMatch(player -> claimManager.isChunkClaimedBy(player, chunk));

        if (!ownerIsTarget) {
            e.blockList().clear();
        }
    }
}
