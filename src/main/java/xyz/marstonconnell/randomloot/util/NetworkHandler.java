package xyz.marstonconnell.randomloot.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
		    new ResourceLocation("mymodid", "main"),
		    () -> PROTOCOL_VERSION,
		    PROTOCOL_VERSION::equals,
		    PROTOCOL_VERSION::equals
		);
	
	public NetworkHandler() {
		// TODO Auto-generated constructor stub
	}
	
	

}
