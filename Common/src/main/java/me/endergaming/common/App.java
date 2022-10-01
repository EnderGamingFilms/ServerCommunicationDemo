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

        long start = System.currentTimeMillis();

        Communication.Server_Info info = stub.getServerInfo(MessageBuilder.buildEmpty());

        long end = System.currentTimeMillis();

        System.out.printf("Server Information: \nName: %s \nMax Players: %d \nOnline: %d%n",
                info.getName(), info.getMaxPlayers(), info.getOnlinePlayers());

        System.out.printf("\nResponse Took: %dms", end - start);

        System.out.println("\n");

        long start_1 = System.currentTimeMillis();

        Communication.Stats stats = stub.getStats(MessageBuilder.buildPlayer(UUID.fromString("269ddc20-206a-48da-9167-877c562054f2"), "ItWasEnder"));

        long end_1 = System.currentTimeMillis();

        System.out.printf("Stats: \nBlocks Mined: %d \nBlocks Placed: %d \nItems Dropped: %d \nDamage Dealt: %f \nDamage Taken: %f \nDeaths: %d \nJoins: %d \nKills: %d%n",
                stats.getBlockedMined(), stats.getBlocksPlaced(), stats.getItemsDropped(), stats.getDamageDealt(), stats.getDamageTaken(), stats.getDeaths(), stats.getJoins(), stats.getKills().getTotal());

        System.out.println("\nKilled Types:");
        stats.getKills().getTypedList().forEach(kill -> System.out.printf("Type: %s \nAmount: %d%n", kill.getType(), kill.getAmount()));

        System.out.printf("\nResponse Took: %dms", end_1 - start_1);
    }
}
