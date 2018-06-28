package Akuto2.Packet;

import Akuto2.Container.ContainerTransmutationMk2;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.ItemStack;

public class PEEXSerchUpdatePKT implements IMessage{
	public int slot;
	public ItemStack stack;

	public PEEXSerchUpdatePKT() {}

	public PEEXSerchUpdatePKT(int slot, ItemStack stack) {
		this.slot = slot;
		this.stack = (stack != null ? stack.copy() : null);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		slot = buf.readInt();
		stack = ByteBufUtils.readItemStack(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(slot);
		ByteBufUtils.writeItemStack(buf, stack);
	}

	public static class Handler implements IMessageHandler<PEEXSerchUpdatePKT, IMessage>{
		@Override
		public IMessage onMessage(PEEXSerchUpdatePKT message, MessageContext ctx) {
			if((ctx.getServerHandler().playerEntity.openContainer instanceof ContainerTransmutationMk2)) {
				ContainerTransmutationMk2 container = (ContainerTransmutationMk2)ctx.getServerHandler().playerEntity.openContainer;
				container.transmutationMk2Inventory.writeIntoOutputSlot(message.slot, message.stack);
			}
			return null;
		}
	}
}
