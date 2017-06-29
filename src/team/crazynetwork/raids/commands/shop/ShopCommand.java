package team.crazynetwork.raids.commands.shop;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.crazynetwork.raids.SkyBlockRaids;

public class ShopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player){
			openInv((Player) sender,"root");
		} else {
			sender.sendMessage("¡ì4ERROR¡ìf> Players only command.");
		}
		return true;
	}
	
	public void openInv(Player player,String currentLocation){
		FileConfiguration config = (FileConfiguration) SkyBlockRaids.getPlugin().getSettings(null,"shop");
		if (config.contains(currentLocation) && config.isConfigurationSection(currentLocation)){
			ConfigurationSection section = config.getConfigurationSection(currentLocation);
			Double row_temp = Math.ceil((section.getKeys(false).size()-1)/9);
			Integer row = row_temp.intValue(); //Can't find a better way to get int value from double.
			
			if (row > 6){
				Bukkit.getLogger().warning(currentLocation + " contains more than 6 rows.");
				return;
			}
			
			if (!(section.contains("name") && section.isString("name"))){
				Bukkit.getLogger().severe("Cannot read shop menu " + currentLocation + ". Missing 'name'.");
				return;
			}
			ShopListener listener = new ShopListener();
			IconMenu menu = new IconMenu(section.getString("name"),row*9,listener,SkyBlockRaids.getPlugin());
			int counter = 0;
			
			for (String subsectionName : section.getKeys(false)){
				//Checks if it is title. If yes, skip.
				if (subsectionName.equalsIgnoreCase("name") || !(section.isConfigurationSection(subsectionName))){
					continue;
				}
				ConfigurationSection subsection = section.getConfigurationSection(subsectionName);
				//Check for name and type tag. If non-existant, skip.
				if (!(subsection.contains("name") && subsection.isString("name") && subsection.contains("type") && subsection.isString("type"))){
					continue;
				}
				//Check type tag. OpenSub type must contain the sub tag.
				if (subsection.getString("type").equalsIgnoreCase("openSub")){
					if (!(subsection.contains("sub") && subsection.isString("sub"))){
						continue;
					}
				//Check type tag. Item type must contain the price tag.
				} else if (subsection.getString("type").equalsIgnoreCase("item")){
					if (!(subsection.contains("price") && subsection.isInt("price"))){
						continue;
					}
				} else {
					//If type tag isn't recognized, skip.
					continue;
				}
				//Check material tag. If invalid item, skip.
				if (Material.getMaterial(subsection.getString("display")) == null){
					continue;
				}
				
				menu.setOption(counter,new ItemStack(Material.getMaterial(subsection.getString("display"))),subsection.getString("display"),"");
				listener.setAction(counter,subsection.getString("type"));
				if (subsection.getString("type").equalsIgnoreCase("openSub")){
					listener.setTarget(counter,subsection.getString("sub"));
				} else {
					listener.setTarget(counter,subsection.getString("price"));
				}
				counter = counter + 1;
			}
			menu.open(player);
		} else {
			Bukkit.getLogger().severe("Cannot read shop menu " + currentLocation + ". Non-existant.");
		}
	}
	
	public ShopCommand getShopCommand(){
		return this;
	}
}
