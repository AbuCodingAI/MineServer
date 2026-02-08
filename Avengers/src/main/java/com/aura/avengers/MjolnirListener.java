package com.aura.avengers;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MjolnirListener implements Listener {

    @EventHandler
    public void onMjolnirHit(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof org.bukkit.entity.Player)) {
            return;
        }

        org.bukkit.entity.Player player = (org.bukkit.entity.Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || item.getType() != org.bukkit.Material.MACE) {
            return;
        }

        if (item.getItemMeta() == null || !item.getItemMeta().hasDisplayName()) {
            return;
        }

        if (!item.getItemMeta().getDisplayName().contains("Mjolnir")) {
            return;
        }

        if (event.getEntity() instanceof LivingEntity) {
            LivingEntity target = (LivingEntity) event.getEntity();

            // Summon lightning
            player.getWorld().strikeLightning(target.getLocation());

            // Extra damage
            event.setDamage(event.getDamage() * 2);
        }
    }
}
