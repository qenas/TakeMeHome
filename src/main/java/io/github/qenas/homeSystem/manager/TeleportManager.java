package io.github.qenas.homeSystem.manager;

import io.github.qenas.homeSystem.tasks.TeleportTask;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportManager {
    private JavaPlugin plugin;
    private CooldownManager cooldownManager;
    private Map<UUID, TeleportTask> activeTask = new HashMap<>();

    public TeleportManager(JavaPlugin plugin, CooldownManager cooldownManager) {
        this.plugin = plugin;
        this.cooldownManager = cooldownManager;
    }

    public void startTeleport(Player player, Location home) {
        UUID playerUUID = player.getUniqueId();

        if(activeTask.containsKey(playerUUID)) {
            player.sendMessage("» You already have a teleport pending.");
            return;
        }

        if(!cooldownManager.hasCooldown(player)) { //verify if the command has been used early by the player
            TeleportTask teleportTask = new TeleportTask(plugin, this, player, home);
            activeTask.put(playerUUID, teleportTask);

            teleportTask.runTaskTimer(plugin, 0, 20L);
        } else {
            player.sendMessage(ChatColor.RED + "You can not use this command right now. You must wait " + cooldownManager.getCooldown(player) + " seconds.");
        }

    }

    public void cancelTeleport(Player player) {
        TeleportTask task = activeTask.remove(player.getUniqueId());
        if(task != null) {
            task.cancel();
        }
    }

    public void finishTeleport(Player player, boolean success) {
        activeTask.remove(player.getUniqueId());

        if(success) {
            cooldownManager.setCooldown(player, 60L);
        }
    }

}
