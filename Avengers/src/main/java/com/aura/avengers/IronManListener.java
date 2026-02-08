package com.aura.avengers;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class IronManListener implements Listener {

    @EventHandler
    public void onGlovesUse(PlayerInteractEvent event) {
        if (event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_AIR
                && event.getAction() != org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != Material.NETHERITE_BOOTS) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Iron Man Gloves")) {
            return;
        }

        event.setCancelled(true);

        // Find closest entity
        LivingEntity target = findClosestEntity(player, 50);
        if (target == null) {
            player.sendMessage("§cNo target found!");
            return;
        }

        // Shoot beam
        shootBeam(player, target);
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

    private void shootBeam(Player player, LivingEntity target) {
        Location from = player.getEyeLocation();
        Location to = target.getLocation().add(0, target.getHeight() / 2, 0);

        // Draw beam effect
        drawBeam(from, to);

        // Deal damage (5 hearts = 10 damage)
        target.damage(10, player);

        player.sendMessage("§c[Iron Man] Beam fired!");
    }

    private void drawBeam(Location from, Location to) {
        Vector direction = to.toVector().subtract(from.toVector()).normalize();
        double distance = from.distance(to);

        for (double i = 0; i < distance; i += 0.5) {
            Location particleLocation = from.clone().add(direction.clone().multiply(i));
            from.getWorld().spawnParticle(org.bukkit.Particle.FLAME, particleLocation, 5, 0.1, 0.1, 0.1, 0.1);
        }
    }
}
