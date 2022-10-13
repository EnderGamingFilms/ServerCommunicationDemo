package me.endergaming.communicator;

import io.grpc.stub.ServerCallStreamObserver;
import io.grpc.stub.StreamObserver;
import me.endergaming.common.MessageBuilder;
import me.endergaming.common.Stats;
import me.endergaming.common.grpc.Communication;
import me.endergaming.common.grpc.CommunicationsGrpc;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class CommunicationImpl extends CommunicationsGrpc.CommunicationsImplBase {
    private final List<StreamObserver<Communication.StatStreamResponse>> statsObservers = new CopyOnWriteArrayList<>();

    @Override
    public void getStats(Communication.PlayerStatsRequest request, StreamObserver<Communication.Stats> responseObserver) {
        UUID playerId = MessageBuilder.toJavaUUID(request.getPlayer().getUuid());

        if (Bukkit.getOfflinePlayer(playerId).getName() == null) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(request.getPlayer().getName());

            if (offlinePlayer.hasPlayedBefore()) {
                playerId = offlinePlayer.getUniqueId();
            }
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

    @Override
    public void establishStatsConnection(Communication.Empty request, StreamObserver<Communication.StatsConnectionResponse> responseObserver) {
//        var observer = (ServerCallStreamObserver<?>) responseObserver;
//        observer.setOnCancelHandler(() -> this.statsObservers.remove(responseObserver));
//
//        this.statsObservers.add(responseObserver);
        System.out.println("Deprecated Action -- EstablishStatsConnection");
    }

    @Override
    public void establishCustomStatsConnection(Communication.Empty request, StreamObserver<Communication.StatStreamResponse> responseObserver) {
        var observer = (ServerCallStreamObserver<?>) responseObserver;
        observer.setOnCancelHandler(() -> this.statsObservers.remove(responseObserver));

        this.statsObservers.add(responseObserver);
    }

    public void notifyStatsUpdate(Player p, Stats stats) {
        var responseMessage = MessageBuilder.buildStatStreamResponse(p.getName(), stats);

        System.out.println("Sending stats update to " + this.statsObservers.size() + " observers");

        this.statsObservers.forEach(observer -> observer.onNext(responseMessage));
    }
}
