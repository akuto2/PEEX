package akuto2.peex.gui.container;

import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.container.slots.ValidatedSlot;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk4;
import moze_intel.projecte.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class ContainerRelayMk4 extends Container{
	final TileEntityRelayMk4 tile;
	public double kleinChargeProgress = 0;
	public double inputBurnProgress = 0;
	public int emc = 0;

	public ContainerRelayMk4(InventoryPlayer invPlayer, TileEntityRelayMk4 tile) {
		this.tile = tile;
		initSlots(invPlayer);
	}

	protected void initSlots(InventoryPlayer invPlayer) {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		addSlotToContainer(new ValidatedSlot(input, 0, 104, 58, SlotPredicates.RELAY_INV));

		int counter = input.getSlots() - 1;
		for(int i = 0; i <= 3; i++) {
			for(int j = 0; j <= 4; j++) {
				addSlotToContainer(new ValidatedSlot(input, counter--, 28 + i * 18, 18 + j * 18, SlotPredicates.RELAY_INV));
			}
		}

		addSlotToContainer(new ValidatedSlot(output, 0, 164, 58, SlotPredicates.IITEMEMC));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 26 + j * 18, 113 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(invPlayer, i, 26 + i * 18, 171));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		PacketHandler.sendProgressBarUpdateInt(listener, this, 0, (int)tile.getStoredEmc());
		PacketHandler.sendProgressBarUpdateInt(listener, this, 1, (int)(tile.getItemChargeProportion() * 8000));
		PacketHandler.sendProgressBarUpdateInt(listener, this, 2, (int)(tile.getInputBurnProportion() * 8000));
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		if(emc != ((int)tile.getStoredEmc())) {
			for(IContainerListener iListener : listeners) {
				PacketHandler.sendProgressBarUpdateInt(iListener, this, 0, (int)tile.getStoredEmc());
			}

			emc = (int)tile.getStoredEmc();
		}

		if(kleinChargeProgress != tile.getItemChargeProportion()) {
			for(IContainerListener iListener : listeners) {
				PacketHandler.sendProgressBarUpdateInt(iListener, this, 1, ((int)tile.getItemChargeProportion() * 8000));
			}

			kleinChargeProgress = tile.getItemChargeProportion();
		}

		if(inputBurnProgress != tile.getInputBurnProportion()) {
			for(IContainerListener iListener : listeners) {
				PacketHandler.sendProgressBarUpdateInt(iListener, this, 2, ((int)tile.getInputBurnProportion() * 8000));
			}

			inputBurnProgress = tile.getInputBurnProportion();
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		switch(id) {
		case 0: emc = data; break;
		case 1: kleinChargeProgress = data / 8000.0; break;
		case 2: inputBurnProgress = data / 8000.0; break;
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		Slot slot = getSlot(index);

		if(slot == null || !slot.getHasStack()) {
			return null;
		}

		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();

		if(index < 22) {
			if(!mergeItemStack(stack, 22, inventorySlots.size(), true)) {
				return null;
			}
			slot.onSlotChanged();
		}
		else if(!mergeItemStack(stack, 0, 21, false)) {
			return null;
		}
		if(stack.stackSize == 0) {
			slot.putStack(null);
		}
		else {
			slot.onSlotChanged();
		}

		slot.onPickupFromSlot(playerIn, newStack);
		return newStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5) <= 64.0;
	}
}
