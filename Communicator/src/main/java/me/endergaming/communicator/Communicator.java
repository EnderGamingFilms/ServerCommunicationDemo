package me.endergaming.communicator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import lombok.SneakyThrows;
import me.endergaming.common.Stats;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;
import java.util.logging.Level;

public final class Communicator extends JavaPlugin implements Listener {
    private static Communicator INSTANCE;
    private final LoadingCache<UUID, Stats> dataCache = CacheBuilder.newBuilder().build(new CacheLoader<>() {
        @Override
        public @NotNull Stats load(@NotNull UUID key) {
            return new Stats();
        }
    });

    Server server;

    @SneakyThrows
    @Override
    public void onEnable() {
        // Plugin startup logic
        INSTANCE = this;

        this.getServer().getPluginManager().registerEvents(this, this);

        this.server = ServerBuilder.forPort(3009)
                .addService(new CommunicationImpl())
                .build();

        this.server.start();

        this.getLogger().log(Level.INFO, "Server started on port 3009");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll((Listener) this);

        this.server.shutdown();
    }

    @SneakyThrows
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Stats stats = this.dataCache.get(e.getPlayer().getUniqueId());

        UUID uuid = e.getPlayer().getUniqueId();
        this.getLogger().log(Level.INFO, "Most Sig: " + uuid.getMostSignificantBits());
        this.getLogger().log(Level.INFO, "Least Sig: " + uuid.getLeastSignificantBits());

        stats.setJoins(stats.getJoins() + 1);
    }

    @SneakyThrows
    @EventHandler
    public void onLeave(BlockBreakEvent e) {
        Stats stats = this.dataCache.get(e.getPlayer().getUniqueId());

        stats.setBlocksMined(stats.getBlocksMined() + 1);
    }

    @SneakyThrows
    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Stats stats = this.dataCache.get(e.getPlayer().getUniqueId());

        stats.setBlocksPlaced(stats.getBlocksPlaced() + 1);
    }

    @SneakyThrows
    @EventHandler
    public void onDamageTake(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return;
        }

        Stats stats = this.dataCache.get(player.getUniqueId());

        if (player.getHealth() - e.getFinalDamage() <= 0) {
            stats.setDeaths(stats.getDeaths() + 1);
        }

        stats.setDamageTaken(stats.getDamageTaken() + e.getFinalDamage());
    }

    @SneakyThrows
    @EventHandler
    public void onDamageDeal(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player player)) {
            return;
        }

        if (!(e.getEntity() instanceof LivingEntity living)) {
            return;
        }

        Stats stats = this.dataCache.get(player.getUniqueId());

        if (living.getHealth() - e.getFinalDamage() <= 0) {
            stats.addKill(living.getType().name().toLowerCase(), 1);
        }

        stats.setDamageDealt(stats.getDamageDealt() + e.getFinalDamage());
    }

    @SneakyThrows
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Stats stats = this.dataCache.get(e.getPlayer().getUniqueId());

        stats.setItemsDropped(stats.getItemsDropped() + e.getItemDrop().getItemStack().getAmount());
    }

    @SneakyThrows
    public Stats getStats(UUID uuid) {
        return this.dataCache.get(uuid);
    }

    public static Communicator getInstance() {
        return INSTANCE;
    }
}
