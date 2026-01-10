package io.github.qenas.homeSystem.manager;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private Map<UUID, Long> cooldownMap = new HashMap<>();
    private JavaPlugin plugin;

    public CooldownManager (JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void setCooldown(Player player, long cooldownTime) {
        cooldownMap.put(player.getUniqueId(), System.currentTimeMillis() + cooldownTime * 1000);
        System.out.println("setCooldown");
    }

    public long getCooldown(Player player) {
        return (cooldownMap.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000; //returns the cooldown time in seconds
    }

    public boolean hasCooldown(Player player) {
        return cooldownMap.containsKey(player.getUniqueId()) && System.currentTimeMillis() < cooldownMap.get(player.getUniqueId());
    }



}
