package team.crazynetwork.raids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import team.crazynetwork.raids.commands.IslandCommand;
import team.crazynetwork.raids.skyfactory.Crook;

public class SkyBlockRaids extends JavaPlugin {
    private static SkyBlockRaids self;
	
    public static Hashtable<String,FileConfiguration> config = new Hashtable<>();
	
    public static List<Island> islands = new ArrayList<>();
    public static HashMap<String, Island> playerIsland = new HashMap<>();

    public static SkyBlockRaids getPlugin() { //Give other classes direct access to the plugin's methods.
        return self;
    }

    @Override
    public void onEnable() {
    	IOSystem.setupFiles();
		self = this; //A workaround as static vars normally do not allow this.
		
		Bukkit.getPluginCommand("island").setExecutor(new IslandCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new Crook(), this); //Adds the Crook Listener
        
        getServer().addRecipe(Crook.recipe()[0]); //Both crook recipes
        getServer().addRecipe(Crook.recipe()[1]);

        IOSystem.loadIslands((FileConfiguration) getSettings(null,"islands"));
    }

    @Override
    public void onDisable() {
        IOSystem.saveFiles();
    }
	
	public Object getSettings(String settingName,String configName){
		configName = configName.split(".")[0];
		if (settingName == null){
			return config.get(configName);
		}
		return config.get(configName).get(settingName);
	}
}
