package team.crazynetwork.raids;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import team.crazynetwork.raids.commands.IslandCommand;
import team.crazynetwork.raids.oregen.OreGen;
import team.crazynetwork.raids.skyfactory.Crook;

public class SkyBlockRaids extends JavaPlugin {
	private static SkyBlockRaids self;
	FileConfiguration islandConfig;
	File iFile;
	
	@Override
	public void onEnable() { //Runs on startup AND when server is reloaded. DO NOT assume 0 players.
		self = this; //A workaround as static vars normally do not allow this.
    	IOSystem.setupFiles();
    	
		Bukkit.getPluginCommand("island").setExecutor(new IslandCommand(self));
		Bukkit.getPluginCommand("oregen").setExecutor(new OreGen());
		//Please remember to add the /raid command! That will not be part of the island command! 
		//Please remember to add the /admin (idk) command! That will not be part of the island/raid command! 
		Bukkit.getServer().getPluginManager().registerEvents(new OreGen(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Crook(), this); //Adds the Crook Listener
        
        getServer().addRecipe(Crook.recipe()[0]); //Both crook recipes
        getServer().addRecipe(Crook.recipe()[1]);
        
        IOSystem.loadIslands((FileConfiguration) getSettings(null,"islands"));
	}
	
	@Override
	public void onDisable(){ //Save all the file here when the plugin is disabled.
        IOSystem.saveFiles();
	}
	
    public static Hashtable<String,FileConfiguration> config = new Hashtable<>();
	
    public static List<Island> islands = new ArrayList<>();
    public static HashMap<String, Island> playerIsland = new HashMap<>();

    public static SkyBlockRaids getPlugin() { //Give other classes direct access to the plugin's methods.
        return self;
    }
	
	public Object getSettings(String settingName,String configName){
		configName = configName.split(".")[0];
		if (settingName == null){
			return config.get(configName);
		}
		return config.get(configName).get(settingName);
	}
}
