package com.gmail.ryderzye.DamageModifier.Listeners;

import com.gmail.ryderzye.DamageModifier.DamageModifier;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class EntityShootBowEventListener implements Listener {
    public EntityShootBowEventListener() {
    }

    @EventHandler
    public void onEntityShootBowEvent(EntityShootBowEvent event) {
        Entity projectile = event.getProjectile();
        if (event.getEntity().getType() == EntityType.PLAYER) {
            Player shooter = (Player)event.getEntity();
            String permission = DamageModifier.get().getConfig().getString("damages.projectile.permissions");
            if (permission != null && shooter.hasPermission(permission)) {
                double damageMultiplier = DamageModifier.get().getConfig().getDouble("damages.projectile.arrow");
                // square the multiplier as arrow damage is calculated based on the square root of (x^2 + y^2 + z^2)
                damageMultiplier *= damageMultiplier;
                Arrow oldArrow = (Arrow)projectile;
                Vector oldSpeed = oldArrow.getVelocity();
                Vector newSpeed = new Vector(oldSpeed.getX() * damageMultiplier, oldSpeed.getY() * damageMultiplier, oldSpeed.getZ() * damageMultiplier);
                oldArrow.setVelocity(newSpeed);
            }
        }

    }
}
