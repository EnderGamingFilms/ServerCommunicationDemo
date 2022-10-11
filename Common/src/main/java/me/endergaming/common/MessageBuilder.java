package me.endergaming.common;

import me.endergaming.common.grpc.Communication;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MessageBuilder {
    public static Communication.UUID buildUUID(long leastSig, long mostSig) {
        return Communication.UUID.newBuilder()
                .setLeastSignificantBits(leastSig)
                .setMostSignificantBits(mostSig)
                .build();
    }

    public static Communication.UUID buildUUID(UUID uuid) {
        return buildUUID(uuid.getLeastSignificantBits(), uuid.getMostSignificantBits());
    }

    public static Communication.Player buildPlayer(UUID uuid, String name) {
        return Communication.Player.newBuilder()
                .setUuid(buildUUID(uuid))
                .setName(name)
                .build();
    }

    public static Communication.Empty buildEmpty() {
        return Communication.Empty.newBuilder().build();
    }

    public static Communication.Stats buildStats(Stats stats) {
        Communication.Stats.Builder builder = Communication.Stats.newBuilder();

        builder.setDamageDealt(stats.damageDealt);
        builder.setDamageTaken(stats.damageTaken);
        builder.setDeaths(stats.deaths);
        builder.setJoins(stats.joins);
        builder.setItemsDropped(stats.itemsDropped);
        builder.setBlockedMined(stats.blocksMined);
        builder.setBlocksPlaced(stats.blocksPlaced);

        Communication.Kills.Builder killsBuilder = Communication.Kills.newBuilder();

        int total = 0;

        List<Communication.Typed_Kill> killInfo = new ArrayList<>();

        for (var entry : stats.killsMap.entrySet()) {
            Communication.Typed_Kill typed_kill = Communication.Typed_Kill.newBuilder()
                    .setType(entry.getKey())
                    .setAmount(entry.getValue())
                    .build();

            killInfo.add(typed_kill);

            total += entry.getValue();
        }

        killsBuilder.addAllTyped(killInfo);
        killsBuilder.setTotal(total);

        builder.setKills(killsBuilder.build());

        return builder.build();
    }

    public static Communication.Server_Info buildServerInfo(String name, int onlinePlayers, int maxPlayers) {
        return Communication.Server_Info.newBuilder()
                .setName(name)
                .setOnlinePlayers(onlinePlayers)
                .setMaxPlayers(maxPlayers)
                .build();
    }

    public static Communication.Text buildText(String text) {
        return Communication.Text.newBuilder()
                .setText(text)
                .build();
    }

    public static Communication.PlayerStatsRequest buildPlayerStatsRequest(UUID uuid, String name) {
        return Communication.PlayerStatsRequest.newBuilder()
                .setPlayer(buildPlayer(uuid, name))
                .build();
    }

    public static Communication.StatsConnectionResponse buildStatsConnectionResponse(UUID uuid, String name, Stats stats) {
        return Communication.StatsConnectionResponse.newBuilder()
                .setPlayer(buildPlayer(uuid, name))
                .setStats(buildStats(stats))
                .build();
    }

    public static UUID toJavaUUID(Communication.UUID uuid) {
        return new UUID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }
}