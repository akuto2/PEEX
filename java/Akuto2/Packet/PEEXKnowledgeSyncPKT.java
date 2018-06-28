package Akuto2.Packet;

import Akuto2.PEEXCore;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

public class PEEXKnowledgeSyncPKT implements IMessage{
	private NBTTagCompound nbtTagCompound;

	public PEEXKnowledgeSyncPKT()	{}

	public PEEXKnowledgeSyncPKT(NBTTagCompound nbt) {
		nbtTagCompound = nbt;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		nbtTagCompound = ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, nbtTagCompound);
	}

	public static class Handler implements IMessageHandler<PEEXKnowledgeSyncPKT, IMessage>{
		@Override
		public IMessage onMessage(PEEXKnowledgeSyncPKT message, MessageContext ctx) {
			PEEXCore.proxy.getClientTransmutationProps().readFromPacket(message.nbtTagCompound);

			return null;
		}
	}
}
