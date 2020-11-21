package com.hayes.chairsex.listener;

import com.hayes.chairsex.ChairsEX;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.spigotmc.event.entity.EntityDismountEvent;

public class ChairListener implements Listener {

    private ChairsEX mainClass;

    public void ChairListener(ChairsEX ChairsMain) {
        this.mainClass = ChairsMain;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getHand().equals(EquipmentSlot.HAND) && e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR) && (e.getClickedBlock().getType().name().contains("STAIRS") || e.getClickedBlock().getType().name().contains("SLAB"))) {
            e.setCancelled(true);
            if (!ChairsEX.isOccupied(e.getClickedBlock())) {
                ChairsEX.sit(e.getPlayer(), e.getClickedBlock(), mainClass);
            }
        }
    }

    @EventHandler
    public void onEntityDismount(EntityDismountEvent e) {

        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            if (ChairsEX.isSitting(player)) {
                ChairsEX.unsit(player);
            }

        }

    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {

        Player player = e.getPlayer();

        if (ChairsEX.isSitting(player)) {
            ChairsEX.unsit(player);
        }

    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent e) {

        Player player = e.getEntity();

        if (ChairsEX.isSitting(player)) {
            ChairsEX.unsit(player);
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (ChairsEX.isOccupied(e.getBlock())) {
            ChairsEX.unsit(e.getBlock());
        }
    }

}
