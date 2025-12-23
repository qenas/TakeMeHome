package io.github.qenas.homeSystem.tasks;

import io.github.qenas.homeSystem.manager.CooldownManager;
import io.github.qenas.homeSystem.manager.TeleportManager;
import io.github.qenas.homeSystem.manager.TeleportScoreboardManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class TeleportTask extends BukkitRunnable {

    private JavaPlugin plugin;
    private TeleportManager teleportManager;
    private TeleportScoreboardManager teleportScoreboardManager;
    private Player player;
    private Location home;
    private Block startLocation;
    private int timer;

    public TeleportTask(JavaPlugin plugin, TeleportManager teleportManager, TeleportScoreboardManager teleportScoreboardManager, Player player, Location home) {
        this.plugin = plugin;
        this.teleportManager = teleportManager;
        this.teleportScoreboardManager = teleportScoreboardManager;
        this.player = player;
        this.home = home;
        this.startLocation = player.getLocation().getBlock(); // the location where the player used the command
        timer = 10;
    }

    @Override
    public void run() {

        if(!player.isOnline()) {
            teleportManager.finishTeleport(player, false);
            cancel();
            return;
        }

        if(!player.getLocation().getBlock().equals(startLocation)) { // verify if the player moved from the location where the command was executed
            player.sendMessage(ChatColor.RED + "» The teleport was cancelled because you moved, try again.");
            teleportManager.finishTeleport(player, false);
            cancel();
            return;
        }

        teleportScoreboardManager.update(player, timer);

        if(timer <= 0) { //timer = 0 - > teleports the player
            player.teleport(home);
            player.sendMessage(ChatColor.GREEN + "» You have been teleported to your house.");
            teleportManager.finishTeleport(player, true);
            cancel();
            return;
        }

        player.sendMessage(ChatColor.GREEN + "» Teleporting in " + timer +  " seconds...");
        timer--;
    }
}
