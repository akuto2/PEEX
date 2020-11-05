package akuto2.peex.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.NetworkRegistry.TargetPoint;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.common.util.FakePlayer;

public class PEEXPacketHandler {
	private static final int MAX_PKT_SIZE = 256;
	private static final SimpleNetworkWrapper HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel("peex");

	public static void register() {
		HANDLER.registerMessage(PEEXKnowledgeSyncPKT.Handler.class, PEEXKnowledgeSyncPKT.class, 0, Side.CLIENT);
		HANDLER.registerMessage(PEEXSerchUpdatePKT.Handler.class, PEEXSerchUpdatePKT.class, 1, Side.SERVER);
	}

	public static void sendToServer(IMessage message) {
		HANDLER.sendToServer(message);
	}

	public static void sendToAll(IMessage message) {
		HANDLER.sendToAll(message);
	}

	public static void sendToAllAround(IMessage message, TargetPoint point) {
		HANDLER.sendToAllAround(message, point);
	}

	public static void sendTo(IMessage message, EntityPlayerMP playerMP) {
		if(!(playerMP instanceof FakePlayer)) {
			HANDLER.sendTo(message, playerMP);
		}
	}

	public static void sendToDimension(IMessage message, int dimension) {
		HANDLER.sendToDimension(message, dimension);
	}
}
