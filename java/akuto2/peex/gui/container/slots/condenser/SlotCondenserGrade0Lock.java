package akuto2.peex.gui.container.slots.condenser;

import akuto2.peex.gui.container.ContainerCondenserGrade0;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCondenserGrade0Lock extends Slot {
	private ContainerCondenserGrade0 container;

	public SlotCondenserGrade0Lock(ContainerCondenserGrade0 condenserGrade0, int slotIndex, int xPos, int yPos){
		super(condenserGrade0.tile, slotIndex, xPos, yPos);
		container = condenserGrade0;
	}

	@Override
	public boolean isItemValid(ItemStack stack){
		if((stack != null) && (!container.tile.getWorldObj().isRemote)){
			putStack(ItemHelper.getNormalizedStack(stack));
			container.tile.checkLockAndUpdate();
			container.detectAndSendChanges();
		}
		return false;
	}

	@Override
	public boolean canTakeStack(EntityPlayer player){
		return false;
	}

	@Override
	public int getSlotStackLimit(){
		return 1;
	}
}
