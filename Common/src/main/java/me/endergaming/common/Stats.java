package me.endergaming.common;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

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
}
