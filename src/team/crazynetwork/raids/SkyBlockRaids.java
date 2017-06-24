package team.crazynetwork.raids;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import team.crazynetwork.raids.commands.IslandCommand;

import java.io.File;
import java.io.IOException;

public class SkyBlockRaids extends JavaPlugin {
	private static SkyBlockRaids self;
	FileConfiguration islandConfig;
	File iFile;
	
	public void onEnable() { //Runs on startup AND when server is reloaded. DO NOT assume 0 players.
		self = this; //A workaround as static vars normally do not allow this.
		Bukkit.getPluginCommand("island").setExecutor(new IslandCommand());
	}
	
	public void onDisable(){ //Save all the file here when the plugin is disabled.
		
	}
	
	public static SkyBlockRaids getPlugin(){ //Give other classes direct access to the plugin's methods.
		return self;
	}
	
	public Object getSettings(String settingName){ 
		//getConfig conflicts with JavaPlugin.getConfig
		//Returns a value from the config file.

		//To be implemented. A return of a placeholder for now.
		return 1000;

		//Code Suggestion:
        /*

        if(islandConfig.get(settingName) != null) {
		    return islandConfig.get(settingName);
        } return null;

         */
        
	}

	private void setupFiles() {
		iFile = new File(getDataFolder(), "islands.yml");

		if(!getDataFolder().exists()) { //Check if the plugin folder (where the config and other files are stored) exists.
			try { //If it doesn't then try create it
				getDataFolder().createNewFile();
			} catch (IOException ex) { //If it can't create a file, something is seriously wrong.
				getLogger().severe("Couldn't create plugin folder!");
			}
		}

		if(!iFile.exists()) { //If the islands.yml file doesn't exist.
			try {
				iFile.createNewFile(); //Try create it
			} catch (IOException e) {
				getLogger().severe("Couldn't create islands.yml!"); //Couldn't create the islands.yml files
			}
		}

		islandConfig = YamlConfiguration.loadConfiguration(iFile); //Make the islandConfig equal the islands.yml file.
	}
}
