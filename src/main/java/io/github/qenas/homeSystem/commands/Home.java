package io.github.qenas.homeSystem.commands;

import io.github.qenas.homeSystem.manager.CooldownManager;
import io.github.qenas.homeSystem.manager.HomeManager;
import org.apache.maven.model.profile.activation.JdkVersionProfileActivator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Home implements CommandExecutor {

    private HomeManager homeManager;
    private CooldownManager cooldownManager;
    private final JavaPlugin plugin;

    public Home (HomeManager homeManager, CooldownManager cooldownManager, JavaPlugin plugin) {
        this.homeManager = homeManager;
        this.cooldownManager = cooldownManager;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        if(!(commandSender instanceof Player)) {
            return true;
        }

        Player player = (Player) commandSender;

        if(homeManager.playerHasHome(player)) {
            if(!cooldownManager.hasCooldown(player)) {
                Location playerHome = homeManager.getHome(player);
                teleportToHome(player, playerHome);
                player.sendMessage(ChatColor.GREEN + "» You have been teleported to your house.");
                cooldownManager.setCooldown(player, 60);
            } else {
                player.sendMessage(ChatColor.RED + "You can not use this command right now. You must wait + " + cooldownManager.getCooldown(player) + " seconds.");
            }
        } else {
            player.sendMessage("» You do not have a home");
        }


        return true;
    }


    private void teleportToHome(Player player, Location home) {
        player.sendMessage(ChatColor.GREEN + "» Teleporting to your home in 10 seconds...");
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if(!player.isOnline()) {
                return;
            }
            player.teleport(home);
            player.sendMessage(ChatColor.GREEN + "» Teleporting...");
        }, 20L * 10); // 20 ticks = 1 second -> 10 seconds = 20 ticks * 10
    }
}
