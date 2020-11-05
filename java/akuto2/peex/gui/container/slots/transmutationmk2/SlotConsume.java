package akuto2.peex.gui.container.slots.transmutationmk2;

import akuto2.peex.gui.container.inventory.TransmutationMk2Inventory;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotConsume extends Slot{

	private TransmutationMk2Inventory inv;

	public SlotConsume(TransmutationMk2Inventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
		inv = inventory;
	}

	@Override
	public void putStack(ItemStack stack) {
		if(stack == null) {
			return;
		}
		ItemStack cache = stack.copy();

		float toAdd = 0.0F;
		while((!inv.hasMaxedEmc()) && (stack.stackSize > 0)) {
			toAdd += EMCHelper.getEmcValue(stack);
			stack.stackSize--;
		}
		inv.addEmc(toAdd);
		onSlotChanged();
		inv.handleKnowledge(cache);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return (!inv.hasMaxedEmc()) && ((EMCHelper.doesItemHaveEmc(stack)) || (stack.getItem() == ObjHandler.tome));
	}
}
