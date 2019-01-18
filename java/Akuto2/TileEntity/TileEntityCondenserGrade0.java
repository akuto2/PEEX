package Akuto2.TileEntity;

import Akuto2.ObjHandlerPEEX;
import Akuto2.Blocks.BlockCondenserGrade0;
import moze_intel.projecte.gameObjs.blocks.MatterFurnace;
import moze_intel.projecte.gameObjs.blocks.Relay;
import moze_intel.projecte.utils.ItemHelper;
import moze_intel.projecte.utils.NBTWhitelist;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCondenserGrade0 extends TileEntityDirection implements IInventory, ISidedInventory{
	protected ItemStack[] inventory;
	private ItemStack lock;
	protected boolean loadChecks;
	private int ticksSinceSync;
	public float lidAngle;
	public float prevLidAngle;
	public int numPlayersUsing;

	public TileEntityCondenserGrade0(){
		inventory = new ItemStack[92];
		loadChecks = false;
	}

	@Override
	public void updateEntity(){
		super.updateEntity();
		updateChest();
		if(worldObj.isRemote){
			return;
		}
		if (this.getWorldObj().getBlock(xCoord, yCoord, zCoord) instanceof BlockCondenserGrade0)
			pushToInventories();
		if(loadChecks){
			checkLockAndUpdate();
			loadChecks = true;
		}
		if(lock != null){
			condense();
		}
	}

	public void checkLockAndUpdate(){
		lock = inventory[0];
		if(lock == null){
			return;
		}
	}

	protected void condense(){
		for(int i = 1; i < 92; i++){
			ItemStack stack = getStackInSlot(i);
			if((stack != null) && (!isStackEqualToLock(stack))){
				if(stack.stackSize <= 0){
					inventory[i] = null;
				}
			}
		}
		if(hasSpace()){
			pushStack();
		}
	}

	protected void pushStack(){
		int slot = getSlotForStack();
		if(slot == 0){
			return;
		}
		if(inventory[slot] == null){
			ItemStack lockCopy = lock.copy();
			if((lockCopy.hasTagCompound()) && (!NBTWhitelist.shouldDupeWithNBT(lockCopy))){
				if(lockCopy.getTagCompound().getString("TypeName") != null) {
					String typeName = lockCopy.getTagCompound().getString("TypeName");
					lockCopy.setTagCompound(new NBTTagCompound());
					lockCopy.getTagCompound().setString("TypeName", typeName);
				}
				else {
					lockCopy.setTagCompound(new NBTTagCompound());
				}
			}
			inventory[slot] = lockCopy;
		}
		else{
			inventory[slot].stackSize += 1;
		}
		markDirty();
	}

	protected int getSlotForStack(){
		for(int i = 1; i < inventory.length; i++){
			ItemStack stack = inventory[i];
			if(stack == null){
				return i;
			}
			if((isStackEqualToLock(stack)) && (stack.stackSize < stack.getMaxStackSize())){
				return i;
			}
		}
		return 0;
	}

	protected boolean hasSpace(){
		for(int i = 1; i < inventory.length; i++){
			ItemStack stack = inventory[i];
			if(stack == null){
				return true;
			}
			if((isStackEqualToLock(stack)) && (stack.stackSize < stack.getMaxStackSize())){
				return true;
			}
		}
		return false;
	}

	protected boolean isStackEqualToLock(ItemStack stack){
		if(lock == null){
			return false;
		}
		if(NBTWhitelist.shouldDupeWithNBT(lock)){
			return ItemHelper.areItemStacksEqual(lock, stack);
		}
		return ItemHelper.areItemStacksEqualIgnoreNBT(lock, stack);
	}

	@Override
	public void invalidate() {
		super.invalidate();

		loadChecks = false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		NBTTagList list = nbt.getTagList("Items", 10);
		for(int i = 0; i < list.tagCount(); i++){
			NBTTagCompound subNBT = list.getCompoundTagAt(i);
			inventory[subNBT.getByte("Slot")] = ItemStack.loadItemStackFromNBT(subNBT);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < inventory.length; i++){
			if(inventory != null){
				NBTTagCompound subNBT = new NBTTagCompound();
				subNBT.setByte("Slot", (byte)i);
				inventory[i].writeToNBT(subNBT);
				list.appendTag(subNBT);
			}
		}
		nbt.setTag("Items", list);
	}

	@Override
	public ItemStack getStackInSlot(int slot){
		return inventory[slot];
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack decrStackSize(int slot, int qnt) {
		ItemStack stack = inventory[slot];
		if(stack != null){
			if(stack.stackSize <= qnt){
				inventory[slot] = null;
			}
			else{
				stack = stack.splitStack(qnt);
				if(stack.stackSize == 0){
					inventory[slot] = null;
				}
			}
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		if(inventory[slot] != null){
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
		return "tile.pe_condenser_grade0.name";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return worldObj.getTileEntity(xCoord, yCoord, zCoord) == this;
	}

	public void updateChest(){
		if(++ticksSinceSync % 20 * 4 == 0){
			worldObj.addBlockEvent(xCoord, yCoord, zCoord, ObjHandlerPEEX.condenserGrade0, 1, numPlayersUsing);
		}
		prevLidAngle = lidAngle;
		float angleIncrement = 0.1F;
		if((numPlayersUsing > 0) && (lidAngle == 0.0F)){
			double adjustXCoord = xCoord + 0.5D;
			double adjustZCoord = zCoord + 0.5D;
			worldObj.playSoundEffect(adjustXCoord, yCoord, adjustZCoord, "random.chestopen", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
		}
		if(((numPlayersUsing == 0) && (lidAngle > 0.0F)) || ((numPlayersUsing > 0) && (lidAngle < 1.0F))){
			float var8 = lidAngle;
			if(numPlayersUsing > 0){
				lidAngle += angleIncrement;
			}else{
				lidAngle -= angleIncrement;
			}
			if(lidAngle > 1.0F){
				lidAngle = 1.0F;
			}
			if((lidAngle < 0.5F) && (var8 >= 0.5F)){
				double adjustXCoord = xCoord + 0.5D;
				double adjustZCoord = zCoord + 0.5D;
				worldObj.playSoundEffect(adjustXCoord, yCoord, adjustZCoord, "random.chestclosed", 0.5F, worldObj.rand.nextFloat() * 0.1F + 0.9F);
			}
			if(lidAngle < 0.0F){
				lidAngle = 0.0F;
			}
		}
	}

	private void pushToInventories()
	{
		int iSide = 0;

		for (ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
		{
			if (dir.offsetY > 0)
			{
				continue;
			}

			int x = this.xCoord + dir.offsetX;
			int y = this.yCoord + dir.offsetY;
			int z = this.zCoord + dir.offsetZ;

			TileEntity tile = this.worldObj.getTileEntity(x, y, z);

			if (tile == null)
			{
				continue;
			}

			Block block = this.worldObj.getBlock(x, y, z);
			if(block instanceof Relay ||
					block instanceof MatterFurnace) {
				continue;
			}

			if (tile != null && tile instanceof ISidedInventory)
			{
				ISidedInventory inv = (ISidedInventory) tile;

				if (inv != null)
				{
					int[] slots = inv.getAccessibleSlotsFromSide(ForgeDirection.OPPOSITES[iSide]);

					if (slots.length > 0)
					{
						for (int j = 1; j < inventory.length; j++)
						{
							ItemStack stack = inventory[j];

							if (stack == null)
							{
								continue;
							}

							for (int k : slots)
							{
								if (inv.canInsertItem(k, stack, Facing.oppositeSide[iSide]))
								{
									ItemStack otherStack = inv.getStackInSlot(k);

									if (otherStack == null)
									{
										inv.setInventorySlotContents(k, stack);
										inventory[j] = null;
										break;
									}
									else if (ItemHelper.areItemStacksEqual(stack, otherStack))
									{
										int remain = otherStack.getMaxStackSize() - otherStack.stackSize;

										if (stack.stackSize <= remain)
										{
											otherStack.stackSize += stack.stackSize;
											inventory[j] = null;
											break;
										}
										else
										{
											otherStack.stackSize += remain;
											inventory[j].stackSize -= remain;
										}
									}
								}
							}
						}
					}
				}
			}
			else if (tile instanceof IInventory)
			{
				for (int j = 1; j < inventory.length; j++)
				{
					ItemStack stack = inventory[j];

					if (stack != null)
					{
						ItemStack result = ItemHelper.pushStackInInv((IInventory) tile, stack);

						if (result == null)
						{
							inventory[j] = null;
							break;
						}
						else
						{
							inventory[j].stackSize = result.stackSize;
						}
					}
				}
			}
		}
	}

	@Override
	public void openInventory() {
		numPlayersUsing += 1;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ObjHandlerPEEX.condenserGrade0, 1, numPlayersUsing);
	}

	@Override
	public void closeInventory() {
		numPlayersUsing -= 1;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ObjHandlerPEEX.condenserGrade0, 1, numPlayersUsing);
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack){
		if(slot == 0){
			return false;
		}
		return (!isStackEqualToLock(stack));
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int side) {
		int[] slots = new int[inventory.length - 1];
		for(int i = 1; i < inventory.length; i++){
			slots[(i - 1)] = i;
		}
		return slots;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack item, int side) {
		if(slot == 0){
			return false;
		}
		return isItemValidForSlot(slot, item);
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack item, int stack) {
		if(slot == 0){
			return false;
		}
		return isStackEqualToLock(item);
	}
}
