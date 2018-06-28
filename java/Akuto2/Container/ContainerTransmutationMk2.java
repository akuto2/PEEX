package Akuto2.Container;

import Akuto2.ObjHandlerPEEX;
import Akuto2.Container.Inventory.TransmutationMk2Inventory;
import Akuto2.Container.Slots.TransmutationMk2.SlotConsume;
import Akuto2.Container.Slots.TransmutationMk2.SlotInput;
import Akuto2.Container.Slots.TransmutationMk2.SlotLock;
import Akuto2.Container.Slots.TransmutationMk2.SlotOutput;
import Akuto2.Container.Slots.TransmutationMk2.SlotUnlearn;
import Akuto2.Packet.PEEXPacketHandler;
import Akuto2.Packet.PEEXSerchUpdatePKT;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerTransmutationMk2 extends Container{

	public TransmutationMk2Inventory transmutationMk2Inventory;

	public ContainerTransmutationMk2(InventoryPlayer player, TransmutationMk2Inventory inventory) {
		transmutationMk2Inventory = inventory;

		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 0, 43, 23));
		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 1, 34, 41));
		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 2, 52, 41));
		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 3, 16, 50));
		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 4, 70, 50));
		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 5, 34, 59));
		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 6, 52, 59));
		addSlotToContainer(new SlotInput(transmutationMk2Inventory, 7, 43, 77));
		addSlotToContainer(new SlotLock(transmutationMk2Inventory, 8, 158, 50));
		addSlotToContainer(new SlotConsume(transmutationMk2Inventory, 9, 107, 97));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 10, 123, 30));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 11, 140, 13));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 12, 158, 9));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 13, 176, 13));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 14, 193, 30));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 15, 199, 50));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 16, 193, 70));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 17, 176, 87));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 18, 158, 91));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 19, 140, 87));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 20, 123, 70));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 21, 116, 50));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 22, 158, 31));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 23, 139, 50));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 24, 177, 50));
		addSlotToContainer(new SlotOutput(transmutationMk2Inventory, 25, 158, 69));
		addSlotToContainer(new SlotUnlearn(transmutationMk2Inventory, 26, 89, 97));
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(player, j + i * 9 + 9, 35 + j * 18, 117 + i * 18));
			}
		}
		for(int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(player, i, 35 + i * 18, 175));
		}
		transmutationMk2Inventory.openInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
		Slot slot = getSlot(slotIndex);
		if((slot == null) || (!slot.getHasStack())) {
			return null;
		}
		ItemStack stack = slot.getStack();
		ItemStack newStack = stack.copy();
		if(slotIndex <= 7) {
			return null;
		}
		if((slotIndex >= 10) && (slotIndex <= 25)) {
			int emc = EMCHelper.getEmcValue(newStack);

			int stackSize = 0;
			while((transmutationMk2Inventory.emc >= emc) && (stackSize < newStack.getMaxStackSize()) && (ItemHelper.hasSpace(player.inventory.mainInventory, newStack))) {
				transmutationMk2Inventory.removeEmc(emc);
				ItemHelper.pushStackInInv(player.inventory, ItemHelper.getNormalizedStack(newStack));
				stackSize++;
			}
			transmutationMk2Inventory.updateOutputs();
		}
		else if(slotIndex >= 26) {
			int emc = EMCHelper.getEmcValue(stack);
			if((emc == 0) && (stack.getItem() != ObjHandler.tome)) {
				return null;
			}
			while((!transmutationMk2Inventory.hasMaxedEmc()) && (stack.stackSize > 0)) {
				transmutationMk2Inventory.addEmc(emc);
				stack.stackSize -= 1;
			}
			transmutationMk2Inventory.handleKnowledge(newStack);
			if(stack.stackSize == 0) {
				slot.putStack(null);
			}
		}
		return null;
	}

	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		transmutationMk2Inventory.closeInventory();
	}

	private FMLSecurityManager accesibleManager = new FMLSecurityManager();

	class FMLSecurityManager extends SecurityManager{
		FMLSecurityManager()	{}

		Class<?>[] getStackClasses(){
			return getClassContext();
		}
	}

	private boolean isNeiScrollWheel() {
		int stacktraceDepth = 3;
		Class<?>[] stacktrace = accesibleManager.getStackClasses();
		return (stacktrace.length >= 3) && (stacktrace[3] != null) && (stacktrace[3].getName().equals("codechicken.nei.FastTransferManager"));
	}

	@Override
	public ItemStack slotClick(int slot, int button, int flag, EntityPlayer player) {
		if((player.worldObj.isRemote) && (isNeiScrollWheel())) {
			return null;
		}
		if((player.worldObj.isRemote) && (10 <= slot) && (slot <= 25)) {
			PEEXPacketHandler.sendToServer(new PEEXSerchUpdatePKT(slot, getSlot(slot).getStack()));
		}
		if((slot >= 0) && (getSlot(slot) != null)) {
			if((getSlot(slot).getStack() != null) && (getSlot(slot).getStack().getItem() == ObjHandlerPEEX.transmutationMk2Tablet) && getSlot(slot).getStack() == player.getHeldItem()) {
				return null;
			}
		}
		return super.slotClick(slot, button, flag, player);
	}

	@Override
	public boolean canDragIntoSlot(Slot slot) {
		if(((slot instanceof SlotConsume)) || (slot instanceof SlotUnlearn) || (slot instanceof SlotInput) || (slot instanceof SlotLock) || (slot instanceof SlotOutput)) {
			return false;
		}
		return true;
	}
}
