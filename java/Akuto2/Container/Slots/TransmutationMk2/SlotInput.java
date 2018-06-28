package Akuto2.Container.Slots.TransmutationMk2;

import Akuto2.Container.Inventory.TransmutationMk2Inventory;
import moze_intel.projecte.api.item.IItemEmc;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotInput extends Slot{

	private TransmutationMk2Inventory transmutationMk2Inventory;

	public SlotInput(TransmutationMk2Inventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
		transmutationMk2Inventory = inventory;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return (!getHasStack()) && (EMCHelper.doesItemHaveEmc(stack));
	}

	@Override
	public void putStack(ItemStack stack) {
		if(stack == null) {
			return;
		}
		super.putStack(stack);
		if((stack.getItem() instanceof IItemEmc)) {
			IItemEmc itemEmc = (IItemEmc)stack.getItem();
			double remainingEmc = itemEmc.getMaximumEmc(stack) - (int)Math.ceil(itemEmc.getStoredEmc(stack));
			if(transmutationMk2Inventory.emc >= remainingEmc) {
				itemEmc.addEmc(stack, remainingEmc);
				transmutationMk2Inventory.removeEmc((float)remainingEmc);
			}
			else {
				itemEmc.addEmc(stack, transmutationMk2Inventory.emc);
				transmutationMk2Inventory.emc = 0.0F;
			}
		}
		if(stack.getItem() != ObjHandler.tome) {
			transmutationMk2Inventory.handleKnowledge(stack.copy());
		}
		else {
			transmutationMk2Inventory.updateOutputs();
		}
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
