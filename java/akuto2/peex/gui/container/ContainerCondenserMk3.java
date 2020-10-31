package akuto2.peex.gui.container;

import akuto2.peex.tiles.TileEntityCondenserMk3;
import akuto2.peex.utils.Constants;
import moze_intel.projecte.gameObjs.container.slots.SlotCondenserLock;
import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.container.slots.ValidatedSlot;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class ContainerCondenserMk3 extends Container{
	public TileEntityCondenserMk3 tile;
	public int displayEmc;
	public int requiredEmc;

	public ContainerCondenserMk3(InventoryPlayer invPlayer, TileEntityCondenserMk3 condenser) {
		tile = condenser;
		tile.numPlayersUsing++;

		addSlotToContainer(new SlotCondenserLock(tile.getLock(), 0, 12, 6));

		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				addSlotToContainer(new ValidatedSlot(input, j + i * 6, 12 + j * 18, 26 + i * 18, s -> SlotPredicates.HAS_EMC.test(s) && !tile.isStackEqualToLock(s)));
			}
		}

		for(int i = 0; i < 7; i++) {
			for(int j = 0; j < 6; j++) {
				addSlotToContainer(new ValidatedSlot(output, j + i * 6, 138 + j * 18, 26 + i * 18, s -> false));
			}
		}

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9, 48 + j * 18, 154 + i * 18));
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

		if(slot == null || !slot.getHasStack()) {
			return null;
		}

		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if(index <= 84) {
			if(!mergeItemStack(stack, 85, 120, false)) {
				return null;
			}
		}
		else if(!EMCHelper.doesItemHaveEmc(stack) || !mergeItemStack(stack, 1, 42, false)) {
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
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5) <= 64.0;
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
				tile.getLock().setStackInSlot(slotId, null);
				detectAndSendChanges();
			}

			return null;
		}
		return super.slotClick(slotId, dragType, clickTypeIn, player);
	}

	public int getProgressScaled() {
		if(requiredEmc == 0) {
			return 0;
		}

		if(displayEmc >= requiredEmc) {
			return Constants.MAX_CONDENSER_PROGRESS;
		}

		return (displayEmc * Constants.MAX_CONDENSER_PROGRESS) / requiredEmc;
	}
}
