package team.crazynetwork.raids.skyfactory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Hammer implements Listener {

    public static ShapedRecipe[][] recipe() {

        String hammerName = ChatColor.RESET + "" + ChatColor.WHITE + "%s Hammer " + ChatColor.DARK_GREEN + "%s/%s";
        //Wooden Hammer Recipe
        ItemStack hammerItem1 = new ItemStack(Material.WOOD_PICKAXE, 1);
        ItemMeta hammerMeta1 = hammerItem1.getItemMeta();
        hammerMeta1.setDisplayName(String.format(hammerName, "Wooden", 64, 64)); //Display name as: Wooden Hammer 64/64
        hammerMeta1.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "Turns cobble into sand, sand into gravel", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "128/128", ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + new Date().getTime()));
        hammerItem1.setItemMeta(hammerMeta1);

        //Stone Hammer Recipe
        ItemStack hammerItem2 = new ItemStack(Material.STONE_PICKAXE, 1);
        ItemMeta hammerMeta2 = hammerItem2.getItemMeta();
        hammerMeta2.setDisplayName(String.format(hammerName, "Stone", 80, 80)); //Display name as: Stone Hammer 80/80
        hammerMeta2.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "Turns cobble into sand, sand into gravel", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "80/80", ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + new Date().getTime()));
        hammerItem2.setItemMeta(hammerMeta2);

        //Iron Hammer Recipe
        ItemStack hammerItem3 = new ItemStack(Material.IRON_PICKAXE, 1);
        ItemMeta hammerMeta3 = hammerItem3.getItemMeta();
        hammerMeta3.setDisplayName(String.format(hammerName, "Iron", 96, 96)); //Display name as: Iron Hammer 96/96
        hammerMeta3.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "Turns cobble into sand, sand into gravel", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "96/96", ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + new Date().getTime()));
        hammerItem3.setItemMeta(hammerMeta3);

        //Golden Hammer Recipe
        ItemStack hammerItem4 = new ItemStack(Material.GOLD_PICKAXE, 1);
        ItemMeta hammerMeta4 = hammerItem4.getItemMeta();
        hammerMeta4.setDisplayName(String.format(hammerName, "Golden", 64, 64)); //Display name as: Golden Hammer 64/64
        hammerMeta4.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "Turns cobble into sand, sand into gravel", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "64/64", ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + new Date().getTime()));
        hammerItem4.setItemMeta(hammerMeta4);

        //Diamond Hammer Recipe
        ItemStack hammerItem5 = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta hammerMeta5 = hammerItem5.getItemMeta();
        hammerMeta5.setDisplayName(String.format(hammerName, "Diamond", 128, 128)); //Display name as: Diamond Hammer 128/128
        hammerMeta5.setLore(Arrays.asList(ChatColor.RESET + "" + ChatColor.GRAY + "" + ChatColor.ITALIC + "Turns cobble into sand, sand into gravel", ChatColor.RESET + "" + ChatColor.DARK_GRAY + "128/128", ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + new Date().getTime()));
        hammerItem5.setItemMeta(hammerMeta5);

        ShapedRecipe woodHammers[] = {new ShapedRecipe(hammerItem1).shape(" w ", " sw", "s  ").setIngredient('s', Material.STICK).setIngredient('w', Material.WOOD), new ShapedRecipe(hammerItem1).shape(" w ", "ws ", "  s").setIngredient('s', Material.STICK).setIngredient('w', Material.WOOD)};
        ShapedRecipe cobbleHammers[] = {new ShapedRecipe(hammerItem2).shape(" c ", " sc", "s  ").setIngredient('s', Material.STICK).setIngredient('c', Material.COBBLESTONE), new ShapedRecipe(hammerItem2).shape(" c ", "cs ", "  s").setIngredient('s', Material.STICK).setIngredient('c', Material.COBBLESTONE)};
        ShapedRecipe ironHammers[] = {new ShapedRecipe(hammerItem3).shape(" i ", " si", "s  ").setIngredient('s', Material.STICK).setIngredient('i', Material.IRON_INGOT), new ShapedRecipe(hammerItem3).shape(" i ", "is ", "  s").setIngredient('s', Material.STICK).setIngredient('i', Material.IRON_INGOT)};
        ShapedRecipe goldHammers[] = {new ShapedRecipe(hammerItem4).shape(" g ", " sg", "s  ").setIngredient('s', Material.STICK).setIngredient('g', Material.GOLD_INGOT), new ShapedRecipe(hammerItem4).shape(" g ", "gs ", "  s").setIngredient('s', Material.STICK).setIngredient('g', Material.GOLD_INGOT)};
        ShapedRecipe diamondHammers[] = {new ShapedRecipe(hammerItem5).shape(" d ", " sd", "s  ").setIngredient('s', Material.STICK).setIngredient('d', Material.DIAMOND), new ShapedRecipe(hammerItem5).shape(" d ", "ds ", "  s").setIngredient('s', Material.STICK).setIngredient('d', Material.DIAMOND)};


        ShapedRecipe[][] hammers = {woodHammers, cobbleHammers, ironHammers, goldHammers, diamondHammers};
        return hammers;
    } ///FIXME: Code suggestions to condense this whole method!

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        try {
            //If the block can be mined with a hammer, then test if it a hammer
            if (e.getPlayer().getItemInHand().getType() == Material.WOOD_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.STONE_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.IRON_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.GOLD_PICKAXE || e.getPlayer().getItemInHand().getType() == Material.DIAMOND_PICKAXE)
                if (e.getPlayer().getItemInHand().hasItemMeta())
                    if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).contains("Hammer "))
                        if (e.getPlayer().getItemInHand().getItemMeta().hasLore())
                            if (e.getPlayer().getItemInHand().getItemMeta().getLore().size() >= 2)
                                if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getLore().get(0)).equalsIgnoreCase("Turns cobble into sand, sand into gravel")) {
                                    //Likely some kind of hammer
                                    if (e.getBlock().getType() == Material.COBBLESTONE || e.getBlock().getType() == Material.SAND) {
                                        e.setCancelled(true);
                                        e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), new ItemStack(e.getBlock().getType() == Material.COBBLESTONE ? Material.SAND : Material.GRAVEL, 1)); //Set the drop as either sand or gravel depending on what the item is.
                                        e.getBlock().setType(Material.AIR);
                                    }
                                    if (ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getLore().get(1)).split("/")[0].equalsIgnoreCase("0")) {
                                        //The item is out of durability. Because hammers don't stack, there will only ever be one hammer in the players hand
                                        e.getPlayer().setItemInHand(null);
                                        e.getPlayer().sendMessage(ChatColor.GRAY + "Your " + ChatColor.RED + "Hammer " + ChatColor.GRAY + "has broken!");
                                        e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ITEM_BREAK, 1F, 1F); //Make the tool break sound
                                        return;
                                    } else {
                                        //Lower the durability by one.
                                        //TODO
                                    }
                                }
        } catch (Exception ex) {
            return; //It shouldn't do anything anyway. Here so no errors are outputted
        }

    }

    @EventHandler
    public void onBlockDamage(BlockDamageEvent e) {
        //TODO: Make the player mine the block faster if its sand or gravel!
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        if (e.getInventory().getResult().getAmount() > 1) {
            for (ShapedRecipe[] sA : recipe())
                for (ShapedRecipe s : sA)
                    if (s.getResult() == e.getInventory().getResult()) {
                        e.setCancelled(true);
                        ItemStack i = e.getInventory().getResult();
                        ItemMeta m = i.getItemMeta();
                        List<String> l = m.getLore();
                        l.add(ChatColor.RESET + "" + ChatColor.LIGHT_PURPLE + "" + ChatColor.MAGIC + new Date().getTime());
                        m.setLore(l);
                        i.setItemMeta(m);
                        e.getInventory().setResult(i);
                        e.getWhoClicked().sendMessage(ChatColor.RED + "You can only craft one at a time!");
                        if (e.getWhoClicked() instanceof Player) {
                            ((Player) e.getWhoClicked()).playSound(e.getWhoClicked().getLocation(), Sound.VILLAGER_DEATH, 1L, 1L);
                        }
                    }
        }
    }
}
