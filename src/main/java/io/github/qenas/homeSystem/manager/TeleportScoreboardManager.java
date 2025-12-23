package io.github.qenas.homeSystem.manager;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TeleportScoreboardManager {

    private static final String SPACER = "----------------------";

    private final JavaPlugin plugin;
    private final ScoreboardManager manager;
    private final Map<UUID, Scoreboard> activeSB = new HashMap<>();

    public TeleportScoreboardManager(JavaPlugin plugin) {
        this.plugin = plugin;
        manager = Bukkit.getScoreboardManager();
    }

    public void display(Player player, int seconds) {


        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("home_tp", Criteria.DUMMY, ChatColor.GOLD + "Home");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);



        Score line1 = objective.getScore(ChatColor.GRAY + SPACER);
        line1.setScore(1);
        Score teleportCooldown = objective.getScore(ChatColor.GREEN + "» Time remaining: " + seconds);
        teleportCooldown.setScore(2);
        Score line2 = objective.getScore(ChatColor.GRAY + SPACER);
        line2.setScore(3);

        player.setScoreboard(scoreboard);
        activeSB.put(player.getUniqueId(), scoreboard);
    }

    public void update(Player player, int seconds) {
        Scoreboard scoreboard = activeSB.get(player.getUniqueId());
        if(scoreboard == null) {
            return;
        }

        Objective objective = scoreboard.getObjective("home_tp");
        if(objective == null) {
            return;
        }

        scoreboard.getEntries().forEach(scoreboard::resetScores);

        Score line1 = objective.getScore(ChatColor.GRAY + SPACER);
        line1.setScore(1);
        Score teleportCooldown = objective.getScore(ChatColor.GREEN + "» Time remaining: " + seconds);
        teleportCooldown.setScore(2);
        Score line2 = objective.getScore(ChatColor.GRAY + SPACER);
        line2.setScore(3);

    }


    public void remove(Player player) {
        activeSB.remove(player.getUniqueId());
        player.setScoreboard(manager.getMainScoreboard());


    }






}
