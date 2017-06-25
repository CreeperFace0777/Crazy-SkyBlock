package team.crazynetwork.raids.skyfactory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Crook implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getBlock().getType() == Material.LEAVES || e.getBlock().getType() == Material.LEAVES_2) {
            //The Block mined is a leaf block
            try {
                if (e.getPlayer().getItemInHand() != null)
                    if (e.getPlayer().getItemInHand().hasItemMeta())
                        if (e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains(" "))
                            if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).split(" ")[0].equalsIgnoreCase("Crook"))
                                if (e.getPlayer().getItemInHand().getItemMeta().hasLore())
                                    if (e.getPlayer().getItemInHand().getItemMeta().getLore().size() >= 2)
                                        if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getLore().get(0)).equalsIgnoreCase("Increases chance of getting a sapling drop"))
                                            //The item is most held likely a crook
                                            if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getLore().get(1)).equalsIgnoreCase("1/64")) {
                                                //The crook is out of durability now
                                                e.setCancelled(true);
                                                e.getPlayer().getItemInHand().setType(Material.AIR);
                                                e.getPlayer().sendMessage(ChatColor.GRAY + "Your " + ChatColor.RED + "Crook " + ChatColor.GRAY + " has broken!");
                                                return;
                                            } else {
                                                ItemMeta newMeta = e.getPlayer().getItemInHand().getItemMeta();
                                                List<String> lore = newMeta.getLore();
                                                int dur = Integer.parseInt(ChatColor.stripColor(newMeta.getLore().get(1).split("/")[0])) - 1; //New Durability
                                                lore.set(1, ChatColor.RESET + "" + ChatColor.DARK_GRAY + dur + "/64");
                                                newMeta.setLore(lore);
                                                ChatColor coloredDur = ChatColor.DARK_GREEN;
                                                if (dur < 63) coloredDur = ChatColor.GREEN;
                                                if (dur < 48) coloredDur = ChatColor.YELLOW;
                                                if (dur < 32) coloredDur = ChatColor.GOLD;
                                                if (dur < 16) coloredDur = ChatColor.RED;
                                                if (dur < 3) coloredDur = ChatColor.DARK_RED;
                                                newMeta.setDisplayName(ChatColor.RESET + "Crook " + coloredDur + dur + "/64");
                                                e.getPlayer().getItemInHand().setItemMeta(newMeta);
                                                //Set the lore and the display name of the crook to the durability
                                            }
                if (new Random().nextInt(4) == 1) { //1 in 4 chance of success
                    e.setCancelled(true);
                    e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(Material.SAPLING, new Random().nextInt(2) + 1)); //Set the drop as a sapling
                    e.getBlock().setType(Material.AIR);

                    //Break the block unnaturally so nothing is dropped apart from the items specified
                }
            } catch (NullPointerException ex) { //Any other non caught error, just ignore
                return;
            }
        }
    }

    public static ShapedRecipe[] recipe() {
        ItemStack crookItem = new ItemStack(Material.STICK, 1);
        ItemMeta crookMeta = crookItem.getItemMeta();
        crookMeta.setDisplayName(ChatColor.RESET + "" + ChatColor.WHITE + "Crook " + ChatColor.DARK_GREEN + "64/64"); //Display name as: Crook 64/64
        crookMeta.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.ITALIC + "Increases chance of getting a sapling drop", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "64/64", ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + "OBFUSCATED"));
        crookItem.setItemMeta(crookMeta); //Setting the name and lore
        ShapedRecipe crook1 = new ShapedRecipe(crookItem); //Initialing recipe
        crook1.shape("//#", "#/#", "#/#"); //Setting the shape for the recipe
        crook1.setIngredient('/', Material.STICK);
        crook1.setIngredient('#', Material.AIR);
        Bukkit.getServer().addRecipe(crook1);
        ShapedRecipe crook2 = new ShapedRecipe(crookItem); //Initialing alternate recipe
        crook2.shape("#//", "#/#", "#/#"); //Setting the alternate shape
        crook2.setIngredient('/', Material.STICK);
        crook2.setIngredient('#', Material.AIR);
        Bukkit.getServer().addRecipe(crook2);
        ShapedRecipe[] recipes = {crook1, crook2};
        return recipes;
    }
}
