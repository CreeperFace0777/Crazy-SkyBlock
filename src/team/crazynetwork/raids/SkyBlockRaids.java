package team.crazynetwork.raids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import team.crazynetwork.raids.commands.IslandCommand;
import team.crazynetwork.raids.commands.shop.ShopCommand;
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
        self = this; //A workaround as static vars normally do not allow this.
    	IOSystem.setupFiles();
    	
		Bukkit.getPluginCommand("island").setExecutor(new IslandCommand(this));
		Bukkit.getPluginCommand("shop").setExecutor(new ShopCommand());
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
	
	public Island findIsland(Player player){
		Boolean playersIsland = false;
		for (String iName:SkyBlockRaids.playerIsland.keySet()){
			Island i = SkyBlockRaids.playerIsland.get(iName);
			if (i.getOwner().equals(player)){
				playersIsland = true;
			} else {
				for (Player p:i.getMembers()){
					if (p.equals(player)){
						playersIsland = true;
					}
				}
			}
			if (playersIsland){
				return i;
			}
		}
		return null;
	}
}
