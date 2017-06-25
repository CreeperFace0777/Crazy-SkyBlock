package team.crazynetwork.raids;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import team.crazynetwork.raids.skyfactory.Crook;

import java.io.File;
import java.io.IOException;

public class SkyBlockRaids extends JavaPlugin {
    private static SkyBlockRaids self;
    FileConfiguration islandConfig;
    File iFile;

    public static SkyBlockRaids getPlugin() { //Give other classes direct access to the plugin's methods.
        return self;
    }

    @Override
    public void onEnable() {

        self = this; //A workaround as static vars normally do not allow this.

        Bukkit.getServer().getPluginManager().registerEvents(new Crook(), this); //Adds the Crook Listener
        getServer().addRecipe(Crook.recipe()[0]); //Both crook recipes
        getServer().addRecipe(Crook.recipe()[1]);

    }

    public Object getSettings(String settingName) {
        if (islandConfig.get(settingName) != null) {
            return islandConfig.get(settingName);
        }
        return null;

    }

    private void setupFiles() {
        iFile = new File(getDataFolder(), "islands.yml");

        if (!getDataFolder().exists()) { //Check if the plugin folder (where the config and other files are stored) exists.
            try { //If it doesn't then try create it
                getDataFolder().createNewFile();
            } catch (IOException ex) { //If it can't create a file, something is seriously wrong.
                getLogger().severe("Couldn't create plugin folder!");
            }
        }

        if (!iFile.exists()) { //If the islands.yml file doesn't exist.
            try {
                iFile.createNewFile(); //Try create it
            } catch (IOException e) {
                getLogger().severe("Couldn't create islands.yml!"); //Couldn't create the islands.yml files
            }
        }

        islandConfig = YamlConfiguration.loadConfiguration(iFile); //Make the islandConfig equal the islands.yml file.
    }
}
