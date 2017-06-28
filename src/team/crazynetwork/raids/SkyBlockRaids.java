package team.crazynetwork.raids;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import team.crazynetwork.raids.skyfactory.Crook;
import team.crazynetwork.raids.skyfactory.SilkWorm;

public class SkyBlockRaids extends JavaPlugin {
	private static SkyBlockRaids self;
	SilkWorm sw;
	FileConfiguration islandConfig;
	File iFile;

	public static List<Island> islands = new ArrayList<>();
	public static HashMap<Player, Island> playerIsland = new HashMap<>();

	public static SkyBlockRaids getPlugin() { // Give other classes direct
												// access to the plugin's
												// methods.
		return self;
	}

	@Override
	public void onEnable() {

		self = this; // A workaround as static vars normally do not allow this.

		// Onenable for spreading wilk worms, every 30 seconds, after 3 minutes
		// the server starts up to make sure everything is gud
		getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {

				for (int i = 0; i < sw.leafwormed().size(); i++) {

					Location l = sw.leafwormed().get(i);
					Location l1 = sw.leafwormed().get(i).add(1, 0, 0);
					Location l2 = sw.leafwormed().get(i).add(-1, 0, 0);
					Location l3 = sw.leafwormed().get(i).add(0, 0, 1);
					Location l4 = sw.leafwormed().get(i).add(0, 0, -1); //Getting the blocks adjacent to the cobweb
					Location l5 = sw.leafwormed().get(i).add(0, 1, 0);
					Location l6 = sw.leafwormed().get(i).add(0, -1, 0);
					ArrayList<Location> possibilities = new ArrayList<Location>();
					possibilities.add(l1);
					possibilities.add(l2);
					possibilities.add(l3);
					possibilities.add(l4); //Adding the locations to a list
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
						if(third.get(2) == "1"){
							final_.getBlock().setType(Material.WEB);
							sw.leafwormed().add(final_);
						}
					} else {
						break;
					}
				}
			}
		}, 20L * 60L * 3L, 20L * 30L);

		Bukkit.getServer().getPluginManager().registerEvents(new Crook(), this); // Adds
																					// the
																					// Crook
																					// Listener
		getServer().addRecipe(Crook.recipe()[0]); // Both crook recipes
		getServer().addRecipe(Crook.recipe()[1]);
		getServer().addRecipe(SilkWorm.recipe());
		// TODO: Handle loading islands from islands.yml
	}

	public Object getSettings(String settingName) {
		if (islandConfig.get(settingName) != null) {
			return islandConfig.get(settingName);
		}
		return null;

	}

	@Override
	public void onDisable() {
		// TODO: Handle saving islands
	}

	@SuppressWarnings("unused")
	private void setupFiles() {
		iFile = new File(getDataFolder(), "islands.yml");

		if (!getDataFolder().exists()) { // Check if the plugin folder (where
											// the config and other files are
											// stored) exists.
			try { // If it doesn't then try create it
				getDataFolder().createNewFile();
			} catch (IOException ex) { // If it can't create a file, something
										// is seriously wrong.
				getLogger().severe("Couldn't create plugin folder!");
			}
		}

		if (!iFile.exists()) { // If the islands.yml file doesn't exist.
			try {
				iFile.createNewFile(); // Try create it
			} catch (IOException e) {
				getLogger().severe("Couldn't create islands.yml!"); // Couldn't
																	// create
																	// the
																	// islands.yml
																	// files
			}
		}

		islandConfig = YamlConfiguration.loadConfiguration(iFile); // Make the
																	// islandConfig
																	// equal the
																	// islands.yml
																	// file.
	}
}
