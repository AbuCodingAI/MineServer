package com.aura.avengers;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class HawkeyeListener implements Listener {

    @EventHandler
    public void onArrowShoot(org.bukkit.event.entity.ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        if (!(arrow.getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) arrow.getShooter();
        ItemStack item = arrow.getItemStack();

        if (item == null || item.getType() != org.bukkit.Material.ARROW) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Hawkeye Arrow")) {
            return;
        }

        // Find closest entity and aim at it
        LivingEntity target = findClosestEntity(player, 100);
        if (target != null) {
            Vector direction = target.getLocation().toVector()
                    .subtract(arrow.getLocation().toVector())
                    .normalize();
            arrow.setVelocity(direction.multiply(3));
        }
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        ItemStack item = arrow.getItemStack();

        if (item == null || item.getType() != org.bukkit.Material.ARROW) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Hawkeye Arrow")) {
            return;
        }

        // Extra damage on hit
        if (event.getHitEntity() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) event.getHitEntity();
            target.damage(5);
        }
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
}
