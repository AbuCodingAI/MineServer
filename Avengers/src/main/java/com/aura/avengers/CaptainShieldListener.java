package com.aura.avengers;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CaptainShieldListener implements Listener {

    @EventHandler
    public void onShieldThrow(PlayerInteractEvent event) {
        if (event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_AIR && 
            event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.SHIELD) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Captain America Shield")) {
            return;
        }

        event.setCancelled(true);

        // Find closest entity
        LivingEntity target = findClosestEntity(player, 50);
        if (target == null) {
            player.sendMessage("§9No target found!");
            return;
        }

        // Throw shield
        throwShield(player, target);
    }

    private LivingEntity findClosestEntity(Player player, double range) {
        LivingEntity closest = null;
        double closestDistance = range;

        for (LivingEntity entity : player.getWorld().getLivingEntities()) {
            if (entity == player || entity instanceof Player) {
                continue;
            }

            double distance = player.getLocation().distance(entity.getLocation());
            if (distance < closestDistance) {
                closest = entity;
                closestDistance = distance;
            }
        }

        return closest;
    }

    private void throwShield(Player player, LivingEntity target) {
        // Deal damage (4 hearts = 8 damage)
        target.damage(8, player);

        // Create visual effect
        Vector direction = target.getLocation().toVector().subtract(player.getLocation().toVector()).normalize();
        target.setVelocity(direction.multiply(1.5));

        player.sendMessage("§9[Captain America] Shield thrown!");
    }
}
