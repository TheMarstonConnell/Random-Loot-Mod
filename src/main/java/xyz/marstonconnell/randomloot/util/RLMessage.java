package xyz.marstonconnell.randomloot.util;

import java.nio.ByteBuffer;
import java.util.function.Supplier;

import org.lwjgl.system.windows.MSG;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import xyz.marstonconnell.randomloot.items.CaseItem;

public class RLMessage {

	public RLMessage() {
	}

	public static void encode(RLMessage pkt, PacketBuffer buf) {
		
	}

	public static RLMessage decode(PacketBuffer buf) {

		return new RLMessage();
	}

	public static class Handler {
		public static void handle(final RLMessage pkt, Supplier<NetworkEvent.Context> ctx) {
			ctx.get().enqueueWork(() -> {

				EntityPlayerMP serverPlayer = ctx.get().getSender();
				// Execute the action on the main server thread by adding it as a
				// scheduled task

				Item i = serverPlayer.inventory.getCurrentItem().getItem();
				serverPlayer.inventory.getCurrentItem().shrink(1);

				serverPlayer.inventory.addItemStackToInventory(((CaseItem)i).getItem(serverPlayer.getServerWorld(),
						((CaseItem)i).level));

			});
		}
	}

}
