package com.gmail.ryderzye.damagemodifier;

import com.gmail.ryderzye.damagemodifier.Listeners.EntityDamageByEntityListener;
import com.gmail.ryderzye.damagemodifier.Listeners.EntityShootBowEventListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class DamageModifier extends JavaPlugin implements Listener {
    public static DamageModifier dm;

    public DamageModifier() {
    }

    public void onEnable() {
        dm = this;
        this.saveDefaultConfig();
        this.getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        this.getServer().getPluginManager().registerEvents(new EntityShootBowEventListener(), this);
        this.getLogger().info("DamageModifier is enabled");
    }

    public void onDisable() {
        this.getLogger().info("DamageModifier is disabled");
    }

    public static DamageModifier get() {
        return dm;
    }
}
