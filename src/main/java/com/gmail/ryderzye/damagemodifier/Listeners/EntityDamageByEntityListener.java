package com.gmail.ryderzye.DamageModifier.Listeners;

import com.gmail.ryderzye.DamageModifier.DamageModifier;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class EntityDamageByEntityListener implements Listener {
    public EntityDamageByEntityListener() {
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        double damageMultiplier = 1.0D;
        String configPath = "damages.default";
        DamageCause damageCause = event.getCause();
        Entity damager = event.getDamager();
        Player target = (Player)event.getEntity();
        double damage = event.getDamage();
        if (damager.getType() == EntityType.ENDER_CRYSTAL) {
            if (isDragonFightCrystal(damager.getLocation())) {
                return;
            }

            if (target.hasPermission(DamageModifier.get().getConfig().getString("damages.crystal.permissions"))) {
                configPath = "damages.crystal.player";
            }
        } else if (damageCause == DamageCause.ENTITY_ATTACK && damager instanceof Player) {
            Player attacker = (Player)damager;
            Material weaponType = attacker.getInventory().getItemInMainHand().getType();
            if (!weaponType.toString().contains("_SWORD") && !weaponType.toString().contains("_AXE")) {
                weaponType = attacker.getInventory().getItemInOffHand().getType();
            }

            if (weaponType.toString().contains("_SWORD")) {
                configPath = this.swordDamageConfigPath(attacker, weaponType, configPath);
            } else if (weaponType.toString().contains("_AXE")) {
                configPath = this.axeDamageConfigPath(attacker, weaponType, configPath);
            }
        }

        damageMultiplier = DamageModifier.get().getConfig().getDouble(configPath);
        event.setDamage(damage * damageMultiplier);
    }

    private static boolean isDragonFightCrystal(Location loc) {
        Block blockUnder = loc.getBlock().getRelative(0, -1, 0);
        return loc.getWorld().getEnvironment() == Environment.THE_END && blockUnder != null && blockUnder.getType() == Material.BEDROCK;
    }

    private String swordDamageConfigPath(Player attacker, Material material, String configPath) {
        if (attacker.hasPermission(DamageModifier.get().getConfig().getString("damages.sword.permissions"))) {
            switch(material) {
                case WOODEN_SWORD:
                    configPath = "damages.sword.wooden";
                    break;
                case STONE_SWORD:
                    configPath = "damages.sword.stone";
                    break;
                case IRON_SWORD:
                    configPath = "damages.sword.iron";
                    break;
                case GOLDEN_SWORD:
                    configPath = "damages.sword.gold";
                    break;
                case DIAMOND_SWORD:
                    configPath = "damages.sword.diamond";
                    break;
                case NETHERITE_SWORD:
                    configPath = "damages.sword.netherite";
            }
        }

        return configPath;
    }

    private String axeDamageConfigPath(Player attacker, Material material, String configPath) {
        if (attacker.hasPermission(DamageModifier.get().getConfig().getString("damages.axe.permissions"))) {
            switch(material) {
                case WOODEN_AXE:
                    configPath = "damages.axe.wooden";
                    break;
                case STONE_AXE:
                    configPath = "damages.axe.stone";
                    break;
                case IRON_AXE:
                    configPath = "damages.axe.iron";
                    break;
                case GOLDEN_AXE:
                    configPath = "damages.axe.gold";
                    break;
                case DIAMOND_AXE:
                    configPath = "damages.axe.diamond";
                    break;
                case NETHERITE_AXE:
                    configPath = "damages.axe.netherite";
            }
        }

        return configPath;
    }
}
