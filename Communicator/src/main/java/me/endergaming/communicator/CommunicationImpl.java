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

        if (Bukkit.getPlayer(playerId) == null) {
            System.out.println("Failed to find with UUID");
            System.out.println("Received: " + request.getPlayer().getUuid());
            playerId = Bukkit.getOfflinePlayer(request.getPlayer().getName()).getUniqueId();
        }

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
