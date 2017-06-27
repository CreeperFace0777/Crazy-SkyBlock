package team.crazynetwork.raids.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
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
	private HashMap<String, String> addMember = new HashMap<String, String>();

	public IslandCommand(SkyBlockRaids self) {
		pl = self;
	}
	
	private void sendCredit(CommandSender sender,Runnable moreInfo){
		sender.sendMessage("¡ì3¡ìm--------------------");
		sender.sendMessage("¡ìc¡ìlSky Block Raids");
		sender.sendMessage("¡ì9¡ìlAuthors:");
		sender.sendMessage("¡ì9¡ìk¡ìl::¡ìr ¡ìa¡ìlCreeperFace0777 ¡ì9¡ìk¡ìl::¡ìr");
		sender.sendMessage("¡ì9¡ìk¡ìl::¡ìr   ¡ìb¡ìlchanbakjsd    ¡ì9¡ìk¡ìl::¡ìr");
		sender.sendMessage("¡ì9¡ìk¡ìl::¡ìr ¡ìe¡ìlSenpaiOfficial  ¡ì9¡ìk¡ìl::¡ìr");
		sender.sendMessage("¡ì3¡ìm--------------------");
		pl.getServer().getScheduler().scheduleSyncDelayedTask(pl,moreInfo,20L);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			/*
			 * showing developer info and showing help info after a second not
			 * to bombard the console ;D
			 */
			
			sendCredit(sender,new Runnable() {
				public void run() {
					sender.sendMessage("¡ì3¡ìm--------------------");
					sender.sendMessage("¡ì6¡ìlFor more information");
					sender.sendMessage("¡ì6¡ìlon SkyBlockRaids, type");
					sender.sendMessage("¡ì9¡ìl/island help");
					sender.sendMessage("¡ì3¡ìm--------------------");
				}
			});
			return true;
		}

		Player p = (Player) sender;
		if (!(p.hasPermission("skyraids.island")) || args[0].equalsIgnoreCase("dev")
				|| args[0].equalsIgnoreCase("developer")) {
			sendCredit(sender,new Runnable() {
				public void run() {
					sender.sendMessage("¡ì3¡ìm--------------------");
					sender.sendMessage("¡ì6¡ìlNo permission for SkyBlockRaids");
					sender.sendMessage("¡ì6¡ìlContact an Administrator, and ask for ¡ì2skyraids.island ¡ì6¡ìlpermission.");
					sender.sendMessage("¡ì3¡ìm--------------------");
				}
			});
			return true;
		}

		// Making sure the player is allowed to execute the command.
		//
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
			return true;
		}
		if (args[0].equalsIgnoreCase("invite")) {
			/*
			 * First check if the player is the owner of an island, or is part
			 * of an island. Then we need to make sure that their is 3 arguments
			 * '/is add [player], no more, no less Then we need to make sure
			 * args[2] is actually a player, and that they are online. then the
			 * player will have 60 seconds to accept the request, and they will
			 * be added to the island. GG EZ
			 * 
			 */
			if (args.length < 3) {
				p.sendMessage("¡ìcInvalid format. - Too few arguments");
				p.sendMessage("¡ì9/invite add [Player]");
				return true;
			}
			if (args.length > 3) {
				p.sendMessage("¡ìcInvalid format. - Too many arguments");
				p.sendMessage("¡ì9/invite add [Player]");
				return true;
			}
			if (args.length == 3) {
				Player t = Bukkit.getPlayer(args[2].toString());
				if (t != null && p.isOnline()) {
					t.sendMessage(
							ChatColor.YELLOW + p.getName() + ChatColor.GOLD + "has invited you to join their island!");
					t.sendMessage(ChatColor.GOLD + "Type ¡ì9/island accept " + ChatColor.YELLOW + p.getName()
							+ ChatColor.GOLD + "to join their island!\n" + ChatColor.GRAY
							+ "You have 60 seconds to accept this invitation.");
					t.playSound(t.getLocation(), Sound.ORB_PICKUP, 10, 10);
					p.sendMessage("¡ìaA request has been sent to " + t.getName());
					addMember.put(p.getUniqueId().toString(), t.getUniqueId().toString());
					pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
						public void run() {
							if (addMember.containsValue(t)) {
								addMember.remove(p);
							}
						}
					}, 20L * 60L);
					return true;
				} else {
					p.sendMessage("¡ìcThat player is not found.");
					return true;
				}
			}
			return true;
		}
		// End of invite sub command
		/*
		 * Now we go to the /accept command. This cmd will allow people to to accept getting joined to other people's island's.
		 * First I'll check if they're in the hashmap, then I'll add them to their island via the island class.
		 */
		
		if (args[0].equalsIgnoreCase("accept")) {

			if (args.length < 3) {
				p.sendMessage("¡ìcInvalid format. - Too few arguments");
				p.sendMessage("¡ì9/invite accept [Player]");
				return true;
			}
			if (args.length > 3) {
				p.sendMessage("¡ìcInvalid format. - Too many arguments");
				p.sendMessage("¡ì9/invite accept [Player]");
				return true;
			}
			if (args.length == 3) {
				Player t = pl.getServer().getPlayer(args[2]);
				if (t != null && t.isOnline()) {
					if(addMember.containsValue(p.getUniqueId().toString())){
						//need to finish up the code.
					}
				}
			}
			return true;
		}

		return true;
	}
}
