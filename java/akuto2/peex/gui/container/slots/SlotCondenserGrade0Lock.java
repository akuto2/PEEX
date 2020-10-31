package akuto2.peex.gui.container.slots;

import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class SlotCondenserGrade0Lock extends SlotItemHandler{
	public SlotCondenserGrade0Lock(IItemHandler inv, int slotIndex, int xPos, int yPos) {
		super(inv, slotIndex, xPos, yPos);
	}

	@Override
	public void putStack(ItemStack stack) {
		if(stack != null && ItemHelper.isDamageable(stack)) {
			stack.setItemDamage(0);
		}

		super.putStack(stack);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		if(stack != null) {
			putStack(ItemHelper.getNormalizedStack(stack));
		}

		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer playerIn) {
		return false;
	}

	@Override
	public int getSlotStackLimit() {
		return 1;
	}
}
