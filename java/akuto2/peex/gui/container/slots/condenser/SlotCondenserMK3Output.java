package akuto2.peex.gui.container.slots.condenser;

import akuto2.peex.tile.TileEntityCondenserMk3;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCondenserMK3Output extends Slot {

	public SlotCondenserMK3Output(TileEntityCondenserMk3 inventory, int slotIndex, int xPos, int yPos)
	{
		super(inventory, slotIndex, xPos, yPos);
	}

	@Override
	public boolean isItemValid(ItemStack stack)
	{
		return false;
	}
}
