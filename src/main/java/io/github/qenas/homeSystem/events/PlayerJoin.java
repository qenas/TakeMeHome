package io.github.qenas.homeSystem.events;

import io.github.qenas.homeSystem.manager.HomeManager;
import io.github.qenas.homeSystem.resourse.CoordinatesShower;
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

        if(homeManager.playerIsOnFile(player)) {
            if(homeManager.hasHome(player)) {
                Location loc = homeManager.getHome(player);
                CoordinatesShower.showHome(loc, player);
            } else {
                player.sendMessage("» You do not have a home");
            }

        } else {
            homeManager.addPlayerToFile(player);
            player.sendMessage("» You do not have a home.");
        }

    }

}
