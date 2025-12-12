package io.github.qenas.homeSystem.manager.events;

import io.github.qenas.homeSystem.manager.HomeManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    private HomeManager homeManager;

    public PlayerJoin (HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @EventHandler
    public void onPlayerJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if(!homeManager.playerHasHome(player)) {
            homeManager.saveHome(player, null);
            player.sendMessage("» You do not have a home.");
        } else {
            if(homeManager.getHome(player) == null) {
                player.sendMessage("» You do not have a home");
            } else {
                Location loc = homeManager.getHome(player);
                player.sendMessage(ChatColor.GREEN + "» You have a home, the coordinates are: ");
                player.sendMessage(ChatColor.GOLD + "X: " + ChatColor.WHITE + loc.getX());
                player.sendMessage(ChatColor.GOLD + "Y: " + ChatColor.WHITE + loc.getY());
                player.sendMessage(ChatColor.GOLD + "Z: " + ChatColor.WHITE + loc.getZ());
            }
        }

    }

}
