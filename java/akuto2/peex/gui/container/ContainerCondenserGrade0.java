package akuto2.peex.gui.container;

import akuto2.peex.gui.container.slots.SlotCondenserGrade0Lock;
import akuto2.peex.tiles.TileEntityCondenserGrade0;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCondenserGrade0 extends Container{
	public TileEntityCondenserGrade0 tile;

	public ContainerCondenserGrade0(InventoryPlayer invPlayer, TileEntityCondenserGrade0 condenser) {
		tile = condenser;
		tile.numPlayersUsing++;
		initSlots(invPlayer);
	}

	private void initSlots(InventoryPlayer invPlayer) {
		addSlotToContainer(new SlotCondenserGrade0Lock(tile.getLock(), 0, 12, 6));

		IItemHandler handler = tile.getInput();

		int counter = 0;
		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 13; j++) {
				addSlotToContainer(new SlotItemHandler(handler, counter++, 12 + j * 18, 26 + i * 18));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 48 + j * 18, 154 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 48 + i * 18, 212));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		if(index == 0) {
			return null;
		}
		Slot slot = getSlot(index);
		if((slot == null) || (!slot.getHasStack())) {
			return null;
		}
		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if(index <= 91) {
			if(!mergeItemStack(stack, 92, 127, false)) {
				return null;
			}
		}
		else if(!mergeItemStack(stack, 1, 91, false)) {
			return null;
		}

		if(stack.stackSize == 0) {
			slot.putStack(null);
		}
		else {
			slot.onSlotChanged();
		}

		slot.onPickupFromSlot(playerIn, stack);
		return newStack;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		tile.numPlayersUsing--;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if(slotId == 0 && tile.getLock().getStackInSlot(0) != null) {
			if(!player.getEntityWorld().isRemote) {
				tile.getLock().setStackInSlot(0, null);
				detectAndSendChanges();
			}
			return null;
		}
		else {
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX() + 0.5F, tile.getPos().getY() + 0.5F, tile.getPos().getZ() + 0.5F) <= 64.0;
	}
}
