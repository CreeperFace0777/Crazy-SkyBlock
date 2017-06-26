package team.crazynetwork.raids.nmsLoader;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class R1_8_3 {

	public static Object getPacketPlayOutChat(Object chatSerialized,Byte details) {
		return new PacketPlayOutChat((IChatBaseComponent) chatSerialized,details);
	}
	
}
