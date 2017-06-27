package team.crazynetwork.raids.skyfactory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import team.crazynetwork.raids.SkyBlockRaids;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class Crook implements Listener {
    public static ShapedRecipe[] recipe() {
        ItemStack crookItem = new ItemStack(Material.STICK, 1);
        ItemMeta crookMeta = crookItem.getItemMeta();
        crookMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Crook " + ChatColor.DARK_GREEN + "64/64"); //Display name as: Crook 64/64
        crookMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "Increases chance of getting a sapling drop", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "64/64", ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + new Date().getTime()));
        crookItem.setItemMeta(crookMeta); //Setting the name and lore
        ShapedRecipe crook1 = new ShapedRecipe(crookItem).shape("ss ", " s ", " s ").setIngredient('s', Material.STICK); //Initialing recipe
        ShapedRecipe crook2 = new ShapedRecipe(crookItem).shape(" ss", " s ", " s ").setIngredient('s', Material.STICK); //Initialing alternate recipe
        ShapedRecipe[] recipes = {crook1, crook2};
        return recipes;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2) {
            //The Block mined is a leaf block
            try {
                if ((e.getPlayer().getItemInHand().getType() == Material.STICK) &&
                		//Is a stick. Check if it is a crook by checking display name.
                		(e.getPlayer().getItemInHand().hasItemMeta()) && 
                		(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains(" ")) &&
                		(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).split(" ")[0].equalsIgnoreCase("Crook")) &&
                		//Display name is crook. Check for lore.
                		(e.getPlayer().getItemInHand().getItemMeta().hasLore()) &&
                		(e.getPlayer().getItemInHand().getItemMeta().getLore().size() >= 2) &&
                		(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getLore().get(0)).equalsIgnoreCase("Increases chance of getting a sapling drop"))
                		){
                			//The item held is most likely a crook
                	
                        	if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getLore().get(1)).equalsIgnoreCase("1/64")) {
                        			//The crook is out of durability now
                        			if (e.getPlayer().getItemInHand().getAmount() == 1) {
                        					e.getPlayer().setItemInHand(null); //If there is only 1 crook in their hand, just remove everything from the hand
                        			} else {
                        					SkyBlockRaids.getPlugin().getLogger().severe("Multiple crooks has been stacked together!"); //This shouldn't happen.
                        					e.getPlayer().setItemInHand(null); //Prevent player from using the crook anymore by removing it.
                        			}
                        			e.getPlayer().sendMessage(ChatColor.GRAY + "Your " + ChatColor.RED + "Crook " + ChatColor.GRAY + "has broken!");
                        			e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_BREAK, 1F, 1F); //Make the tool break sound
                        			return;
                        	} else {
                        		//Crook is not out of durability.
                        		ItemMeta newMeta = e.getPlayer().getItemInHand().getItemMeta();
                        		List<String> lore = newMeta.getLore();
                            	int dur = Integer.parseInt(ChatColor.stripColor(newMeta.getLore().get(1).split("/")[0])) - 1; //New Durability
                            	lore.set(1, ChatColor.RESET + "" + ChatColor.DARK_GRAY + dur + "/64");
                            	newMeta.setLore(lore);
                            	
                            	//Change ChatColor depending on durability.
                            	ChatColor coloredDur = ChatColor.DARK_GREEN; 
                            	if (dur <= 63) coloredDur = ChatColor.GREEN;
                            	if (dur <= 48) coloredDur = ChatColor.YELLOW;
                            	if (dur <= 32) coloredDur = ChatColor.GOLD;
                            	if (dur <= 16) coloredDur = ChatColor.RED;
                            	if (dur <= 3) coloredDur = ChatColor.DARK_RED;
                            	newMeta.setDisplayName(ChatColor.RESET + "Crook " + coloredDur + dur + "/64");
                            	e.getPlayer().getItemInHand().setItemMeta(newMeta);
                              	//Set the lore and the display name of the crook to the durability
                        	}
                        	if (new Random().nextInt(4) == 1) { //just under a 1 in 4 chance of success
                        		e.setCancelled(true);
                        		e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.SAPLING, 1)); //Set the drop as a sapling
                        		e.getBlock().setType(Material.AIR);
                        		//Break the block unnaturally so nothing is dropped apart from the items specified
                        	}
                		}
            } catch (NullPointerException ex) { //Any other non caught error, just ignore
                return;
            }
        }
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        if (e.getInventory().getResult().getAmount() > 1) {
            e.setCancelled(true);
            e.getWhoClicked().sendMessage(ChatColor.RED + "You can only craft one at a time!");
            if (e.getWhoClicked() instanceof Player) {
                ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.VILLAGER_DEATH, 1L, 1L);
            }
        }
    }
}
