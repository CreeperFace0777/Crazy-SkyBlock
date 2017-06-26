package team.crazynetwork.raids;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Island {
    public int x, y, z; //The coordinates of the island
    public Long raidableTime; //When it will be raidable
    private Player owner; //The owner
    public List<Player> members; //Members of the island
    public double balance; //Island Balance

    public Island(int x, int y, int z, Player owner) { //Constructor. Initializes everything from given vars. This is for NEW islands
        x = this.x;
        y = this.y;
        z = this.z;
        raidableTime = new Date().getTime() + (Long) SkyBlockRaids.getPlugin().getSettings("raidDelay","islands"); //Gets current time and adds the delay to raid to it.
        this.balance = 0;
        this.members = new ArrayList<>();

        SkyBlockRaids.islands.add(this);
        SkyBlockRaids.playerIsland.put(owner.getUniqueId().toString(), this);
    }

    public Island(String ownerName) {
    	FileConfiguration config = (FileConfiguration) SkyBlockRaids.getPlugin().getSettings(null,"islands");
        ConfigurationSection pIsland = config.getConfigurationSection(ownerName);
        try {
            this.x = pIsland.getInt("x");
            this.y = pIsland.getInt("y");
            this.z = pIsland.getInt("z");
            this.raidableTime = pIsland.getLong("raidableTime");
            for (String member : pIsland.getStringList("members"))
                this.members.add(Bukkit.getPlayer(UUID.fromString(member)));
            this.balance = pIsland.getDouble("balance");
        } catch (NullPointerException e) {
            Bukkit.getLogger().severe("Config for " + owner.getName() + " is set out incorrectly");
        }
        SkyBlockRaids.islands.add(this);
        SkyBlockRaids.playerIsland.put(owner.getUniqueId().toString(), this);
        raidableTime = new Date().getTime() + (Long) SkyBlockRaids.getPlugin().getSettings("raidDelay","islands"); //Gets current time and adds the delay to raid to it.
    
    }

    public Player getOwner() {
        return owner; //Returns owner. Duh.
    }

    public List<Player> getMembers() {
        return members; //Get the members of the island
    }

    public void setMembers(List<Player> members) {
        this.members = members; //Set the members of the island. Main Use: /is add/remove command
    }

    public boolean isRaidable() {
        Long time = new Date().getTime(); //Get current time
        if (time >= raidableTime) { //Check if it is OK to raid.
            return true; //Returns true if it is.
        } else {
            return false; //Return false if it isn't.
        }
    }

}
