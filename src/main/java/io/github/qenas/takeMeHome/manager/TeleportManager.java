package io.github.qenas.takeMeHome.manager;

import io.github.qenas.takeMeHome.tasks.TeleportTask;
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
    private ScoreboardManager scoreboardManager;
    private Map<UUID, TeleportTask> activeTask = new HashMap<>();

    public TeleportManager(JavaPlugin plugin, CooldownManager cooldownManager, ScoreboardManager scoreboardManager) {
        this.plugin = plugin;
        this.cooldownManager = cooldownManager;
        this.scoreboardManager = scoreboardManager;
    }

    public void startTeleport(Player player, Location home) {
        UUID playerUUID = player.getUniqueId();

        if(activeTask.containsKey(playerUUID)) {
            player.sendMessage("» You already have a teleport pending.");
            return;
        }



        if(!cooldownManager.hasCooldown(player)) { //verify if the command has been used early by the player
            boolean isCooldownAllowed = this.plugin.getConfig().getBoolean("cooldown");

            if(isCooldownAllowed) { //config file - > "cooldown = true"
                TeleportTask teleportTask = new TeleportTask(plugin, this, scoreboardManager, player, home);
                activeTask.put(playerUUID, teleportTask);

                scoreboardManager.display(player, 10);

                teleportTask.runTaskTimer(plugin, 0, 20L);
            } else { //config file - > "cooldown = false" (teleports the player without hesitation)
                player.teleport(home);
            }
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

        scoreboardManager.remove(player);

        if(success) {
            cooldownManager.setCooldown(player, 60L);

        }
    }

}
