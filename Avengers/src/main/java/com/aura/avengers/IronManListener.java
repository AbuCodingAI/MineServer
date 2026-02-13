package com.aura.avengers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class IronManListener implements Listener {

    private Map<UUID, Long> beamCooldown = new HashMap<>();

    @EventHandler
    public void onIronManDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack chestplate = player.getInventory().getChestplate();
        ItemStack shield = player.getInventory().getItemInOffHand();

        // Check if player has Iron Man armor
        boolean hasHelmet = helmet != null && helmet.getType() == Material.NETHERITE_HELMET
                && helmet.getItemMeta() != null && helmet.getItemMeta().hasDisplayName()
                && helmet.getItemMeta().getDisplayName().contains("Iron Man");

        boolean hasChestplate = chestplate != null && chestplate.getType() == Material.NETHERITE_CHESTPLATE
                && chestplate.getItemMeta() != null && chestplate.getItemMeta().hasDisplayName()
                && chestplate.getItemMeta().getDisplayName().contains("Iron Man");

        boolean hasShield = shield != null && shield.getType() == Material.SHIELD
                && shield.getItemMeta() != null && shield.getItemMeta().hasDisplayName()
                && shield.getItemMeta().getDisplayName().contains("Captain America");

        // If wearing Iron Man armor and holding Captain Shield, shield takes no damage
        if (hasHelmet && hasChestplate && hasShield) {
            event.setCancelled(true);
            player.sendMessage("§c[Iron Man] Shield protected you!");
        }
    }

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

        // Check cooldown (0.5 seconds = 500 milliseconds)
        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        if (beamCooldown.containsKey(uuid)) {
            long lastUse = beamCooldown.get(uuid);
            if (currentTime - lastUse < 500) {
                player.sendMessage("§c[Iron Man] Beam is on cooldown!");
                return;
            }
        }

        // Find closest entity within 10 blocks
        LivingEntity target = findClosestEntity(player, 10);
        if (target == null) {
            player.sendMessage("§cNo target found within 10 blocks!");
            return;
        }

        // Shoot beam
        shootBeam(player, target);
        beamCooldown.put(uuid, currentTime);
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
        org.bukkit.Location from = player.getEyeLocation();
        org.bukkit.Location to = target.getLocation().add(0, target.getHeight() / 2, 0);

        // Draw beam effect
        drawBeam(from, to);

        // Deal damage (5 hearts = 10 damage)
        target.damage(10, player);

        player.sendMessage("§c[Iron Man] Beam fired!");
    }

    private void drawBeam(org.bukkit.Location from, org.bukkit.Location to) {
        Vector direction = to.toVector().subtract(from.toVector()).normalize();
        double distance = from.distance(to);

        for (double i = 0; i < distance; i += 0.5) {
            org.bukkit.Location particleLocation = from.clone().add(direction.clone().multiply(i));
            from.getWorld().spawnParticle(org.bukkit.Particle.FLAME, particleLocation, 5, 0.1, 0.1, 0.1, 0.1);
        }
    }
}
