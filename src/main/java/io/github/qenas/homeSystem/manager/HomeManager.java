package io.github.qenas.homeSystem.manager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class HomeManager {
    private JavaPlugin plugin;
    private File file;
    private FileConfiguration homeFile;
    private Map<UUID, Location> homeLocation = new HashMap<>();

    public HomeManager(JavaPlugin plugin){
        this.plugin = plugin;
        setupFile();
        loadHomes();
    }






    private void setupFile() {
        file = new File(plugin.getDataFolder(),"home_file.yml");
        boolean fileExists = true;
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            fileExists = false;
            System.out.println("Creating home_file.yml...");
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("home_file.yml was found");
        }

        homeFile = YamlConfiguration.loadConfiguration(file);

        if(!fileExists) {
            homeFile.set("home.players", new ArrayList<String>());
            saveFile();
        }
    }

    private void saveFile() {
        try {
            homeFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reloadFile() {
        homeFile = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getHomeFile(){
        return homeFile;
    }

    public boolean playerIsOnFile(Player player) {
        String path = "home.players." + player.getUniqueId().toString();
        return homeFile.contains(path);
    }

    public boolean hasHome(Player player) {
        if(getHome(player) != null) {
            return true;
        }
        return false;
    }

    public void addPlayerToFile(Player player) {
        String path = "home.players." + player.getUniqueId().toString();
        homeFile.set(path + ".name", player.getName());
        saveFile();
        homeLocation.put(player.getUniqueId(), null); //puts the player to the map
    }


    public void saveHome(Player player, Location loc) {
        String path = "home.players." + player.getUniqueId().toString();
        String locationPath = path + ".location";

        homeFile.set(path + ".name", player.getName());
        homeFile.set(locationPath + ".world", loc.getWorld().getName());
        homeFile.set(locationPath + ".x", loc.getX());
        homeFile.set(locationPath + ".y", loc.getY());
        homeFile.set(locationPath + ".z", loc.getZ());
        homeFile.set(locationPath + ".yaw", loc.getYaw());
        homeFile.set(locationPath + ".pitch", loc.getPitch());

        saveFile();

        homeLocation.put(player.getUniqueId(), loc);
    }

    public Location getHome(Player player) {
        return homeLocation.get(player.getUniqueId());

    }


    public void loadHomes() {
        if(!homeFile.contains("home.players")) {
            return;
        }
        for(String key: homeFile.getConfigurationSection("home.players").getKeys(false)){
            String path = "home.players." + key + ".location";

            World world = Bukkit.getWorld(homeFile.getString(path + ".world"));
            double x = homeFile.getDouble(path + ".x");
            double y = homeFile.getDouble(path + ".y");
            double z = homeFile.getDouble(path + ".z");
            float yaw = (float) homeFile.getDouble(path + ".yaw");
            float pitch = (float) homeFile.getDouble(path + ".pitch");

            UUID playerUUID = UUID.fromString(key);
            Location loc = new Location(world, x, y, z, yaw, pitch);

            homeLocation.put(playerUUID, loc);
        }




    }




}
