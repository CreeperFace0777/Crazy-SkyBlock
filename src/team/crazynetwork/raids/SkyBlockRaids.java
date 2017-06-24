package team.crazynetwork.raids;

import org.bukkit.plugin.java.JavaPlugin;

public class SkyBlockRaids extends JavaPlugin {
	private static SkyBlockRaids self;
	
	public void onEnable(){ //Runs on startup AND when server is reloaded. DO NOT assume 0 players.
		self = this; //A workaround as static vars normally do not allow this.
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

        if(getConfig().get(settingName) != null) {
		    return getConfig().get(settingName);
        } return null;

         */
        
	}
}
