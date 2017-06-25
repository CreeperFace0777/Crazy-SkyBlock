package team.crazynetwork.raids.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import team.crazynetwork.raids.Island;
import team.crazynetwork.raids.SkyBlockRaids;

public class IslandCommand implements CommandExecutor {

	Plugin pl;
	Island i;

	public IslandCommand(SkyBlockRaids self) {
		pl = self;
	}


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(!(sender instanceof Player)) {
		
			// showing developer
			// info and showing help
			// info after a second
			// not to bombard the
			// player ;D
		sender.sendMessage("§3§m--------------------"); 
		sender.sendMessage("§c§lSky Block Raids");
		sender.sendMessage("§9§lAuthors:");
		sender.sendMessage("§9§k§l::§r §a§lCreeperFace0777 §9§k§l::§r");
		sender.sendMessage("§9§k§l::§r   §b§lchanbakjsd    §9§k§l::§r");
		sender.sendMessage("§9§k§l::§r §e§lSenpaiOfficial  §9§k§l::§r");
		sender.sendMessage("§3§m--------------------");
		pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
			public void run() {
				sender.sendMessage("§3§m--------------------");
				sender.sendMessage("§6§lFor more information");
				sender.sendMessage("§6§lon SkyBlockRaids, type");
				sender.sendMessage("§9§l/island help");
				sender.sendMessage("§3§m--------------------");
			}
		}, 20L);
		return true;
	}


		Player p = (Player) sender;
		if (!(p.hasPermission("skyraids.island"))) {
			p.sendMessage("§cYou do not have the permission to do this!");
		} // Making sure the player is allowed to execute the command.
		if (args.length < 1) { // If the person only writes "/is, it should
			// show up the GUI"
			/*
			 * GUI Requirements: Lines of the GUI. Different items. Commands
			 * executed on click. add customizability. show specific items for
			 * specific donator's permission I have a flight to catch. Gonna
			 * quickly push this so u guys can work on it. kthxbye. See u in a
			 * week. wish me luck in hanoi! <3
			 * 
			 */
		}
		if(args[0].equalsIgnoreCase("invite")){
			//First check if the player is the owner of an island, or is part of an island.
			//Then we need to make sure that their is 3 arguments '/is add [player], no more, no less
			// Then we need to make sure args[2] is actually a player, and that they are online.
			// then the player will have 60 seconds to accept the request, and they will be added to the island.
			// GG EZ 
			
		}
		
		return true;
	}
}
