package team.crazynetwork.raids;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Island {
    public static List<Island> islands = new ArrayList<>();
    private int x, y, z; //The coordinates of the island
    private Long raidableTime; //When it will be raidable
    private Player owner; //The owner
    private List<Player> members; //Members of the island

    public Island(int x, int y, int z, Player owner) { //Constructor. Initializes everything from given vars. This is for NEW islands
        x = this.x;
        y = this.y;
        z = this.z;
        raidableTime = new Date().getTime() + (Long) SkyBlockRaids.getPlugin().getSettings("raidDelay"); //Gets current time and adds the delay to raid to it.
    }

    public Island(Player owner) {
        ConfigurationSection pIsland = SkyBlockRaids.getPlugin().islandConfig.getConfigurationSection(owner.getUniqueId().toString());
        this.x = pIsland.getInt("x");
        this.y = pIsland.getInt("y");
        this.z = pIsland.getInt("z");
        this.raidableTime = pIsland.getLong("raidableTime");
        for (String member : pIsland.getStringList("members"))
            this.members.add(Bukkit.getPlayer(UUID.fromString(member)));


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
