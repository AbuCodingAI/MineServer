package com.aura.avengers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class CaptainShieldListener implements Listener {

    private Map<UUID, Long> shieldCooldown = new HashMap<>();

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

        // Check cooldown (0.5 seconds = 500 milliseconds)
        UUID uuid = player.getUniqueId();
        long currentTime = System.currentTimeMillis();
        if (shieldCooldown.containsKey(uuid)) {
            long lastUse = shieldCooldown.get(uuid);
            if (currentTime - lastUse < 500) {
                player.sendMessage("§c[Captain America] Shield is on cooldown!");
                return;
            }
        }

        // Find closest entity within 10 blocks
        LivingEntity target = findClosestEntity(player, 10);
        if (target == null) {
            player.sendMessage("§9No target found within 10 blocks!");
            return;
        }

        // Throw shield
        throwShield(player, target);
        shieldCooldown.put(uuid, currentTime);
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
