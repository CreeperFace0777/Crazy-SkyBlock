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
import team.crazynetwork.raids.skyfactory.SilkWorm;

public class SkyBlockRaids extends JavaPlugin {
	private static SkyBlockRaids self;
	public SilkWorm sw;

    public static Hashtable<String,FileConfiguration> config = new Hashtable<>();
    public static List<Island> islands = new ArrayList<>();
    public static HashMap<String, Island> playerIsland = new HashMap<>();

	public static SkyBlockRaids getPlugin() { // Give other classes direct access to the plugin's methods.
		return self;
	}

	@Override
	public void onEnable() {

		self = this; // A workaround as static vars normally do not allow this.
    	IOSystem.setupFiles();
		
		Bukkit.getPluginCommand("island").setExecutor(new IslandCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new Crook(), this); //Adds the Crook Listener
        
        getServer().addRecipe(Crook.recipe()[0]); //Both crook recipes
        getServer().addRecipe(Crook.recipe()[1]);

        IOSystem.loadIslands((FileConfiguration) getSettings(null,"islands"));
		// Onenable for spreading wilk worms, every 30 seconds, after 3 minutes
		// the server starts up to make sure everything is gud
		getServer().getScheduler().scheduleSyncRepeatingTask(this,sw.getInfestRunnable(), 20L * 60L * 3L, 20L * 30L);
		
		getServer().addRecipe(Crook.recipe()[0]); // Both crook recipes
		getServer().addRecipe(Crook.recipe()[1]);
		getServer().addRecipe(SilkWorm.recipe());
		// TODO: Handle loading islands from islands.yml
	}


	public Object getSettings(String settingName,String configName){
		configName = configName.split(".")[0];
		if (settingName == null){
			return config.get(configName);
		}
		return config.get(configName).get(settingName);
	}

    @Override
    public void onDisable() {
        IOSystem.saveFiles();
    }
}
