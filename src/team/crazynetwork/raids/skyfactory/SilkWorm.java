package team.crazynetwork.raids.skyfactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import team.crazynetwork.raids.SkyBlockRaids;

public class SilkWorm implements Listener {
	private ArrayList<Location> isSilkwormed = new ArrayList<Location>();
	
	public ArrayList<Location> leafwormed(){
		return isSilkwormed;
	}

	public static FurnaceRecipe recipe() {

		ItemStack silkworm = new ItemStack(Material.STRING, 1);
		ItemMeta silkmeta = silkworm.getItemMeta();
		silkmeta.setDisplayName("§r§cSilkworm");
		List<String> lore = silkmeta.getLore();
		lore.add("§7Right click on a §2leaf block§7 to infect it.");
		lore.add("§6Cook§7 me to restore 2 hunger (1 drumstick)");
		silkmeta.setLore(lore);
		silkworm.setItemMeta(silkmeta);
		ItemStack cookedworm = new ItemStack(Material.STRING, 1);
		ItemMeta cookedmeta = cookedworm.getItemMeta();
		cookedmeta.setDisplayName("§r§cCooked Silkworm");
		lore.clear();
		lore.add("§7Eat me to restore §62 hunger.");
		cookedworm.setItemMeta(cookedmeta);
		FurnaceRecipe recette = new FurnaceRecipe(cookedworm, Material.STRING);
		return recette;
	}

	@EventHandler
	public void onTreeInfect(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			Block b = e.getClickedBlock();
			Player p = e.getPlayer();
			if (b.getType().equals(Material.LEAVES) || b.getType().equals(Material.LEAVES_2)) {
				try {
					if (p.getItemInHand().equals(Material.STRING) && p.getItemInHand().hasItemMeta()
							&& p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.stripColor("Silkworm"))
							&& p.getItemInHand().getItemMeta().getLore().size() == 2
							&& p.getItemInHand().getItemMeta().getLore().get(0)
									.equals(ChatColor.stripColor("Right click me on a leaf block" + "to infect it"))) {
						p.sendMessage("§7The §2Leaf §7has been infected!");
						b.setType(Material.WEB);
						isSilkwormed.add(b.getLocation());

						return;
					}

				} catch (NullPointerException ex) {
					return;
				}
			} else {
				return;
			}
			return;
		}
		return;
	}

	@EventHandler // made 2 event method to make it cleaner
	public void onEat(PlayerInteractEvent e) {
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			try {
				if (e.getPlayer().getItemInHand().equals(Material.STRING) && e.getPlayer().getItemInHand().hasItemMeta()
						&& e.getPlayer().getItemInHand().getItemMeta().getDisplayName()
								.equals(ChatColor.stripColor("Cooked Silkworm"))
						&& e.getPlayer().getItemInHand().getItemMeta().getLore().size() == 1
						&& e.getPlayer().getItemInHand().getItemMeta().getLore().get(0)
								.equals(ChatColor.stripColor("Eat me to restore 2 hunger."))) {
					e.setCancelled(true);
					if (e.getPlayer().getFoodLevel() == 19) {
						e.getPlayer().setFoodLevel(20);
					} else {
						e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 2);
					}
					if (e.getPlayer().getFoodLevel() == 20) {
						e.getPlayer().sendMessage("§cYou are at full hunger already.");
						return;
					}
					e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() - 1);
				}

			} catch (NullPointerException ex) {
				return;
			}
		}

	}
	
	public Runnable getInfestRunnable(){
		return new Runnable() {
			public void run() {

				for (int i = 0; i < SkyBlockRaids.getPlugin().sw.leafwormed().size(); i++) {

					Location l = SkyBlockRaids.getPlugin().sw.leafwormed().get(i);
					Location l1 = l.add(1, 0, 0);
					Location l2 = l.add(-1, 0, 0);
					Location l3 = l.add(0, 0, 1);
					Location l4 = l.add(0, 0, -1); // Getting the blocks
													// adjacent to the cobweb
					Location l5 = l.add(0, 1, 0);
					Location l6 = l1.add(0, -1, 0);
					ArrayList<Location> possibilities = new ArrayList<Location>();
					possibilities.add(l1);
					possibilities.add(l2);
					possibilities.add(l3);
					possibilities.add(l4); // Adding the locations to a list
					possibilities.add(l5);
					possibilities.add(l6);
					Collections.shuffle(possibilities);
					Location final_ = possibilities.get(2);
					if (final_.getBlock().getType().equals(Material.LEAVES)
							|| final_.getBlock().getType().equals(Material.LEAVES_2)) {
						ArrayList<String> third = new ArrayList<String>();
						third.add("1");
						third.add("2");
						third.add("3");
						Collections.shuffle(third);
						if (third.get(2) == "1") { //making a random selection in 1/3 chance if it will actually spread 
							final_.getBlock().setType(Material.WEB);
							SkyBlockRaids.getPlugin().sw.leafwormed().add(final_); // Adding to the arraylist
						}
					} else {
						break;
					}
				}
			}
		};
	}

}
