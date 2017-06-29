package team.crazynetwork.raids.commands.shop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import team.crazynetwork.raids.SkyBlockRaids;

public class ShopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		openInv("root");
		return true;
	}
	
	public void openInv(String currentLocation){
		FileConfiguration config = (FileConfiguration) SkyBlockRaids.getPlugin().getSettings(null,"shop");
		if (config.contains(currentLocation) && config.isConfigurationSection(currentLocation)){
			
		} else {
			
		}
	}
}
