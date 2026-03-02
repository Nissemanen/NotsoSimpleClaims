package io.github.nissemanen.notsoSimpleClaims.Claiming;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jspecify.annotations.NonNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ClaimManager {
    private Map<UUID, Set<Claim>> uuidToChunkSet = new HashMap<>();
    private Map<Claim, UUID> claimToUuid = new HashMap<>();

    public UUID getOwnerOf(Claim claim) { return claimToUuid.get(claim); }

    public Set<Claim> getOwnedChunksOf(@NonNull Player player) { return this.getOwnedChunksOf(player.getUniqueId()); }
    public Set<Claim> getOwnedChunksOf(UUID player) { return uuidToChunkSet.get(player); }

    public Map<UUID, Set<Claim>> getUuidToChunkSet() { return this.uuidToChunkSet; }
    public Map<Claim, UUID> getClaimToUuid() { return this.claimToUuid; }

    public boolean isChunkClaimed(Claim claim) { return claimToUuid.containsKey(claim); }

    public boolean isChunkClaimedBy(@NonNull Player player, Claim claim) { return this.isChunkClaimedBy(player.getUniqueId(), claim); }
    public boolean isChunkClaimedBy(UUID player, Claim claim) { return this.isChunkClaimed(claim) && claimToUuid.get(claim).equals(player); }

    public boolean claimChunk(@NonNull Player player, Claim claim) { return this.claimChunk(player.getUniqueId(), claim); }
    public boolean claimChunk(UUID player, Claim claim) {
        boolean success = claimToUuid.putIfAbsent(claim, player) == null;

        if (success) {
            uuidToChunkSet
                    .computeIfAbsent(player, uuid -> new HashSet<>())
                    .add(claim);
        }

        return success;
    }

    public boolean abandonChunk(Player player, Claim claim) { return this.abandonChunk(player.getUniqueId(), claim); }
    public boolean abandonChunk(UUID player, Claim claim) {
        if (!this.isChunkClaimedBy(player, claim)) { return false; }

        claimToUuid.remove(claim);
        uuidToChunkSet.compute(player, (uuid, claims) -> {
            if (claims == null) return null;

            claims.remove(claim);

            return claims.isEmpty() ? null : claims;
        });


        return true;
    }

    public void setClaimData(Map<Claim, UUID> claimData) {
        this.claimToUuid = claimData;

        for (Map.Entry<Claim, UUID> entry : claimData.entrySet()) {
            uuidToChunkSet
                    .computeIfAbsent(entry.getValue(), s -> new HashSet<>())
                    .add(entry.getKey());
        }
    }

    // Database things
    private final HikariDataSource dataSource;

    public ClaimManager(HikariDataSource dataSource) {
        this.dataSource = dataSource;

        this.readyDataBase();
        this.setClaimData(this.getChunkToUuidFromDB());
    }

    public void readyDataBase() {
        try (final Connection connection = this.dataSource.getConnection();
             final PreparedStatement statement = connection.prepareStatement("""
CREATE TABLE IF NOT EXISTS claims (
uuid TEXT NOT NULL,
world TEXT NOT NULL,
x INTEGER NOT NULL,
z INTEGER NOT NULL,
PRIMARY KEY (world, x, z))
""")) {
            statement.execute();

        } catch (SQLException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    public Map<Claim, UUID> getChunkToUuidFromDB() {
        Map<Claim, UUID> result = new HashMap<>();

        try (
                final Connection connection = this.dataSource.getConnection();
                final PreparedStatement statement = connection.prepareStatement("SELECT * FROM claim")
                ) {
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UUID uuid = UUID.fromString(resultSet.getString("uuid"));
                Claim claim = new Claim(
                        Bukkit.getWorld(resultSet.getString("world")),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                );

                result.put(claim, uuid);
            }

        } catch (SQLException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }

        return result;
    }
}
