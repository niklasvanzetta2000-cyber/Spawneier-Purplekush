package de.example.spawneggdrops;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ThreadLocalRandom;

public class SpawnEggDrops extends JavaPlugin implements Listener {

    private double chancePercent;
    private boolean requirePlayerKill;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        chancePercent = getConfig().getDouble("chance-percent", 1.0);
        requirePlayerKill = getConfig().getBoolean("require-player-kill", true);
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("SpawnEier-PurpleKush aktiv - Chance: " + chancePercent + " %");
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity victim = event.getEntity();

        if (victim instanceof Player) {
            return;
        }

        if (requirePlayerKill && victim.getKiller() == null) {
            return;
        }

        Material egg = Material.matchMaterial(victim.getType().name() + "_SPAWN_EGG");
        if (egg == null) {
            return;
        }

        if (ThreadLocalRandom.current().nextDouble() * 100.0 < chancePercent) {
            event.getDrops().add(new ItemStack(egg));
        }
    }
}
