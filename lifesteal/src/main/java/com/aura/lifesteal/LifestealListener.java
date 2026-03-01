package com.aura.lifesteal;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.attribute.Attribute;

public class LifestealListener implements Listener {

    private static final double HEALTH_PER_LIFE = 2.0; // 1 life = 2 health (1 heart)

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        // Victim loses a life (2 health)
        double currentMax = victim.getAttribute(Attribute.MAX_HEALTH).getValue();
        double newMax = Math.max(2.0, currentMax - HEALTH_PER_LIFE); // Minimum 1 heart
        victim.getAttribute(Attribute.MAX_HEALTH).setBaseValue(newMax);

        // If killer exists, they gain a life (2 health)
        if (killer != null) {
            double killerMax = killer.getAttribute(Attribute.MAX_HEALTH).getValue();
            double killerNewMax = Math.min(40.0, killerMax + HEALTH_PER_LIFE); // Maximum 20 hearts
            killer.getAttribute(Attribute.MAX_HEALTH).setBaseValue(killerNewMax);
            killer.sendMessage("§a[Lifesteal] You gained a life! Max health: " + (killerNewMax / 2) + " hearts");
        }

        victim.sendMessage("§c[Lifesteal] You lost a life! Max health: " + (newMax / 2) + " hearts");
    }
}
