package me.endergaming.common;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Data
public class Stats {
    @Setter(AccessLevel.NONE)
    Map<String, Integer> killsMap = new HashMap<>();
    int joins;
    double damageDealt;
    double damageTaken;
    int blocksMined;
    int blocksPlaced;
    int deaths;
    int itemsDropped;

    public void addKill(String type, int amount) {
        int total = this.killsMap.getOrDefault(type, 0) + amount;

        this.killsMap.put(type, total);
    }

    public static class StatsAdapter extends TypeAdapter<Stats> {

        @Override
        public void write(JsonWriter out, Stats value) throws IOException {
            out.beginObject();

            out.name("killsMap");
            out.beginObject();
            for (var entry : value.killsMap.entrySet()) {
                out.name(entry.getKey());
                out.value(entry.getValue());
            }
            out.endObject();

            out.name("joins");
            out.value(value.joins);

            out.name("damageDealt");
            out.value(value.damageDealt);

            out.name("damageTaken");
            out.value(value.damageTaken);

            out.name("blocksMined");
            out.value(value.blocksMined);

            out.name("blocksPlaced");
            out.value(value.blocksPlaced);

            out.name("deaths");
            out.value(value.deaths);

            out.name("itemsDropped");
            out.value(value.itemsDropped);

            out.endObject();
        }

        @Override
        public Stats read(JsonReader in) throws IOException {
            Stats stats = new Stats();

            in.beginObject();

            while (in.hasNext()) {
                String name = in.nextName();

                switch (name) {
                    case "killsMap":
                        in.beginObject();
                        while (in.hasNext()) {
                            String type = in.nextName();
                            int amount = in.nextInt();

                            stats.addKill(type, amount);
                        }
                        in.endObject();
                        break;
                    case "joins":
                        stats.joins = in.nextInt();
                        break;
                    case "damageDealt":
                        stats.damageDealt = in.nextDouble();
                        break;
                    case "damageTaken":
                        stats.damageTaken = in.nextDouble();
                        break;
                    case "blocksMined":
                        stats.blocksMined = in.nextInt();
                        break;
                    case "blocksPlaced":
                        stats.blocksPlaced = in.nextInt();
                        break;
                    case "deaths":
                        stats.deaths = in.nextInt();
                        break;
                    case "itemsDropped":
                        stats.itemsDropped = in.nextInt();
                        break;
                }
            }

            in.endObject();

            return stats;
        }
    }
}
