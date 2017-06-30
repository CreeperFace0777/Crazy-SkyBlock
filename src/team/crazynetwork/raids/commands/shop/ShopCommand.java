package team.crazynetwork.raids.commands.shop;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
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
					listener.setTarget(counter,Integer.toString(subsection.getInt("price")));
				}
				counter = counter + 1;
			}
			menu.open(player);
		} else {
			Bukkit.getLogger().severe("Cannot read shop menu " + currentLocation + ". Non-existant.");
		}
	}
	
	@SuppressWarnings("deprecation")
	public void openPurchase(Player player,ItemStack item,int price,int purchaseAmount){
		if (item == null){
			return;
		}
		IconMenu menu = new IconMenu((String)SkyBlockRaids.getPlugin().getSettings("purchasePrefix","shop") + item.getItemMeta().getDisplayName(),54,new BuySellMenuListener(price,purchaseAmount,player,item),SkyBlockRaids.getPlugin());
		
		if (purchaseAmount > 1){
			menu.setOption(27,new ItemStack(Material.REDSTONE_BLOCK),"¡ì4¡ìlSET TO 1","");
			menu.setOption(28,new ItemStack(Material.STAINED_GLASS,1,DyeColor.RED.getData()),"¡ì4¡ìlREMOVE 10","");
			menu.setOption(29,new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.RED.getData()),"¡ì4¡ìlREMOVE 1","");
		}
		menu.setOption(31,item,item.getItemMeta().getDisplayName(),"");
		if (purchaseAmount < 64){
			menu.setOption(33,new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.GREEN.getData()),"¡ìa¡ìlADD 1","");
			menu.setOption(34,new ItemStack(Material.STAINED_GLASS,1,DyeColor.GREEN.getData()),"¡ìa¡ìlADD 10","");
			menu.setOption(35,new ItemStack(Material.EMERALD_BLOCK,1,DyeColor.GREEN.getData()),"¡ìa¡ìlSET TO 64","");
		}
		if (SkyBlockRaids.getPlugin().findIsland(player).getBalance() >= (price*purchaseAmount)){
			menu.setOption(48,new ItemStack(Material.EMERALD),"¡ìa¡ìlBUY" + purchaseAmount,"Costs $" + Integer.toString(price*purchaseAmount));
		} else {
			menu.setOption(48,new ItemStack(Material.STAINED_GLASS_PANE,1,DyeColor.GRAY.getData()),"¡ìa¡ìlBUY" + purchaseAmount,"Costs $" + Integer.toString(price*purchaseAmount) + ". Not enough money!");
		}
		menu.setOption(49,new ItemStack(Material.BARRIER),"¡ìa¡ìlCANCEL" + purchaseAmount,"");
		if (player.getInventory().contains(item,purchaseAmount)){
			menu.setOption(50,new ItemStack(Material.REDSTONE_BLOCK),"¡ì4¡ìlSELL" + purchaseAmount,"Gives $" + Integer.toString(price*purchaseAmount));
		} else {
			menu.setOption(50,new ItemStack(Material.REDSTONE_BLOCK),"¡ì4¡ìlSELL" + purchaseAmount,"Gives $" + Integer.toString(price*purchaseAmount) + ". Not enough item.");
		}
		
		menu.open(player);
	}
	
	public ShopCommand getShopCommand(){
		return this;
	}
}
