package team.crazynetwork.raids;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class IOSystem {

	public static void setupFiles() {
		if (!SkyBlockRaids.getPlugin().getDataFolder().exists()){
			try { //If it doesn't then try create it
				SkyBlockRaids.getPlugin().getDataFolder().createNewFile();
			} catch (IOException ex) { //If it can't create a file, something is seriously wrong.
				SkyBlockRaids.getPlugin().getLogger().severe("Couldn't create plugin folder!");
			}
		}
		loadConfig("islands.yml");
		loadConfig("shop.yml");
	}
	
	private static void loadConfig(String configName){
		SkyBlockRaids.getPlugin().config.put(configName.split(".")[0],
				(FileConfiguration)YamlConfiguration.loadConfiguration(new File(SkyBlockRaids.getPlugin().getDataFolder(),configName)));
	}
	
	public static void loadIslands(FileConfiguration config){
		for (String sectionName:config.getKeys(false)){
			if (config.isConfigurationSection(sectionName)){
				SkyBlockRaids.getPlugin(); //To access playerIsland in a "static" way
				SkyBlockRaids.playerIsland.put(sectionName,new Island(sectionName));
			} else {
				SkyBlockRaids.getPlugin().getLogger().severe("Island config may be corrupted! " + sectionName + "'s island may be missing.");
			}
		}
	}
}
