package com.aura.avengers;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.Random;

public class HawkeyeListener implements Listener {

    private Random random = new Random();

    @EventHandler
    public void onArrowShoot(ProjectileLaunchEvent event) {
        if (!(event.getEntity() instanceof Arrow)) {
            return;
        }

        Arrow arrow = (Arrow) event.getEntity();
        if (!(arrow.getShooter() instanceof Player)) {
            return;
        }

        Player player = (Player) arrow.getShooter();
        ItemStack bow = player.getInventory().getItemInMainHand();

        // Check if using Infinity bow
        if (bow == null || bow.getType() != Material.BOW) {
            return;
        }

        if (!bow.containsEnchantment(Enchantment.INFINITY)) {
            return;
        }

        ItemStack item = arrow.getItemStack();

        if (item == null || item.getType() != Material.ARROW) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Hawkeye Arrow")) {
            return;
        }

        // Check if arrow already has loyalty
        boolean hasLoyalty = item.containsEnchantment(Enchantment.LOYALTY);

        if (!hasLoyalty) {
            // 4% chance to get loyalty 3
            if (random.nextInt(100) < 4) {
                item.addUnsafeEnchantment(Enchantment.LOYALTY, 3);
                player.sendMessage("§a[Hawkeye] Arrow enchanted with Loyalty III!");
            }
        }

        // 1% chance to get loyalty 4 or 5
        int chance = random.nextInt(100);
        if (chance < 1) {
            int loyaltyLevel = random.nextBoolean() ? 4 : 5;
            item.removeEnchantment(Enchantment.LOYALTY);
            item.addUnsafeEnchantment(Enchantment.LOYALTY, loyaltyLevel);
            player.sendMessage("§6[Hawkeye] Arrow enchanted with Loyalty " + loyaltyLevel + "!");
        }

        // Auto-aim at closest entity
    

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

        if (item == null || item.getType() != Material.ARROW) {
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
