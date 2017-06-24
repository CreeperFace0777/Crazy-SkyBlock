package team.crazynetwork.raids;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import team.crazynetwork.raids.skyfactory.Crook;

import java.util.Arrays;

public class SkyBlockRaids extends JavaPlugin {
    private static SkyBlockRaids self;

    @Override
    public void onEnable() {

        self = this; //A workaround as static vars normally do not allow this.

        Bukkit.getServer().getPluginManager().registerEvents(new Crook(), this); //Adds the Crook Listener

        //CROOK RECIPE
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

    }


    public static SkyBlockRaids getPlugin(){ //Give other classes direct access to the plugin's methods.
        return self;
    }

    public Object getSettings(String settingName){
        //getConfig conflicts with JavaPlugin.getConfig
        //Returns a value from the config file.

        //To be implemented. A return of a placeholder for now.
        return 1000;
    }
}
