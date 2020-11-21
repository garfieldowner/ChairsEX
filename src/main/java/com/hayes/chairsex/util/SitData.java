package com.hayes.chairsex.util;

import com.hayes.chairsex.ChairsEX;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("deprecated")
public class SitData {

    private Player player;
    private Block chair;
    private ChairsEX pluginMan;

    private ArmorStand stand;
    private int task;

    public SitData(Player player, Block chair, ChairsEX mainClass) {

        this.player = player;
        this.chair = chair;
        this.pluginMan = mainClass;

        stand = (ArmorStand) chair.getLocation().getWorld().spawn(chair.getLocation().add(0.5D, 0.3D, 0.5D), ArmorStand.class, (settings) -> {

            settings.setGravity(false);
            settings.setMarker(true);
            settings.setSmall(true);
            settings.setVisible(false);
            settings.setCollidable(false);
            settings.setInvulnerable(true);
            settings.addPassenger(player);

        });

        task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(pluginMan, new Runnable() {
            @Override
            public void run() {
                try {
                    Object obj = stand.getClass().getMethod("getHandle").invoke(stand);
                    obj.getClass().getField("yaw").set(obj, player.getLocation().getYaw());
                } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }, 4L, 4L);

    }

    public Block getBlock() {

        return chair;

    }

    public Player getPlayer() {

        return player;

    }

    public void unsit() {

        Bukkit.getScheduler().cancelTask(task);
        player.leaveVehicle();
        stand.remove();

    }

}
