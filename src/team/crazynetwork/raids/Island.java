package team.crazynetwork.raids;

import java.util.Date;

import org.bukkit.entity.Player;

public class Island {
    private int x,y,z; //The coordinates of the island
    private Long raidableTime; //When it will be raidable
    private Player owner; //The owner
    
    public Island(int x,int y,int z,Player owner){ //Constructor. Initializes everything from given vars.
    	x=this.x;
    	y=this.y;
    	z=this.z;
    	raidableTime=new Date().getTime() + (Long)SkyBlockRaids.getPlugin().getSettings("raidDelay"); //Gets current time and adds the delay to raid to it.
    }
    
    public Player getOwner(){
    	return owner; //Returns owner. Duh.
    }
    
    public boolean isRaidable(){
    	Long time = new Date().getTime(); //Get current time
    	if (time >= raidableTime){ //Check if it is OK to raid.
    		return true; //Returns true if it is.
    	} else {
    		return false; //Return false if it isn't.
    	}
    }
}
