package me.endergaming.common;

import com.google.protobuf.BoolValue;
import com.google.protobuf.DoubleValue;
import com.google.protobuf.FloatValue;
import com.google.protobuf.Int32Value;
import com.google.protobuf.Int64Value;
import com.google.protobuf.StringValue;
import me.endergaming.common.grpc.Communication;

import java.util.ArrayList;
import java.util.Collection;
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

    public static Communication.Number buildNumber(int number) {
        return Communication.Number.newBuilder()
                .setInt32(Int32Value.newBuilder().setValue(number).build())
                .build();
    }

    public static Communication.Number buildNumber(long number) {
        return Communication.Number.newBuilder()
                .setInt64(Int64Value.newBuilder().setValue(number).build())
                .build();
    }

    public static Communication.Number buildNumber(double number) {
        return Communication.Number.newBuilder()
                .setFloat64(DoubleValue.newBuilder().setValue(number).build())
                .build();
    }

    public static Communication.Number buildNumber(float number) {
        return Communication.Number.newBuilder()
                .setFloat32(FloatValue.newBuilder().setValue(number).build())
                .build();
    }

    public static Communication.Value buildValue(String value) {
        return Communication.Value.newBuilder()
                .setStringValue(StringValue.newBuilder().setValue(value).build())
                .build();
    }

    public static Communication.Value buildValue(Communication.Number number) {
        return Communication.Value.newBuilder()
                .setNumber(number)
                .build();
    }

    public static Communication.Value buildValue(boolean bool) {
        return Communication.Value.newBuilder()
                .setBoolValue(BoolValue.newBuilder().setValue(bool).build())
                .build();
    }

    public static Communication.Value buildValue(Communication.MultiStat multiStat) {
        return Communication.Value.newBuilder()
                .setStatsList(multiStat)
                .build();
    }

    public static Communication.Stat buildStat(String stat, Communication.Value value) {
        return Communication.Stat.newBuilder()
                .setKey(stat)
                .setValue(value)
                .build();
    }

    public static Communication.MultiStat buildMultiStat(Collection<Communication.Stat> stats) {
        return Communication.MultiStat.newBuilder()
                .addAllStats(stats)
                .build();
    }

    public static Communication.MultiStat buildMultiStat(Communication.Stat... stats) {
        return Communication.MultiStat.newBuilder()
                .addAllStats(List.of(stats))
                .build();
    }

    public static Communication.MultiStat buildMultiStat(Stats stats) {
        Communication.MultiStat.Builder multiStatBuilder = Communication.MultiStat.newBuilder();

        multiStatBuilder.addStats(buildStat("damage_dealt", buildValue(buildNumber(stats.damageDealt))));
        multiStatBuilder.addStats(buildStat("damage_taken", buildValue(buildNumber(stats.damageTaken))));
        multiStatBuilder.addStats(buildStat("deaths", buildValue(buildNumber(stats.deaths))));
        multiStatBuilder.addStats(buildStat("joins", buildValue(buildNumber(stats.joins))));
        multiStatBuilder.addStats(buildStat("items_dropped", buildValue(buildNumber(stats.itemsDropped))));
        multiStatBuilder.addStats(buildStat("blocks_mined", buildValue(buildNumber(stats.blocksMined))));
        multiStatBuilder.addStats(buildStat("blocks_placed", buildValue(buildNumber(stats.blocksPlaced))));

        Communication.MultiStat.Builder killsBuilder = Communication.MultiStat.newBuilder();

        int total = 0;

        List<Communication.Stat> killInfo = new ArrayList<>();

        for (var entry : stats.killsMap.entrySet()) {
            killInfo.add(buildStat(entry.getKey(), buildValue(buildNumber(entry.getValue()))));

            total += entry.getValue();
        }

        killsBuilder.addAllStats(killInfo);

        multiStatBuilder.addStats(buildStat("total_kills", buildValue(buildNumber(total))));

        multiStatBuilder.addStats(buildStat("kills", buildValue(killsBuilder.build())));

        return multiStatBuilder.build();
    }

    public static Communication.StatStreamResponse buildStatStreamResponse(String owner, Stats stats) {
        return Communication.StatStreamResponse.newBuilder()
                .setOwner(owner)
                .setStats(buildMultiStat(stats))
                .build();
    }

    public static UUID toJavaUUID(Communication.UUID uuid) {
        return new UUID(uuid.getMostSignificantBits(), uuid.getLeastSignificantBits());
    }

    public static Number toJavaNumber(Communication.Number number) {
        if (number.hasInt32()) {
            return number.getInt32().getValue();
        } else if (number.hasInt64()) {
            return number.getInt64().getValue();
        } else if (number.hasFloat32()) {
            return number.getFloat32().getValue();
        } else if (number.hasFloat64()) {
            return number.getFloat64().getValue();
        } else {
            throw new IllegalArgumentException("Unknown number type");
        }
    }
}