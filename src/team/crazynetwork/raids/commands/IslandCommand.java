package team.crazynetwork.raids.commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.plugin.Plugin;

import team.crazynetwork.raids.Island;
import team.crazynetwork.raids.SkyBlockRaids;

public class IslandCommand implements CommandExecutor {

	Plugin pl;
	Island i;
	private HashMap<Player, Player> addMember = new HashMap<Player, Player>();

	public IslandCommand(SkyBlockRaids self) {
		pl = self;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!(sender instanceof Player)) {
			/*
			 * showing developer info and showing help info after a second not
			 * to bombard the player ;D
			 */
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
		if (!(p.hasPermission("skyraids.island")) || args[0].equalsIgnoreCase("dev")
				|| args[0].equalsIgnoreCase("developer")) {
			// Show credits
			p.sendMessage("§3§m--------------------");
			p.sendMessage("§c§lSky Block Raids");
			p.sendMessage("§9§lAuthors:");
			p.sendMessage("§9§k§l::§r §a§lCreeperFace0777 §9§k§l::§r");
			p.sendMessage("§9§k§l::§r   §b§lchanbakjsd    §9§k§l::§r");
			p.sendMessage("§9§k§l::§r §e§lSenpaiOfficial  §9§k§l::§r");
			p.sendMessage("§3§m--------------------");

			pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
				public void run() {
					sender.sendMessage("§3§m--------------------");
					sender.sendMessage("§6§lNo permission for SkyBlockRaids");
					sender.sendMessage("§6§lContact an Administrator, and ask for §2skyraids.island §6§lpermission.");
					sender.sendMessage("§3§m--------------------");
				}
			}, 20L);
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
				p.sendMessage("§cInvalid format. - Too few arguments");
				p.sendMessage("§9/invite add [Player]");
				return true;
			}
			if (args.length > 3) {
				p.sendMessage("§cInvalid format. - Too many arguments");
				p.sendMessage("§cInvalid format.");
				p.sendMessage("§9/invite add [Player]");
				return true;
			}
			if (args.length == 3) {
				Player t = Bukkit.getPlayer(args[2].toString());
				if (t != null && p.isOnline()) {
					t.sendMessage(
							ChatColor.YELLOW + p.getName() + ChatColor.GOLD + "has invited you to join their island!");
					t.sendMessage(ChatColor.GOLD + "Type §9/island accept " + ChatColor.YELLOW + p.getName()
							+ ChatColor.GOLD + "to join their island!\n" + ChatColor.GRAY
							+ "You have 60 seconds to accept this invitation.");
					t.playSound(t.getLocation(), Sound.ORB_PICKUP, 10, 10);
					p.sendMessage("¶aA request has been sent to " + t.getName());
					addMember.put(p, t);
					pl.getServer().getScheduler().scheduleSyncDelayedTask(pl, new Runnable() {
						public void run() {
							if (addMember.containsValue(t)) {
								addMember.remove(p);
							}
						}
					}, 20L * 60L);
					return true;
				} else {
					p.sendMessage("§cThat player is not found.");
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
				p.sendMessage("§cInvalid format. - Too few arguments");
				p.sendMessage("§9/invite accept [Player]");
				return true;
			}
			if (args.length > 3) {
				p.sendMessage("§cInvalid format. - Too many arguments");
				p.sendMessage("§9/invite accept [Player]");
				return true;
			}
			if (args.length == 3) {
				Player t = pl.getServer().getPlayer(args[2]);
				if (t != null && t.isOnline()) {
					if(addMember.containsValue(p)){
						
					}
				}
			}
			return true;
		}

		return true;
	}
}
