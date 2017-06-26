package team.crazynetwork.raids.skyfactory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class SilkWorm implements Listener {
    public static FurnaceRecipe recipe() {
    	ItemStack silkworm = new ItemStack(Material.STRING, 1);
    	ItemMeta silkmeta = silkworm.getItemMeta();
    	silkmeta.setDisplayName("§r§cSilkWorm");
    	//TO DO
    	
    	
    	return null;
    }
    

    @EventHandler
    public void onTreeInfect(PlayerInteractEvent e) {
        //TODO: Handle
    }
}
