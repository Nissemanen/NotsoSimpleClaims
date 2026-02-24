package io.github.nissemanen.notsoSimpleClaims;

import org.bukkit.entity.Player;

import java.util.*;

public class ClaimManager {
    private Map<UUID, Set<Chunk>> uuidToChunkSet = new HashMap<>();
    private Map<Chunk, UUID> chunkToUuid = new HashMap<>();

    public UUID getOwnerOf(Chunk chunk) { return chunkToUuid.get(chunk); }

    public Set<Chunk> getOwnedChunksOf(Player player) { return this.getOwnedChunksOf(player.getUniqueId()); }
    public Set<Chunk> getOwnedChunksOf(UUID player) { return uuidToChunkSet.get(player); }

    public boolean isChunkClaimed(Chunk chunk) { return chunkToUuid.containsKey(chunk); }

    public boolean isChunkClaimedBy(Player player, Chunk chunk) { return this.isChunkClaimedBy(player.getUniqueId(), chunk); }
    public boolean isChunkClaimedBy(UUID player, Chunk chunk) { return this.isChunkClaimed(chunk) && chunkToUuid.get(chunk).equals(player); }

    public boolean claimChunk(Player player, Chunk chunk) { return this.claimChunk(player.getUniqueId(), chunk); }
    public boolean claimChunk(UUID player, Chunk chunk) {
        boolean success = chunkToUuid.putIfAbsent(chunk, player) == null;

        if (success) {
            uuidToChunkSet
                    .computeIfAbsent(player, uuid -> new HashSet<>())
                    .add(chunk);
        }

        return success;
    }

    public boolean abandonChunk(Player player, Chunk chunk) { return this.abandonChunk(player.getUniqueId(), chunk); }
    public boolean abandonChunk(UUID player, Chunk chunk) {
        if (!this.isChunkClaimedBy(player, chunk)) { return false; }

        chunkToUuid.remove(chunk);
        uuidToChunkSet.get(player).remove(chunk);

        return true;
    }

    // Database things

}
