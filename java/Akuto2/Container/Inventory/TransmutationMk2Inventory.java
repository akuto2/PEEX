package Akuto2.Container.Inventory;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import Akuto2.PlayerData.TransmutationMk2;
import moze_intel.projecte.emc.FuelMapper;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.utils.Comparators;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import moze_intel.projecte.utils.ItemSearchHelper;
import moze_intel.projecte.utils.NBTWhitelist;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TransmutationMk2Inventory implements IInventory{
	public float emc;
	public static final float TRANSMUTATION_MAX_EMC = 1.0E20F;
	private EntityPlayer player = null;
	private static final int LOCK_INDEX = 8;
	private static final int[] MATTER_INDEXS = {12, 11, 13, 10, 14, 21, 15, 20, 16, 19, 17, 18};
	private static final int[] FUEL_INDEXES = {22, 23, 24, 25};
	private ItemStack[] inventory = new ItemStack[27];
	public int learnFlag = 0;
	public int unlearnFlag = 0;
	public String Filter = "";
	public int searchpage = 0;
	public List<ItemStack> knowledge = Lists.newArrayList();

	public TransmutationMk2Inventory(EntityPlayer player) {
		this.player = player;
	}

	public void handleKnowledge(ItemStack stack) {
		if(stack.stackSize > 1) {
			stack.stackSize = 1;
		}
		if((!stack.getHasSubtypes()) && (stack.getMaxDamage() != 0) && (stack.getItemDamage() != 0)) {
			stack.setItemDamage(0);
		}
		if(!TransmutationMk2.hasKnowlefgeForStack(stack, player)) {
			learnFlag = 300;
			if(stack.getItem() == ObjHandler.tome) {
				TransmutationMk2.setFullKnowledge(player);
			}
			else {
				if((stack.hasTagCompound()) && (!NBTWhitelist.shouldDupeWithNBT(stack))) {
					stack.stackTagCompound = null;
				}
				TransmutationMk2.addKnowledge(stack, player);
			}
			if(!player.worldObj.isRemote) {
				TransmutationMk2.sync(player);
			}
		}
		updateOutputs();
	}

	public void handleUnlearn(ItemStack stack) {
		if(stack.stackSize > 1) {
			stack.stackSize = 1;
		}
		if((!stack.getHasSubtypes()) && (stack.getMaxDamage() != 0) && (stack.getItemDamage() != 0)) {
			stack.setItemDamage(0);
		}
		if(TransmutationMk2.hasKnowlefgeForStack(stack, player)) {
			unlearnFlag = 300;
			if((stack.hasTagCompound()) &&(!NBTWhitelist.shouldDupeWithNBT(stack))) {
				stack.stackTagCompound = null;
			}
			TransmutationMk2.removeKnowledge(stack, player);
			if(!player.worldObj.isRemote) {
				TransmutationMk2.sync(player);
			}
		}
		updateOutputs();
	}

	public void checkForUpdates() {
		int matterEmc = EMCHelper.getEmcValue(inventory[MATTER_INDEXS[0]]);
		int fuelEmc = EMCHelper.getEmcValue(inventory[FUEL_INDEXES[0]]);

		int maxEmc = matterEmc > fuelEmc ? matterEmc : fuelEmc;
		if(maxEmc > emc) {
			updateOutputs();
		}
	}

	public void updateOutputs() {
		updateOutputs(false);
	}

	public void updateOutputs(Boolean async) {
		if(!player.worldObj.isRemote) {
			return;
		}
		knowledge = Lists.newArrayList(TransmutationMk2.getKowledge(player));
		for(int i : MATTER_INDEXS) {
			inventory[i] = null;
		}
		for(int i : FUEL_INDEXES) {
			inventory[i] = null;
		}
		ItemStack lockCopy = null;

		Collections.sort(knowledge, Comparators.ITEMSTACK_EMC_DESCENDING);
		ItemSearchHelper searchHelper = ItemSearchHelper.create(Filter);
		if(inventory[8] != null) {
			int reqEmc = EMCHelper.getEmcValue(inventory[8]);
			if(emc < reqEmc) {
				return;
			}
			lockCopy = ItemHelper.getNormalizedStack(inventory[8]);
			if((lockCopy.hasTagCompound()) && (!NBTWhitelist.shouldDupeWithNBT(lockCopy))) {
				lockCopy.setTagCompound(new NBTTagCompound());
			}
			Iterator<ItemStack> iter = knowledge.iterator();
			int pageCounter = 0;
			while(iter.hasNext()) {
				ItemStack stack = (ItemStack)iter.next();
				if(EMCHelper.getEmcValue(stack) > reqEmc) {
					iter.remove();
				}
				else if(ItemHelper.basicAreStacksEqual(lockCopy, stack)) {
					iter.remove();
				}
				else if(searchHelper.doesItemMatchFilter(stack)) {
					iter.remove();
				}
				else if(pageCounter < searchpage * 12) {
					pageCounter++;
					iter.remove();
				}
			}
		}
		else {
			Iterator<ItemStack> iter = knowledge.iterator();
			int pageCounter = 0;
			while(iter.hasNext()) {
				ItemStack stack = (ItemStack)iter.next();
				if(emc < EMCHelper.getEmcValue(stack)) {
					iter.remove();
				}
				else if(!searchHelper.doesItemMatchFilter(stack)) {
					iter.remove();
				}
				else if(pageCounter < searchpage * 12) {
					pageCounter++;
					iter.remove();
				}
			}
		}

		int matterCounter = 0;
		int fuelCounter = 0;
		if(lockCopy != null) {
			if(FuelMapper.isStackFuel(lockCopy)) {
				inventory[FUEL_INDEXES[0]] = lockCopy;
				fuelCounter++;
			}
			else {
				inventory[MATTER_INDEXS[0]] = lockCopy;
				matterCounter++;
			}
		}
		for(ItemStack stack : knowledge) {
			if(FuelMapper.isStackFuel(stack)) {
				if(fuelCounter < 4) {
					inventory[FUEL_INDEXES[fuelCounter]] = stack;
					fuelCounter++;
				}
			}
			else if(matterCounter < 12) {
				inventory[MATTER_INDEXS[matterCounter]] = stack;
				matterCounter++;
			}
		}
	}

	public void writeIntoOutputSlot(int slot, ItemStack stack) {
		if((EMCHelper.doesItemHaveEmc(stack)) && (EMCHelper.getEmcValue(stack) <= emc) && (TransmutationMk2.hasKnowlefgeForStack(stack, player))) {
			inventory[slot] = stack;
		}
		else {
			inventory[slot] = null;
		}
	}

	public List<ItemStack> getOutput(){
		return Arrays.asList(inventory).subList(10, 26);
	}

	@Override
	public int getSizeInventory() {
		return 26;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int par2) {
		ItemStack stack = inventory[slot];
		if(stack != null) {
			if(stack.stackSize <= par2) {
				inventory[slot] = null;
			}
			else {
				stack = stack.splitStack(par2);
				if(stack.stackSize == 0) {
					inventory[slot] = null;
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(inventory[slot] != null) {
			ItemStack stack = inventory[slot];
			inventory[slot] = null;
			return stack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
		if((stack != null) && (stack.stackSize > getInventoryStackLimit())) {
			stack.stackSize = getInventoryStackLimit();
		}
		markDirty();
	}

	@Override
	public String getInventoryName() {
		return "item.transmutationMk2.name";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return true;
	}

	@Override
	public void openInventory() {
		emc = TransmutationMk2.getEmc(player);
		ItemStack[] inputLocks = TransmutationMk2.getInputAndLock(player);
		System.arraycopy(inputLocks, 0, inventory, 0, 9);
		if(player.worldObj.isRemote) {
			updateOutputs(true);
		}
	}

	@Override
	public void closeInventory() {
		if(!player.worldObj.isRemote) {
			TransmutationMk2.setEmc(player, emc);
			TransmutationMk2.setInputAndLocks((ItemStack[])Arrays.copyOfRange(inventory, 0, 9), player);
			TransmutationMk2.sync(player);
		}
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return false;
	}

	public void addEmc(float value) {
		emc += value;
		if((emc >= 1.0E20F) || (emc < 0.0F)) {
			emc = 1.0E20F;
		}
	}

	public void removeEmc(float value) {
		emc -= value;
		if(emc < 0.0F) {
			emc = 0.0F;
		}
	}

	public boolean hasMaxedEmc() {
		return emc >= 1.0E20F;
	}

}
