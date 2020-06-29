package akuto2.gui.container;

import akuto2.tiles.TileEntityCollectorMk4;
import moze_intel.projecte.emc.FuelMapper;
import moze_intel.projecte.gameObjs.container.slots.SlotGhost;
import moze_intel.projecte.gameObjs.container.slots.SlotPredicates;
import moze_intel.projecte.gameObjs.container.slots.ValidatedSlot;
import moze_intel.projecte.network.PacketHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;

public class ContainerCollectorMk4 extends Container {
	protected final TileEntityCollectorMk4 tile;
	public int sunLevel = 0;
	public int emc = 0;
	public double kleinChargeProgress = 0;
	public double fuelProgress = 0;
	public int kleinEmc = 0;

	public ContainerCollectorMk4(InventoryPlayer player, TileEntityCollectorMk4 collector) {
		tile = collector;
		initSlots(player);
	}

	protected void initSlots(InventoryPlayer player) {
		IItemHandler aux = tile.getAux();
		IItemHandler main = tile.getInput();

		addSlotToContainer(new ValidatedSlot(aux, TileEntityCollectorMk4.UPGRADE_SLOT, 158, 58, SlotPredicates.COLLECTOR_INV));

		int counter = main.getSlots() - 1;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				addSlotToContainer(new ValidatedSlot(main, counter--, 218 + i * 18, 8 + j * 18, SlotPredicates.COLLECTOR_INV));
			}
		}

		addSlotToContainer(new ValidatedSlot(aux, TileEntityCollectorMk4.UPGRADE_SLOT, 158, 13, SlotPredicates.COLLECTOR_INV));

		addSlotToContainer(new ValidatedSlot(aux, TileEntityCollectorMk4.LOCK_SLOT, 187, 36, SlotPredicates.COLLECTOR_LOCK));

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player, j + i * 9, 30 + j * 18, 84 + i * 18));
			}
		}

		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 30 + i * 18, 142));
		}
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		PacketHandler.sendProgressBarUpdateInt(listener, this, 0, tile.getSunLevel());
		PacketHandler.sendProgressBarUpdateInt(listener, this, 1, (int)tile.getStoredEmc());
		PacketHandler.sendProgressBarUpdateInt(listener, this, 2, (int)tile.getItemChargeProportion() * 8000);
		PacketHandler.sendProgressBarUpdateInt(listener, this, 3, (int)tile.getFuelProgress() * 8000);
		PacketHandler.sendProgressBarUpdateInt(listener, this, 4, (int)tile.getItemCharge() * 8000);
	}

	@Override
	public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		if(slotId >= 0 && getSlot(slotId) instanceof SlotGhost && getSlot(slotId).getStack() != null) {
			getSlot(slotId).putStack(null);
			return null;
		}
		else {
			return super.slotClick(slotId, dragType, clickTypeIn, player);
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		if(sunLevel != tile.getSunLevel()) {
			for(IContainerListener icrafting : listeners) {
				PacketHandler.sendProgressBarUpdateInt(icrafting, this, 0, tile.getSunLevel());
			}

			sunLevel = tile.getSunLevel();
		}

		if(emc != ((int)tile.getStoredEmc())) {
			for(IContainerListener icrafting : listeners) {
				PacketHandler.sendProgressBarUpdateInt(icrafting, this, 1, ((int)tile.getStoredEmc()));
			}

			emc = ((int)tile.getStoredEmc());
		}

		if(kleinChargeProgress != tile.getItemChargeProportion()) {
			for(IContainerListener icrafting : listeners) {
				PacketHandler.sendProgressBarUpdateInt(icrafting, this, 2, (int)(tile.getItemChargeProportion() * 8000));
			}

			kleinChargeProgress = tile.getItemChargeProportion();
		}

		if(fuelProgress != tile.getFuelProgress()) {
			for(IContainerListener icrafting : listeners) {
				PacketHandler.sendProgressBarUpdateInt(icrafting, this, 3, (int)(tile.getFuelProgress() * 8000));
			}

			fuelProgress = tile.getFuelProgress();
		}

		if(kleinEmc != ((int)tile.getItemCharge())) {
			for(IContainerListener icrafting : listeners) {
				PacketHandler.sendProgressBarUpdateInt(icrafting, this, 4, ((int)tile.getItemCharge()));
			}

			kleinEmc = ((int)tile.getItemCharge());
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void updateProgressBar(int id, int data) {
		switch(id) {
		case 0:	sunLevel = data;	break;
		case 1:	emc = data;	break;
		case 2: kleinChargeProgress = data / 8000.0;	break;
		case 3:	fuelProgress = data / 8000.0;	break;
		case 4:	kleinEmc = data;	break;
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

		if(index <= 18) {
			if(!mergeItemStack(stack, 19, 54, false)) {
				return null;
			}
		}
		else if(index <= 54) {
			if(!FuelMapper.isStackFuel(stack) || FuelMapper.isStackMaxFuel(stack) || !mergeItemStack(stack, 1, 8, false)) {
				return null;
			}
		}
		else {
			return null;
		}

		if(stack.isEmpty()) {
			slot.putStack(null);
		}
		else {
			slot.onSlotChanged();
		}

		slot.onTake(playerIn, stack);
		return newStack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq(tile.getPos().getX() + 0.5, tile.getPos().getY() + 0.5, tile.getPos().getZ() + 0.5) <= 64.0;
	}

}
