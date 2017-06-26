package team.crazynetwork.raids;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import team.crazynetwork.raids.commands.IslandCommand;
import team.crazynetwork.raids.skyfactory.Crook;
import team.crazynetwork.raids.skyfactory.Hammer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

public class SkyBlockRaids extends JavaPlugin {
    public static Hashtable<String, FileConfiguration> config = new Hashtable<>();
    public static List<Island> islands = new ArrayList<>();
    public static HashMap<String, Island> playerIsland = new HashMap<>();
    private static SkyBlockRaids self;

    public static SkyBlockRaids getPlugin() { //Give other classes direct access to the plugin's methods.
        return self;
    }

    @Override
    public void onEnable() {

        self = this; //A workaround as static vars normally do not allow this.
        IOSystem.setupFiles();
        Bukkit.getPluginCommand("island").setExecutor(new IslandCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new Crook(), this); //Adds the Crook Listener
        Bukkit.getServer().getPluginManager().registerEvents(new Hammer(), this); //Adds the Hammer Listener

        getServer().addRecipe(Crook.recipe()[0]); //Both crook recipes
        getServer().addRecipe(Crook.recipe()[1]);

        getServer().addRecipe(Hammer.recipe()[0][0]); //All the hammers
        getServer().addRecipe(Hammer.recipe()[0][1]);
        getServer().addRecipe(Hammer.recipe()[1][0]);
        getServer().addRecipe(Hammer.recipe()[1][1]);
        getServer().addRecipe(Hammer.recipe()[2][0]);
        getServer().addRecipe(Hammer.recipe()[2][1]);
        getServer().addRecipe(Hammer.recipe()[3][0]);
        getServer().addRecipe(Hammer.recipe()[3][1]);
        getServer().addRecipe(Hammer.recipe()[4][0]);
        getServer().addRecipe(Hammer.recipe()[4][1]);

        IOSystem.loadIslands((FileConfiguration) getSettings(null, "islands"));
    }

    @Override
    public void onDisable() {
        IOSystem.saveFiles();
    }

    public Object getSettings(String settingName, String configName) {
        if (configName.contains("."))
            configName = configName.split(".")[0];
        if (settingName == null) {
            return config.get(configName);
        }
        return config.get(configName).get(settingName);
    }
}
