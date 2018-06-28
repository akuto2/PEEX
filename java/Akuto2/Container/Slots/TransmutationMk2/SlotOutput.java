package Akuto2.Container.Slots.TransmutationMk2;

import Akuto2.Container.Inventory.TransmutationMk2Inventory;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotOutput extends Slot{

	private TransmutationMk2Inventory inv;

	public SlotOutput(TransmutationMk2Inventory inventory, int par2, int par3, int par4) {
		super(inventory, par2, par3, par4);
		inv = inventory;
	}

	@Override
	public ItemStack decrStackSize(int amount) {
		ItemStack stack = getStack().copy();
		stack.stackSize = amount;
		int emcValue = amount * EMCHelper.getEmcValue(stack);
		if(emcValue > inv.emc) {
			stack.stackSize = 0;
			return stack;
		}
		inv.removeEmc(emcValue);
		inv.checkForUpdates();

		return stack;
	}

	@Override
	public void putStack(ItemStack stack) {}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player) {
		if(getHasStack()) {
			return EMCHelper.getEmcValue(getStack()) <= inv.emc;
		}
		return true;
	}
}
