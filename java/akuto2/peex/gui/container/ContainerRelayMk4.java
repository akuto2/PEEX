package akuto2.peex.gui.container;

import akuto2.peex.tile.TileEntityRelayMk4;
import moze_intel.projecte.gameObjs.container.slots.relay.SlotRelayInput;
import moze_intel.projecte.gameObjs.container.slots.relay.SlotRelayKlein;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerRelayMk4 extends Container{
	private TileEntityRelayMk4 tile;

	public ContainerRelayMk4(InventoryPlayer invPlayer, TileEntityRelayMk4 relay){
		this.tile = relay;
		this.tile.func_70295_k_();

		addSlotToContainer(new SlotRelayInput(this.tile, 0, 104, 58));
		int i;
		int j;
		for(i = 0; i <= 3; ++i){
			for(j = 0; j <= 4; ++j){
				addSlotToContainer(new SlotRelayKlein(this.tile, i * 5 + j + 1, 28 + i * 18, 18 + j * 18));
			}
		}

		addSlotToContainer(new SlotRelayKlein(this.tile, 21, 164, 58));

		for(i = 0; i < 3; ++i){
			for(j = 0; j < 9; ++j){
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 26 + j * 18, 113 + i * 18));
			}
		}
		for(i = 0; i < 9; ++i){
			addSlotToContainer(new Slot(invPlayer, i, 26 + i * 18, 171));
		}
	}


	@Override
	public void onContainerClosed(EntityPlayer player){
		super.onContainerClosed(player);
		this.tile.func_70305_f();
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex){
		Slot slot = this.getSlot(slotIndex);

		if (slot == null || !slot.getHasStack())
		{
			return null;
		}

		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if (slotIndex < 22)
		{
			if (!this.mergeItemStack(stack, 22, this.inventorySlots.size(), true))
				return null;
			slot.onSlotChanged();
		}
		else if (!this.mergeItemStack(stack, 0, 21, false))
		{
			return null;
		}
		if (stack.stackSize == 0)
		{
			slot.putStack(null);
		}
		else
		{
			slot.onSlotChanged();
		}

		slot.onPickupFromSlot(player, newStack);
		return newStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return player.getDistanceSq(tile.xCoord + 0.5, tile.yCoord + 0.5, tile.zCoord + 0.5) <= 64.0;
	}
}
