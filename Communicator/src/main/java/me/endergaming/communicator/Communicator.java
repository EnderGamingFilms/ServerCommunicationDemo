package me.endergaming.communicator;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
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
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(Stats.class, new Stats.StatsAdapter())
            .create();

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

        this.read();
    }

    public void read() {
        try {
            File file = this.getDataFile();

            Reader reader = new BufferedReader(new FileReader(file));

            JsonArray _array = this.gson.fromJson(reader, JsonArray.class);

            if (_array == null) {
                return;
            }

            for (var _element : _array) {
                JsonObject _stats = _element.getAsJsonObject();

                String id = _stats.get("uuid").getAsString();

                if (id.isBlank()) {
                    continue;
                }

                UUID uuid = UUID.fromString(id);
                Stats stats = this.gson.fromJson(_stats.get("stats"), Stats.class);

                this.dataCache.put(uuid, stats);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getDataFile() throws IOException {
        File file = new File(this.getDataFolder(), "data.json");

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        return file;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        HandlerList.unregisterAll((Listener) this);

        try {
            File file = this.getDataFile();

            Writer writer = new BufferedWriter(new FileWriter(file));

            JsonArray _array = new JsonArray();

            for (var entry : this.dataCache.asMap().entrySet()) {
                JsonObject _stats = new JsonObject();

                _stats.addProperty("uuid", entry.getKey().toString());
                _stats.add("stats", this.gson.toJsonTree(entry.getValue()));

                _array.add(_stats);
            }

            this.gson.toJson(_array, writer);

            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
