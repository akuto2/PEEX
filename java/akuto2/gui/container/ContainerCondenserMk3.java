package akuto2.gui.container;

import akuto2.tiles.TileEntityCondenserMk3;
import moze_intel.projecte.gameObjs.container.LongContainer;
import moze_intel.projecte.gameObjs.container.slots.SlotCondenserLock;
import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.container.slots.ValidatedSlot;
import moze_intel.projecte.network.PacketHandler;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class ContainerCondenserMk3 extends LongContainer{
	public TileEntityCondenserMk3 tile;
	public long displayEmc;
	public long requiredEmc;

	public ContainerCondenserMk3(InventoryPlayer invPlayer, TileEntityCondenserMk3 condenser) {
		tile = condenser;
		tile.numPlayersUsing++;
		initSlots(invPlayer);
	}

	protected void initSlots(InventoryPlayer player) {
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
				addSlotToContainer(new Slot(player, j + i * 9, 48 + j * 18, 154 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 48 + i * 18, 212));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		PacketHandler.sendProgressBarUpdateLong(listener, this, 0, tile.displayEmc);
		PacketHandler.sendProgressBarUpdateLong(listener, this, 1, tile.requiredEmc);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		if(displayEmc != tile.displayEmc) {
			for(IContainerListener listener : listeners) {
				PacketHandler.sendProgressBarUpdateLong(listener, this, 0, tile.displayEmc);
			}

			displayEmc = tile.displayEmc;
		}

		if(requiredEmc != tile.requiredEmc) {
			for(IContainerListener listener : listeners) {
				PacketHandler.sendProgressBarUpdateLong(listener, this, 1, tile.requiredEmc);
			}

			requiredEmc = tile.requiredEmc;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		switch(id) {
		case 0:	displayEmc = data;	break;
		case 1:	requiredEmc = data;	break;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBarLong(int id, long data) {
		switch(id) {
		case 0:	displayEmc = data;	break;
		case 1:	requiredEmc = data;	break;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		if(index == 0) {
			return ItemStack.EMPTY;
		}

		Slot slot = getSlot(index);

		if(slot == null || !slot.getHasStack()) {
			return ItemStack.EMPTY;
		}

		ItemStack stack = slot.getStack();

		if(index <= 84) {
			if(!mergeItemStack(stack, 85, 120, false)) {
				return ItemStack.EMPTY;
			}
		}
		else if(!EMCHelper.doesItemHaveEmc(stack) || !mergeItemStack(stack, 1, 42, false)) {
			return ItemStack.EMPTY;
		}

		if(stack.isEmpty()) {
			slot.putStack(ItemStack.EMPTY);
		}
		else {
			slot.onSlotChanged();
		}

		return slot.onTake(playerIn, stack);
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn) {
		super.onContainerClosed(playerIn);
		tile.numPlayersUsing--;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5) <= 64.0;
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if(slotId == 0 && !tile.getLock().getStackInSlot(0).isEmpty()) {
			if(!player.getEntityWorld().isRemote) {
				tile.getLock().setStackInSlot(slotId, ItemStack.EMPTY);
				detectAndSendChanges();
			}

			return ItemStack.EMPTY;
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

		return (int)(Constants.MAX_CONDENSER_PROGRESS * ((double)displayEmc / requiredEmc));
	}
}
