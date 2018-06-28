package Akuto2.Container.Slots.TransmutationMk2;

import Akuto2.Container.Inventory.TransmutationMk2Inventory;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotLock extends Slot{

	private TransmutationMk2Inventory inv;

	public SlotLock(TransmutationMk2Inventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
		inv = inventory;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return EMCHelper.doesItemHaveEmc(stack);
	}

	@Override
	public void putStack(ItemStack stack) {
		if(stack == null) {
			return;
		}
		super.putStack(stack);
		if(stack.getItem() != ObjHandler.tome) {
			inv.handleKnowledge(stack.copy());
		}
		else {
			inv.updateOutputs();
		}
	}

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack stack) {
		super.onPickupFromSlot(player, stack);

		inv.updateOutputs();
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
