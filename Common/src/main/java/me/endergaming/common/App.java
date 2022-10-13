package me.endergaming.common;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import me.endergaming.common.grpc.Communication;
import me.endergaming.common.grpc.CommunicationsGrpc;

import java.util.UUID;

public class App {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder
                .forAddress("localhost", 3009)
                .usePlaintext()
                .build();

        CommunicationsGrpc.CommunicationsBlockingStub stub = CommunicationsGrpc.newBlockingStub(channel);

        new Thread(() -> {
            var it = stub
                    .withWaitForReady()
                    .establishCustomStatsConnection(MessageBuilder.buildEmpty());

            while (true) {
                if (it.hasNext()) {
                    System.out.println("\n\n\n\n\n\n\n\n------------------------------------------------------");
                    Communication.StatStreamResponse response = it.next();

                    Communication.MultiStat stats = response.getStats();
                    String owner = response.getOwner();

                    System.out.println("Stats for " + owner);

                    for (Communication.Stat stat : stats.getStatsList()) {
                        String key = fix(stat.getKey(), true);
                        Communication.Value value = stat.getValue();

                        printValue(key, value);
                    }

//                    stats.getKills().getTypedList().forEach(kill -> System.out.printf("%s | %d%n", kill.getType(), kill.getAmount()));
                }
            }
        }).start();
    }

    private static void printValue(String key, Communication.Value value) {
        if (value.hasNumber()) {
            System.out.println(key + ": " + MessageBuilder.toJavaNumber(value.getNumber()));
        } else if (value.hasBoolValue()) {
            System.out.println(key + ": " + value.getBoolValue());
        } else if (value.hasStringValue()) {
            System.out.println(key + ": " + value.getStringValue());
        } else if (value.hasStatsList()) {
            System.out.println(fix(key, false));
            for (Communication.Stat stat : value.getStatsList().getStatsList()) {
                printValue("  " + stat.getKey(), stat.getValue());
            }
        }
    }

    private static void pollServer(CommunicationsGrpc.CommunicationsBlockingStub stub) {
        long start = System.currentTimeMillis();

        Communication.Server_Info info = stub.getServerInfo(MessageBuilder.buildEmpty());

        long end = System.currentTimeMillis();

        System.out.printf("\nServer Information: \nName: %s \nMax Players: %d \nOnline: %d%n",
                info.getName(), info.getMaxPlayers(), info.getOnlinePlayers());

        System.out.printf("\nResponse Took: %dms", end - start);

        System.out.println("\n");

        UUID toUse = UUID.fromString("269ddc20-206a-48da-9167-877c562054f2");

        long start_1 = System.currentTimeMillis();

        Communication.Stats stats = stub.getStats(MessageBuilder.buildPlayerStatsRequest(toUse, "ItWasEnder"));

        long end_1 = System.currentTimeMillis();

        System.out.printf("Stats: \nBlocks Mined: %d \nBlocks Placed: %d \nItems Dropped: %d \nDamage Dealt: %f \nDamage Taken: %f \nDeaths: %d \nJoins: %d \nKills: %d%n",
                stats.getBlockedMined(), stats.getBlocksPlaced(), stats.getItemsDropped(), stats.getDamageDealt(), stats.getDamageTaken(), stats.getDeaths(), stats.getJoins(), stats.getKills().getTotal());

        System.out.println("\nKilled Types:");
        stats.getKills().getTypedList().forEach(kill -> System.out.printf("%s | %d%n", kill.getType(), kill.getAmount()));

        System.out.printf("\nResponse Took: %dms", end_1 - start_1);
    }

    /**
     * Fix the given text with making the first letter capitalised and the rest not.
     *
     * @param text the text fixing.
     * @param replaceUnderscore True to replace all _ with a space, false otherwise.
     * @return The new fixed text.
     */
    public static String fix(String text, boolean replaceUnderscore) {
        if (text.isEmpty()) {
            return text;
        }

        if (text.length() == 1) {
            return text.toUpperCase();
        }

        if (replaceUnderscore) {
            text = text.replace("_", " ");
        }

        StringBuilder builder = new StringBuilder();

        for (String split : text.split(" ")) {
            if (split.isEmpty()) {
                builder.append(" ");
                continue;
            }

            if (split.length() == 1) {
                builder.append(split.toUpperCase()).append(" ");
                continue;
            }

            builder.append(split.substring(0, 1).toUpperCase()).append(split.substring(1).toLowerCase()).append(" ");
        }

        return builder.toString().trim();
    }
}
