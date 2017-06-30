package team.crazynetwork.raids.oregen;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OreGen implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args[0].equalsIgnoreCase("help")) {
				if (sender instanceof Player){ //At least try to prevent the error... Using try catch too often isn't good practice.
					Player p = (Player) sender;
					p.sendMessage("");
				} else {
					sender.sendMessage("Execute via console!");
				}
		}

		return false;
	}

	@EventHandler
	public void cobblegen(BlockFromToEvent e) {
		if (((e.getBlock().getType() == Material.LAVA) || (e.getBlock().getType() == Material.WATER)
				|| (e.getBlock().getType() == Material.STATIONARY_LAVA)
				|| (e.getBlock().getType() == Material.STATIONARY_WATER))) {
			if (e.getToBlock().equals(Material.COBBLESTONE)) {
				e.setCancelled(true);
				int percentage = new Random().nextInt(100);
				if (percentage >= 0 && percentage <= 65) {
					e.getBlock().setType(Material.STONE);
				}
				if (percentage >= 66 && percentage <= 75) {
					e.getBlock().setType(Material.COAL_ORE);
				}
				if (percentage >= 76 && percentage <= 85) {
					e.getBlock().setType(Material.IRON_ORE);
				}
				if (percentage >= 86 && percentage <= 90) {
					e.getBlock().setType(Material.LAPIS_ORE);
				}
				if (percentage >= 91 && percentage <= 95) {
					e.getBlock().setType(Material.REDSTONE_ORE);
				}
				if (percentage >= 96 && percentage <= 98) {
					e.getBlock().setType(Material.DIAMOND_ORE);
				}
				if (percentage == 99){
					e.getBlock().setType(Material.EMERALD_ORE);
				}
			}
		}
	}

	@EventHandler
	public void onLavaFix(PlayerInteractEvent e) {
		ItemStack m = e.getPlayer().getItemInHand();
		if (m.equals(Material.WOOD_PICKAXE) || m.equals(Material.STONE_PICKAXE) || m.equals(Material.IRON_PICKAXE)
				|| m.equals(Material.GOLD_PICKAXE) || e.equals(Material.DIAMOND)) {
			Action a = e.getAction();
			if (a.equals(Action.RIGHT_CLICK_BLOCK)) {
				if (e.getClickedBlock().equals(Material.OBSIDIAN)) {
					e.getClickedBlock().setType(Material.LAVA);
				}
			}

		}
	}

}
