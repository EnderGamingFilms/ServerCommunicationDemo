package me.endergaming.communicator;

import io.grpc.stub.StreamObserver;
import me.endergaming.common.MessageBuilder;
import me.endergaming.common.grpc.Communication;
import me.endergaming.common.grpc.CommunicationsGrpc;
import org.bukkit.Bukkit;
import org.bukkit.Server;

import java.util.UUID;

public class CommunicationImpl extends CommunicationsGrpc.CommunicationsImplBase {
    @Override
    public void getStats(Communication.PlayerStatsRequest request, StreamObserver<Communication.Stats> responseObserver) {
        UUID playerId = MessageBuilder.toJavaUUID(request.getPlayer().getUuid());

        UUID id = new UUID(request.getPlayer().getUuid().getMostSignificantBits(), request.getPlayer().getUuid().getLeastSignificantBits());

        System.out.println("Expected: " + "269ddc20-206a-48da-9167-877c562054f2");
        System.out.println("Received: " + playerId);

        Communication.Stats stats = MessageBuilder.buildStats(Communicator.getInstance().getStats(playerId));

        responseObserver.onNext(stats);
        responseObserver.onCompleted();
    }

    @Override
    public void getServerInfo(Communication.Empty request, StreamObserver<Communication.Server_Info> responseObserver) {
        Server bukkitServer = Communicator.getInstance().getServer();

        Communication.Server_Info info = MessageBuilder.buildServerInfo(
                bukkitServer.getName(),
                bukkitServer.getOnlinePlayers().size(),
                bukkitServer.getMaxPlayers());

        responseObserver.onNext(info);
        responseObserver.onCompleted();
    }
}
