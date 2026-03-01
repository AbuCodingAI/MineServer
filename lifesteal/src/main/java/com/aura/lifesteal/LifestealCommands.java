package com.aura.lifesteal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.attribute.Attribute;

public class LifestealCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("maxhealth")) {
            double maxHealth = player.getAttribute(Attribute.MAX_HEALTH).getValue();
            player.sendMessage("§a[Lifesteal] Your max health: " + (maxHealth / 2) + " hearts");
            return true;
        }

        if (command.getName().equalsIgnoreCase("sethealth")) {
            if (!player.isOp()) {
                player.sendMessage("§cYou don't have permission!");
                return true;
            }

            if (args.length < 2) {
                player.sendMessage("§cUsage: /sethealth <player> <hearts>");
                return true;
            }

            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                player.sendMessage("§cPlayer not found!");
                return true;
            }

            try {
                double hearts = Double.parseDouble(args[1]);
                double health = hearts * 2.0;
                health = Math.max(2.0, Math.min(40.0, health)); // Clamp between 1-20 hearts
                target.getAttribute(Attribute.MAX_HEALTH).setBaseValue(health);
                player.sendMessage("§a[Lifesteal] Set " + target.getName() + "'s max health to " + hearts + " hearts");
                target.sendMessage("§a[Lifesteal] Your max health set to " + hearts + " hearts");
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid number!");
            }
            return true;
        }

        return false;
    }
}
