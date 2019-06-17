package xyz.marstonconnell.randomloot.util;

import java.util.function.Supplier;

import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class SpawnItemMessage {
	static Entity toSpawn;
	public SpawnItemMessage(Entity entity) {
		this.toSpawn = entity;
	}

	public static void encode(SpawnItemMessage pkt, PacketBuffer buf) {
		
	}

	public static SpawnItemMessage decode(PacketBuffer buf) {

		return new SpawnItemMessage(toSpawn);
	}

	public static class Handler {
		public static void handle(final SpawnItemMessage pkt, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {
				ctx.get().getSender().getEntityWorld().spawnEntity(toSpawn);
				ctx.get().getSender().getServerWorld().spawnEntity(toSpawn);

			});
		}
	}
}
