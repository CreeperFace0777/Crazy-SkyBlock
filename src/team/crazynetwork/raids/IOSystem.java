package team.crazynetwork.raids;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class IOSystem {

	public static void setupFiles() {
		if (!SkyBlockRaids.getPlugin().getDataFolder().exists()){
			//If it doesn't then try create it
			SkyBlockRaids.getPlugin().getDataFolder().mkdir();

		}
		loadConfig("islands.yml");
		loadConfig("shop.yml");
	}
	
	private static void loadConfig(String configName){
		SkyBlockRaids.getPlugin();
		File file = new File(SkyBlockRaids.getPlugin().getDataFolder(),configName);
		if (!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e){
				SkyBlockRaids.getPlugin().getLogger().severe("Couldn't create " + configName);
				return; //Do not continue with code if the file cannot be created.
			}
		}
		if(configName.contains("."))
		SkyBlockRaids.config.put(configName.split(".")[0],
				(FileConfiguration)YamlConfiguration.loadConfiguration(new File(SkyBlockRaids.getPlugin().getDataFolder(),configName)));
		else SkyBlockRaids.config.put(configName,
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
	
	public static void saveFiles(){
		SkyBlockRaids.getPlugin(); //To access playerIsland in a "static" way
		setupFiles(); //Reload config
		FileConfiguration config = (FileConfiguration) SkyBlockRaids.getPlugin().getSettings(null,"islands");
		for (String playerId:SkyBlockRaids.playerIsland.keySet()){
			if (!(config.isConfigurationSection(playerId))){
				config.createSection(playerId);
			}
			ConfigurationSection section = config.getConfigurationSection(playerId);
			section.set("x",SkyBlockRaids.playerIsland.get(playerId).x);
			section.set("y",SkyBlockRaids.playerIsland.get(playerId).y);
			section.set("z",SkyBlockRaids.playerIsland.get(playerId).z);
			section.set("balance",SkyBlockRaids.playerIsland.get(playerId).balance);
			section.set("raidableTime",SkyBlockRaids.playerIsland.get(playerId).raidableTime);
			section.set("members",SkyBlockRaids.playerIsland.get(playerId).members);
		}
	}
}