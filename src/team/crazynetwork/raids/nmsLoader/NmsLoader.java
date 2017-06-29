package team.crazynetwork.raids.nmsLoader;

import org.bukkit.Bukkit;

import team.crazynetwork.raids.SkyBlockRaids;

public class NmsLoader {
	//When in need of nms. Add get methods here. Use placeholder if the thing you are trying to get has methods to prevent NPE.
	private String getVersion(){
		if (!(Bukkit.getServer().getClass().getPackage().getName().replace(".",",").contains(","))){
			SkyBlockRaids.getPlugin().getLogger().severe("Cannot detect server version. Are you using Spigot?");
			return "InvalidVersionDetected";
		}
		return Bukkit.getServer().getClass().getPackage().getName().replace(".",  ",").split(",")[3];
	}
	
	//Example.
	public Object getPacketPlayOutChat(Object chatSerialized,Byte details){
		if (getVersion().equalsIgnoreCase("v1_8_R3")){
			return R1_8_3.getPacketPlayOutChat(chatSerialized,details);
		}
		SkyBlockRaids.getPlugin().getLogger().warning("Unsupported version for PacketPlayOutChat. No placeholder found.");
		return null;
	}
}
