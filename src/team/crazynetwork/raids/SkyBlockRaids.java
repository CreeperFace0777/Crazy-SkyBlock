package team.crazynetwork.raids;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import team.crazynetwork.raids.skyfactory.Crook;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class SkyBlockRaids extends JavaPlugin {
    private static SkyBlockRaids self;
    FileConfiguration islandConfig;
    File iFile;

    public static SkyBlockRaids getPlugin() { //Give other classes direct access to the plugin's methods.
        return self;
    }

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

    public Object getSettings(String settingName) {
        //getConfig conflicts with JavaPlugin.getConfig
        //Returns a value from the config file.

        //To be implemented. A return of a placeholder for now.
        return 1000;

        //Code Suggestion:
        /*

        if(islandConfig.get(settingName) != null) {
		    return islandConfig.get(settingName);
        } return null;

         */

    }

    private void setupFiles() {
        iFile = new File(getDataFolder(), "islands.yml");

        if (!getDataFolder().exists()) { //Check if the plugin folder (where the config and other files are stored) exists.
            try { //If it doesn't then try create it
                getDataFolder().createNewFile();
            } catch (IOException ex) { //If it can't create a file, something is seriously wrong.
                getLogger().severe("Couldn't create plugin folder!");
            }
        }

        if (!iFile.exists()) { //If the islands.yml file doesn't exist.
            try {
                iFile.createNewFile(); //Try create it
            } catch (IOException e) {
                getLogger().severe("Couldn't create islands.yml!"); //Couldn't create the islands.yml files
            }
        }

        islandConfig = YamlConfiguration.loadConfiguration(iFile); //Make the islandConfig equal the islands.yml file.
    }
}
