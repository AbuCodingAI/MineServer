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
                if (args.length == 0) {
                    player.sendMessage("§cUsage: /makebox <size>");
                    return true;
                }
                makeBox(player, args);
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
            case "givesword":
                giveEnchantedSword(player);
                return true;
            case "removewarp":
                if (args.length == 0) {
                    player.sendMessage("§cUsage: /removewarp <warpname>");
                    return true;
                }
                removeWarp(player, args[0]);
                return true;
            case "admin":
                if (args.length == 0) {
                    player.sendMessage("§cUsage: /admin <username>");
                    return true;
                }
                makeAdmin(player, args[0]);
                return true;
            case "nocheats":
                destroyAllCommandBlocks(player);
                return true;
            case "player":
                makePlayer(player);
                return true;
            case "unplayer":
                makeOwner(player);
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

    private void makeBox(Player player, String[] args) {
        int size = 30;

        if (args.length > 0) {
            try {
                size = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                player.sendMessage("§cInvalid size! Using default size of 30.");
            }
        }

        var location = player.getLocation();
        var world = player.getWorld();
        int startX = location.getBlockX();
        int startY = location.getBlockY();
        int startZ = location.getBlockZ();
        int endX = startX + size - 1;
        int endY = startY + size - 1;
        int endZ = startZ + size - 1;

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

        player.sendMessage("§aCreated " + size + "x" + size + "x" + size + " hollow box with wooden plank walls!");
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
                org.bukkit.World skyblockWorld = org.bukkit.Bukkit.getWorld("skyblock");
                if (skyblockWorld != null) {
                    player.teleport(skyblockWorld.getSpawnLocation());
                    player.sendMessage("§a[Lobby] Teleported to Skyblock!");
                } else {
                    player.sendMessage("§c[Lobby] Skyblock world not found!");
                }
                break;
            case "lifesteal":
                player.sendMessage("§a[Lobby] Joining Lifesteal...");
                player.sendMessage("§7(Lifesteal world not yet set up)");
                break;
            case "pvp":
                player.performCommand("warp pvp");
                break;
            case "survival":
                org.bukkit.World survivalWorld = org.bukkit.Bukkit.getWorld("world");
                if (survivalWorld != null) {
                    player.teleport(survivalWorld.getSpawnLocation());
                    player.sendMessage("§a[Lobby] Teleported to Survival!");
                } else {
                    player.sendMessage("§c[Lobby] Survival world not found!");
                }
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
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.THORNS, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FIRE_PROTECTION, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROJECTILE_PROTECTION, 255);
        item.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.BLAST_PROTECTION, 255);
    }

    private void addHelmetEnchantments(ItemStack helmet) {
        // Helmet-specific enchantments
        helmet.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 255);
        helmet.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.RESPIRATION, 255);
        helmet.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.AQUA_AFFINITY, 255);
    }

    private void addChestplateEnchantments(ItemStack chestplate) {
        // Chestplate-specific enchantments
        chestplate.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 255);
        chestplate.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.THORNS, 255);
        chestplate.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.BLAST_PROTECTION, 255);
    }

    private void addLeggingsEnchantments(ItemStack leggings) {
        // Leggings-specific enchantments
        leggings.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROTECTION, 255);
        leggings.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FIRE_PROTECTION, 255);
        leggings.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.PROJECTILE_PROTECTION, 255);
    }

    private void addBootsEnchantments(ItemStack boots) {
        // Boots-specific enchantments
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FEATHER_FALLING, 255);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.DEPTH_STRIDER, 255);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FROST_WALKER, 255);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SOUL_SPEED, 255);
        boots.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SWIFT_SNEAK, 255);
    }

    private void giveEnchantedSword(Player player) {
        ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);

        // Add all sword enchantments at level 255
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SHARPNESS, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SMITE, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.BANE_OF_ARTHROPODS, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.KNOCKBACK, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FIRE_ASPECT, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.LOOTING, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SWEEPING_EDGE, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        sword.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);

        player.getInventory().addItem(sword);
        player.sendMessage("§a[Sword] You have been given a fully enchanted netherite sword!");
    }

    private void giveAllTools(Player player) {
        // Pickaxe
        ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
        pickaxe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.EFFICIENCY, 255);
        pickaxe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FORTUNE, 255);
        pickaxe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        pickaxe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);

        // Axe
        ItemStack axe = new ItemStack(Material.NETHERITE_AXE);
        axe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.EFFICIENCY, 255);
        axe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.SHARPNESS, 255);
        axe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        axe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);

        // Shovel
        ItemStack shovel = new ItemStack(Material.NETHERITE_SHOVEL);
        shovel.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.EFFICIENCY, 255);
        shovel.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FORTUNE, 255);
        shovel.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        shovel.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);

        // Hoe
        ItemStack hoe = new ItemStack(Material.NETHERITE_HOE);
        hoe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.EFFICIENCY, 255);
        hoe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.FORTUNE, 255);
        hoe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.UNBREAKING, 255);
        hoe.addUnsafeEnchantment(org.bukkit.enchantments.Enchantment.MENDING, 255);

        player.getInventory().addItem(pickaxe);
        player.getInventory().addItem(axe);
        player.getInventory().addItem(shovel);
        player.getInventory().addItem(hoe);

        player.sendMessage("§a[Tools] You have been given all fully enchanted netherite tools!");
    }

    private void removeWarp(Player player, String warpName) {
        // This delegates to EssentialsX /delwarp command
        player.performCommand("delwarp " + warpName);
    }

    private void makeAdmin(Player player, String username) {
        // Check if player is owner
        if (!player.getName().equals("I_am_Aura_67") && !player.getName().equals("IamAura67")) {
            player.sendMessage("§c[Admin] Only owners can make admins!");
            return;
        }

        // Add admin to the list
        PermissionManager.addAdmin(username);
        player.sendMessage("§a[Admin] " + username + " is now an admin!");
    }

    private void destroyAllCommandBlocks(Player player) {
        // Check if player is owner
        if (!player.getName().equals("I_am_Aura_67") && !player.getName().equals("IamAura67")) {
            player.sendMessage("§c[NoCheat] Only owners can use this command!");
            return;
        }

        int count = 0;
        for (org.bukkit.World world : org.bukkit.Bukkit.getWorlds()) {
            for (org.bukkit.Chunk chunk : world.getLoadedChunks()) {
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
                            org.bukkit.block.Block block = chunk.getBlock(x, y, z);
                            if (block.getType() == Material.COMMAND_BLOCK || 
                                block.getType() == Material.CHAIN_COMMAND_BLOCK || 
                                block.getType() == Material.REPEATING_COMMAND_BLOCK) {
                                block.setType(Material.AIR);
                                count++;
                            }
                        }
                    }
                }
            }
        }

        player.sendMessage("§a[NoCheat] Destroyed " + count + " command blocks!");
        org.bukkit.Bukkit.getLogger().info("[NoCheat] " + player.getName() + " destroyed " + count + " command blocks");
    }

    private void makePlayer(Player player) {
        // Remove from admin list
        PermissionManager.removeAdmin(player.getName());
        player.setDisplayName(player.getName());
        player.setPlayerListName(player.getName());
        player.sendMessage("§a[Test] You are now a regular player!");
    }

    private void makeOwner(Player player) {
        // Add back as owner
        player.setDisplayName("§c§l[OWNER] §r" + player.getName());
        player.setPlayerListName("§c§l[OWNER] §r" + player.getName());
        player.sendMessage("§a[Test] You are back to owner!");
    }
}