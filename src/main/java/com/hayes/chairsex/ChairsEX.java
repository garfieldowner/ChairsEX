package com.hayes.chairsex;

import com.hayes.chairsex.listener.ChairListener;
import com.hayes.chairsex.util.SitData;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public final class ChairsEX extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new ChairListener(this), this);
        System.out.println("ChairsEX initialized");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        System.out.println("ChairsEX deinitialized");
    }

    private static HashMap<UUID, SitData> sitting = new HashMap<>();

    public static void sit(Player player, Block block, ChairsEX mainClass) {
        if (sitting.containsKey(player.getUniqueId())) {
            if (sitting.get(player.getUniqueId()).getBlock().equals(block)) {
                return;
            }

            unsit(player);
        }

        sitting.put(player.getUniqueId(), new SitData(player, block, mainClass));
    }

    public static void unsit(Player player) {
        sitting.get(player.getUniqueId()).unsit();
        sitting.remove(player.getUniqueId());
    }

    public static void unsit(Block chair) {
        UUID remove = null;
        for (UUID uuid : sitting.keySet()) {
            SitData data = sitting.get(uuid);

            if (data.getBlock().equals(chair)) {
                data.unsit();
            }
            remove = uuid;
            break;
        }

        if (remove != null) {
            sitting.remove(remove);
        }
    }

    public static boolean isOccupied(Block block) {
        for (SitData data : sitting.values()) {
            if (data.getBlock().equals(block)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isSitting(Player player) {
        return sitting.containsKey(player.getUniqueId());
    }


}
