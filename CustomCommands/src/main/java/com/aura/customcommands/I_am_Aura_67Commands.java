package com.aura.customcommands;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class I_am_Aura_67Commands implements CommandExecutor {

    private static final int SIZE = 30;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.getName().equals("I_am_Aura_67") && !player.getName().equals("IamAura67")) {
            player.sendMessage("§cYou don't have permission to use this command!");
            return true;
        }

        switch (command.getName().toLowerCase()) {
            case "spawnancientdebris":
                spawnAncientDebris(player);
                return true;
            case "spawndiamonds":
                spawnDiamonds(player);
                return true;
            case "makebox":
                makeBox(player);
                return true;
            case "spawn":
                if (args.length == 0) {
                    player.sendMessage("§cUsage: /spawn <block_name> [size]");
                    return true;
                }
                spawnBlock(player, args);
                return true;
            case "lobby":
                if (args.length == 0) {
                    ItemStack item = new ItemStack(Material.BEDROCK);
                    ItemMeta meta = item.getItemMeta();
                    if (meta != null) {
                        meta.setDisplayName("§b§lLobby Selector");
                        meta.setLore(Arrays.asList(
                                "§7Right-click to open lobby menu",
                                "§7Choose your gamemode!"
                        ));
                        item.setItemMeta(meta);
                    }
                    player.getInventory().addItem(item);
                    player.sendMessage("§a[Lobby] Lobby Selector added to inventory!");
                    return true;
                }
                joinLobby(player, args[0]);
                return true;
            case "givearmor":
                giveEnchantedArmor(player);
                return true;
        }

        return false;
    }

    private void spawnAncientDebris(Player player) {
        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();

        for (int x = startX; x < startX + SIZE; x++) {
            for (int y = startY; y < startY + SIZE; y++) {
                for (int z = startZ; z < startZ + SIZE; z++) {
                    world.getBlockAt(x, y, z).setType(Material.ANCIENT_DEBRIS);
                }
            }
        }

        player.sendMessage("§aSpawned 30x30x30 Ancient Debris cube!");
    }

    private void spawnDiamonds(Player player) {
        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();

        for (int x = startX; x < startX + SIZE; x++) {
            for (int y = startY; y < startY + SIZE; y++) {
                for (int z = startZ; z < startZ + SIZE; z++) {
                    world.getBlockAt(x, y, z).setType(Material.DIAMOND_BLOCK);
                }
            }
        }

        player.sendMessage("§aSpawned 30x30x30 Diamond cube!");
    }

    private void makeBox(Player player) {
        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();
        int endX = startX + SIZE - 1;
        int endY = startY + SIZE - 1;
        int endZ = startZ + SIZE - 1;

        // Fill entire area with air first
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    world.getBlockAt(x, y, z).setType(Material.AIR);
                }
            }
        }

        // Create wooden plank shell (outer walls only)
        for (int x = startX; x <= endX; x++) {
            for (int y = startY; y <= endY; y++) {
                for (int z = startZ; z <= endZ; z++) {
                    // Check if block is on the edge
                    if (x == startX || x == endX || y == startY || y == endY || z == startZ || z == endZ) {
                        world.getBlockAt(x, y, z).setType(Material.OAK_PLANKS);
                    }
                }
            }
        }

        player.sendMessage("§aCreated 30x30x30 hollow box with wooden plank walls!");
    }

    private void spawnBlock(Player player, String[] args) {
        String blockName = args[0].toUpperCase();
        int size = SIZE;

        if (args.length > 1) {
            try {
                size = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid size! Using default size of 30.");
            }
        }

        Material material;
        try {
            material = Material.valueOf(blockName);
        } catch (IllegalArgumentException e) {
            player.sendMessage("§cUnknown block: " + blockName);
            player.sendMessage("§cExamples: DIAMOND_ORE, NETHERITE_BLOCK, BEDROCK, DIAMOND_BLOCK");
            return;
        }

        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();

        for (int x = startX; x < startX + size; x++) {
            for (int y = startY; y < startY + size; y++) {
                for (int z = startZ; z < startZ + size; z++) {
                    world.getBlockAt(x, y, z).setType(material);
                }
            }
        }

        player.sendMessage("§aSpawned " + size + "x" + size + "x" + size + " " + blockName + " cube!");
    }

    private void joinLobby(Player player, String gamemode) {
        switch (gamemode.toLowerCase()) {
            case "skyblock":
                player.performCommand("mv tp skyblock");
                break;
            case "lifesteal":
                player.sendMessage("§a[Lobby] Joining Lifesteal...");
                player.sendMessage("§7(Lifesteal world not yet set up)");
                break;
            case "pvp":
                player.performCommand("warp pvp");
                break;
            case "survival":
                player.sendMessage("§a[Lobby] Joining Pure Survival...");
                player.sendMessage("§7(Pure Survival world not yet set up)");
                break;
            case "manhunt":
                player.sendMessage("§a[Lobby] Joining Manhunt...");
                player.sendMessage("§7(Manhunt world not yet set up)");
                break;
            default:
                player.sendMessage("§cUnknown gamemode: " + gamemode);
                player.sendMessage("§cAvailable: skyblock, lifesteal, pvp, survival, manhunt");
        }
    }

    private void giveEnchantedArmor(Player player) {
        ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
        ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
        ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);

        // Add universal enchantments to all pieces
        addUniversalEnchantments(helmet);
        addUniversalEnchantments(chestplate);
        addUniversalEnchantments(leggings);
        addUniversalEnchantments(boots);

        // Add specific enchantments based on armor piece
        addHelmetEnchantments(helmet);
        addChestplateEnchantments(chestplate);
        addLeggingsEnchantments(leggings);
        addBootsEnchantments(boots);

        player.getInventory().setHelmet(helmet);
        player.getInventory().setChestplate(chestplate);
        player.getInventory().setLeggings(leggings);
        player.getInventory().setBoots(boots);

        player.sendMessage("§a[Armor] You have been given full enchanted netherite armor!");
    }

    private void addUniversalEnchantments(ItemStack item) {
        // Universal enchantments for all armor pieces
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 3);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 1);
    }

    private void addHelmetEnchantments(ItemStack helmet) {
        // Helmet-specific enchantments
        helmet.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 4);
        helmet.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.RESPIRATION, 3);
        helmet.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.AQUA_AFFINITY, 1);
    }

    private void addChestplateEnchantments(ItemStack chestplate) {
        // Chestplate-specific enchantments
        chestplate.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 4);
        chestplate.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.THORNS, 3);
        chestplate.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.BLAST_PROTECTION, 4);
    }

    private void addLeggingsEnchantments(ItemStack leggings) {
        // Leggings-specific enchantments
        leggings.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 4);
        leggings.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FIRE_PROTECTION, 4);
        leggings.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROJECTILE_PROTECTION, 4);
    }

    private void addBootsEnchantments(ItemStack boots) {
        // Boots-specific enchantments
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 4);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FEATHER_FALLING, 4);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DEPTH_STRIDER, 3);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FROST_WALKER, 2);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SOUL_SPEED, 3);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SWIFT_SNEAK, 3);
    }
}
