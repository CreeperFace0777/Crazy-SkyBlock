package team.crazynetwork.raids.skyfactory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SilkWorm implements Listener {
	List<Location> isSilkwormed = new ArrayList<Location>(); 
	
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
			if(b.getType().equals(Material.LEAVES) || b.getType().equals(Material.LEAVES_2)){
				try {
					if(e.getPlayer().getItemInHand().equals(Material.STRING) && e.getPlayer().getItemInHand().hasItemMeta() &&
							e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.stripColor("Silkworm")) && 
								e.getPlayer().getItemInHand().getItemMeta().getLore().size() == 2 && 
									e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.stripColor("Right click me on a leaf block"
											+ "to infect it"))){
						//TODO
						return;
					}
						
				}catch(NullPointerException ex){
					return;
				}
			} else {
				return;
			}
			return;
		}
		return;
	}
	@EventHandler //made 2 event method to make it cleaner
	public void onEat(PlayerInteractEvent e){
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
			try {
				if(e.getPlayer().getItemInHand().equals(Material.STRING) && e.getPlayer().getItemInHand().hasItemMeta() &&
						e.getPlayer().getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.stripColor("Cooked Silkworm")) && 
							e.getPlayer().getItemInHand().getItemMeta().getLore().size() == 1 && 
								e.getPlayer().getItemInHand().getItemMeta().getLore().get(0).equals(ChatColor.stripColor("Eat me to restore 2 hunger."))){
					e.setCancelled(true);
					if(e.getPlayer().getFoodLevel() == 19){
						e.getPlayer().setFoodLevel(20);
					}
					else{
						e.getPlayer().setFoodLevel(e.getPlayer().getFoodLevel() + 2);
					}
					e.getPlayer().getItemInHand().setAmount(e.getPlayer().getItemInHand().getAmount() -1);
					if(e.getPlayer().getFoodLevel() == 20){
						e.getPlayer().sendMessage("§2You are at full hunger already.");
					}
					return;
				}
					
			}catch(NullPointerException ex){
				return;
			}
		}
				
	}
	
	
	
}
