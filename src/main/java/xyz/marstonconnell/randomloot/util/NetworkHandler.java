package xyz.marstonconnell.randomloot.util;

import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import xyz.marstonconnell.randomloot.RandomLoot;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(RandomLoot.MODID, "main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals);

	public NetworkHandler() {
		INSTANCE.registerMessage(1, RLMessage.class, RLMessage::encode, RLMessage::decode, RLMessage.Handler::handle);
		INSTANCE.registerMessage(1, SpawnItemMessage.class, SpawnItemMessage::encode, SpawnItemMessage::decode, SpawnItemMessage.Handler::handle);

	}

	public static void getNewItem() {
		INSTANCE.sendToServer(new RLMessage());
		
	}

	
	public static void spawnEntity(Entity entity) {
		INSTANCE.sendToServer(new SpawnItemMessage(entity));
		
	}


}
