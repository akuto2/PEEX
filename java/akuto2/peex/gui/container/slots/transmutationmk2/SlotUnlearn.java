package akuto2.peex.gui.container.slots.transmutationmk2;

import akuto2.peex.gui.container.inventory.TransmutationMk2Inventory;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotUnlearn extends Slot{

	private TransmutationMk2Inventory inv;

	public SlotUnlearn(TransmutationMk2Inventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
		inv = inventory;
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return (!getHasStack()) && (EMCHelper.doesItemHaveEmc(stack));
	}

	@Override
	public void putStack(ItemStack stack) {
		if(stack != null) {
			inv.handleUnlearn(stack);
		}
		super.putStack(stack);
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
