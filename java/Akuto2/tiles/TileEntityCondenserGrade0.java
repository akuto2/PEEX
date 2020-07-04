package Akuto2.tiles;

import Akuto2.ObjHandlerPEEX;
import Akuto2.blocks.BlockCondenserGrade0;
import moze_intel.projecte.gameObjs.blocks.MatterFurnace;
import moze_intel.projecte.gameObjs.blocks.Relay;
import moze_intel.projecte.utils.ItemHelper;
import moze_intel.projecte.utils.NBTWhitelist;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityCondenserGrade0 extends TileEntity implements ITickable{
	private final ItemStackHandler inputInventory = createInput();
	private final ItemStackHandler outputInventory = createOutput();
	private final IItemHandler automationInventory = createAutomationInventory();
	private final ItemStackHandler lock = new StackHandler(1);
	private int ticksSinceSync;
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;

	public ItemStackHandler getLock() {
		return lock;
	}

	public ItemStackHandler getInput() {
		return inputInventory;
	}

	public ItemStackHandler getOutput() {
		return outputInventory;
	}

	protected ItemStackHandler createInput() {
		return new StackHandler(91);
	}

	protected ItemStackHandler createOutput() {
		return inputInventory;
	}

	protected IItemHandler createAutomationInventory() {
		return new WrappedItemHandler(inputInventory, WrappedItemHandler.WriteMode.IN_OUT) {
			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				return !isStackEqualToLock(stack) ? super.insertItem(slot, stack, simulate) : stack;
			}

			@Override
			public ItemStack extractItem(int slot, int amount, boolean simulate) {
				if(getStackInSlot(slot) != null && isStackEqualToLock(getStackInSlot(slot))) {
					return super.extractItem(slot, amount, simulate);
				}
				else {
					return null;
				}
			}
		};
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(automationInventory);
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public void update() {
		if(worldObj.isRemote) {
			return;
		}
		updateChest();
		if(worldObj.getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ())).getBlock() instanceof BlockCondenserGrade0) {
			pushToInventories();
		}
		if(lock.getStackInSlot(0) != null) {
			condence();
		}
	}

	protected void condence() {
		for(int i = 0; i < inputInventory.getSlots(); i++) {
			ItemStack stack = inputInventory.getStackInSlot(i);

			if(stack != null && !isStackEqualToLock(stack)) {
				if(stack.stackSize <= 0) {
					inputInventory.setStackInSlot(i, null);
				}
			}
		}

		if(hasSpace()) {
			pushStack();
		}
	}

	protected void pushStack() {
		ItemStack lockCopy = lock.getStackInSlot(0).copy();

		if(lockCopy.hasTagCompound() && !NBTWhitelist.shouldDupeWithNBT(lockCopy)) {
			lockCopy.setTagCompound(new NBTTagCompound());
		}

		ItemHandlerHelper.insertItemStacked(outputInventory, lockCopy, false);
	}

	protected boolean hasSpace() {
		for(int i = 0; i < outputInventory.getSlots(); i++) {
			ItemStack stack = outputInventory.getStackInSlot(i);

			if(stack == null) {
				return true;
			}

			if(isStackEqualToLock(stack) && stack.stackSize < stack.getMaxStackSize()) {
				return true;
			}
		}

		return false;
	}

	public boolean isStackEqualToLock(ItemStack stack) {
		if(lock.getStackInSlot(0) == null) {
			return false;
		}

		if(NBTWhitelist.shouldDupeWithNBT(lock.getStackInSlot(0))) {
			return ItemHelper.areItemStacksEqual(lock.getStackInSlot(0), stack);
		}

		return ItemHelper.areItemStacksEqualIgnoreNBT(lock.getStackInSlot(0), stack);
	}

	private void updateChest() {
		if(++ticksSinceSync % 20 * 4 == 0) {
			worldObj.addBlockEvent(pos, ObjHandlerPEEX.condenserGrade0, 1, numPlayersUsing);
		}

		prevLidAngle = lidAngle;
		float angleIncrement = 0.1F;

		if(numPlayersUsing > 0 && lidAngle == 0.0F) {
			worldObj.playSound(null, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}

		if(numPlayersUsing == 0 && lidAngle > 0.0F || numPlayersUsing > 0 && lidAngle < 1.0F) {
			float var8 = lidAngle;
			if(numPlayersUsing > 0) {
				lidAngle += angleIncrement;
			}
			else {
				lidAngle -= angleIncrement;
			}

			if(lidAngle > 1.0F) {
				lidAngle = 1.0F;
			}

			if(lidAngle < 0.5F && var8 >= 0.5F) {
				worldObj.playSound(null, pos, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}

			if(lidAngle < 0.0F) {
				lidAngle = 0.0F;
			}
		}
	}

	private void pushToInventories() {
		for(EnumFacing face : EnumFacing.VALUES) {
			if(face.getFrontOffsetY() > 0) {
				continue;
			}

			int x = pos.getX() + face.getFrontOffsetX();
			int y = pos.getY() + face.getFrontOffsetY();
			int z = pos.getZ() + face.getFrontOffsetZ();

			TileEntity tile = worldObj.getTileEntity(new BlockPos(x, y, z));

			if(tile == null) {
				continue;
			}

			Block block = worldObj.getBlockState(new BlockPos(x, y, z)).getBlock();

			if(block instanceof Relay || block instanceof MatterFurnace || block instanceof BlockCondenserGrade0) {
				continue;
			}

			IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.DOWN);

			if(handler == null) {
				if(tile instanceof ISidedInventory) {
					handler = new SidedInvWrapper((ISidedInventory)tile, EnumFacing.UP);
				}
				else if(tile instanceof IInventory) {
					handler = new InvWrapper((IInventory)tile);
				}
				else {
					continue;
				}
			}

			for(int i = 0; i < getOutput().getSlots(); i++) {
				// 搬出を試す
				ItemStack extractTest = getOutput().extractItem(i, Integer.MAX_VALUE, true);
				// null判定はスロットにアイテムがないということ
				if(extractTest == null) {
					continue;
				}

				// 相手側に搬入できるかを試す
				ItemStack remainderTest = ItemHandlerHelper.insertItemStacked(handler, extractTest, true);
				int successfullyTransferred = extractTest.stackSize - (remainderTest == null ? 0 : remainderTest.stackSize);

				// 搬入できるのならば実際にアイテムを動かす
				if(successfullyTransferred > 0) {
					ItemStack toInsert = getOutput().extractItem(i, successfullyTransferred, false);
					ItemStack result = ItemHandlerHelper.insertItemStacked(handler, toInsert, false);
					if(result == null) {
						break;
					}
				}
			}
		}
	}

	@Override
	public boolean receiveClientEvent(int id, int type) {
		if(id == 1) {
			numPlayersUsing = type;
			return true;
		}
		else {
			return super.receiveClientEvent(id, type);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		inputInventory.deserializeNBT(compound.getCompoundTag("Input"));
		lock.deserializeNBT(compound.getCompoundTag("Lock"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound = super.writeToNBT(compound);
		compound.setTag("Input", inputInventory.serializeNBT());
		compound.setTag("Lock", lock.serializeNBT());
		return compound;
	}

	class StackHandler extends ItemStackHandler{
		StackHandler(int size) {
			super(size);
		}

		@Override
		protected void onContentsChanged(int slot) {
			TileEntityCondenserGrade0.this.markDirty();
		}
	}
}
