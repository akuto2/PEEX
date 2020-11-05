package akuto2.peex.tile;

import akuto2.peex.ObjHandlerPEEX;
import akuto2.peex.blocks.BlockAEGUEX;
import moze_intel.projecte.gameObjs.blocks.CondenserMK2;
import moze_intel.projecte.gameObjs.blocks.MatterFurnace;
import moze_intel.projecte.gameObjs.blocks.Relay;
import moze_intel.projecte.gameObjs.tiles.CondenserTile;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityCondenserMk3 extends CondenserTile {
	private static final int INPUT_SLOTS[] = {1, 42};
	private static final int OUTPUT_SLOTS[] = {43, 84};

	private final long[] generateEmcOfAEGU = {0, 100000, 1000000, 35000000, 10000000000000000L};
	private String[][] coordAEGU = new String[3][26];	// AEGUの座標を記録
	private int numAEGU = 0;
	public boolean isGenerate = false;
	long generateEmc = 0;

	public TileEntityCondenserMk3() {
		super();
	}

	@Override
	public void updateEntity()
	{

		super.updateEntity();

		if(!worldObj.isRemote) {
			if (this.getWorldObj().getBlock(xCoord, yCoord, zCoord) instanceof CondenserMK2)
				pushToInventories();
			checkGenerate();

			if (isGenerate) {
				this.addEMC((double)generateEmc / 20);

			}
		}
	}

	@Override
	protected void condense()
	{
		for (int i = INPUT_SLOTS[0]; i <= INPUT_SLOTS[1]; i++)
		{
			ItemStack stack = inventory[i];

			if (stack == null || isStackEqualToLock(stack))
			{
				continue;
			}
			if ((long)EMCHelper.getEmcValue(stack) * stack.stackSize + (long)this.getStoredEmc() > this.getMaximumEmc()) {
				this.addEMC(EMCHelper.getEmcValue(stack));
				decrStackSize(i, 1);
			}
			else {
				this.addEMC(EMCHelper.getEmcValue(stack) * stack.stackSize);
				inventory[i] = null;
			}
			break;
		}

		while (this.hasSpace() && this.getStoredEmc() >= requiredEmc)
		{
			pushStack();
			this.removeEMC(requiredEmc);
		}
	}

	@Override
	protected boolean hasSpace()
	{
		for (int i = OUTPUT_SLOTS[0]; i <= OUTPUT_SLOTS[1]; i++)
		{
			ItemStack stack = inventory[i];

			if (stack == null)
			{
				return true;
			}

			if (isStackEqualToLock(stack) && stack.stackSize < stack.getMaxStackSize())
			{
				return true;
			}
		}

		return false;
	}

	@Override
	protected int getSlotForStack()
	{
		for (int i = OUTPUT_SLOTS[0]; i <= OUTPUT_SLOTS[1]; i++)
		{
			ItemStack stack = inventory[i];

			if (stack == null)
			{
				return i;
			}

			if (isStackEqualToLock(stack) && stack.stackSize < stack.getMaxStackSize())
			{
				return i;
			}
		}

		return 0;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack)
	{
		if (slot == 0 || slot >= OUTPUT_SLOTS[0])
		{
			return false;
		}

		return !isStackEqualToLock(stack) && EMCHelper.doesItemHaveEmc(stack);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);

		isGenerate = nbt.getBoolean("IsGenerate");
		numAEGU = nbt.getInteger("NumAEGU");
		generateEmc = nbt.getLong("GenerateEmc");

		for (int i = 0; i < coordAEGU[0].length; i++)
		{
			coordAEGU[0][i] = nbt.getString("coordAEGUX" + i);
			coordAEGU[1][i] = nbt.getString("coordAEGUY" + i);
			coordAEGU[2][i] = nbt.getString("coordAEGUZ" + i);
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);

		nbt.setBoolean("IsGenerate", isGenerate);
		nbt.setInteger("NumAEGU", numAEGU);
		nbt.setLong("GenerateEmc", generateEmc);

		for (int i = 0; i < coordAEGU[0].length; i++)
		{
			if (coordAEGU[0][i] == null && coordAEGU[1][i] == null &&
				coordAEGU[2][i] == null)
				continue;

			nbt.setString("coordAEGUX" + i, coordAEGU[0][i]);
			nbt.setString("coordAEGUY" + i, coordAEGU[1][i]);
			nbt.setString("coordAEGUZ" + i, coordAEGU[2][i]);
		}

	}

	@Override
	public ItemStack decrStackSize(int slot, int qnt)
	{
		ItemStack stack = inventory[slot];

		if (stack != null)
		{
			if (stack.stackSize <= qnt)
			{
				inventory[slot] = null;
			}
			else
			{
				stack = stack.splitStack(qnt);

				if (stack.stackSize == 0)
				{
					inventory[slot] = null;
				}
			}
		}

		return stack;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack)
	{
		inventory[slot] = stack;

		if (stack != null && stack.stackSize > this.getInventoryStackLimit())
		{
			stack.stackSize = this.getInventoryStackLimit();
		}

		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public ItemStack getStackInSlot(int slot)
	{
		return inventory[slot];
	}

	@Override
	public void openInventory()
	{
		++numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ObjHandlerPEEX.condenserMk3, 1, numPlayersUsing);
	}

	@Override
	public void closeInventory()
	{
		--numPlayersUsing;
		worldObj.addBlockEvent(xCoord, yCoord, zCoord, ObjHandlerPEEX.condenserMk3, 1, numPlayersUsing);
	}

	public int[] outputStorage = new int[] {43, 84};

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
						for (int j = outputStorage[0]; j < outputStorage[1]; j++)
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
				for (int j = outputStorage[0]; j <= outputStorage[1]; j++)
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

	public void storeAEGUCoord(Block block, int x, int y, int z)
	{
		addCoord(x, y, z);
		addGenerateEmc(block, x, y, z);
	}

	public void addGenerateEmc(Block block, int x, int y, int z) {
		BlockAEGUEX aegu = (BlockAEGUEX) block;
		generateEmc += this.generateEmcOfAEGU[aegu.getTier()];
	}

	public boolean destoreAEGUCoord(Block block, int x, int y, int z)
	{
		if (decCoord(x, y, z)) {
			decGenerateEmc(block, x, y, z);
			return true;
		}
		return false;
	}

	public void decGenerateEmc(Block block, int x, int y, int z) {
		BlockAEGUEX aegu = (BlockAEGUEX) block;
		generateEmc -= this.generateEmcOfAEGU[aegu.getTier()];
	}

	public void addCoord(int x, int y, int z)
	{
		int i = 0;
		for(; i < coordAEGU[0].length; i++) {
			if (coordAEGU[0][i] == null || coordAEGU[0][i].equals("")) {
				coordAEGU[0][i] = String.valueOf(x);
				coordAEGU[1][i] = String.valueOf(y);
				coordAEGU[2][i] = String.valueOf(z);
				this.addAEGUNum();
				return;
			}
		}
		if (i == coordAEGU[0].length) {
		}
	}

	public boolean decCoord(int x, int y, int z)
	{
		int num = checkStore(x, y, z);
		if (num != -1) {
			coordAEGU[0][num] = "";
			coordAEGU[1][num] = "";
			coordAEGU[2][num] = "";
			this.decAEGUNum();
			return true;
		}

		return false;
	}

	public void addAEGUNum()
	{
		this.numAEGU++;
		checkNum();
	}

	public void decAEGUNum()
	{
		this.numAEGU--;
		checkNum();
	}

	public void checkNum() {
		if (this.numAEGU < 0 || this.numAEGU > 26)
			System.out.println("AEGUの数が不正です。ソースコードを見直してください。");
	}

	public void checkGenerate() {
		if (this.numAEGU < 0 || this.numAEGU > 26) {}
		else if (this.numAEGU >= 25) {
			if (isGenerate == false && checkCanUseAEGU() == false)
					return;

			changeGenerate(true);
			isGenerate = true;
		} else if (isGenerate == true) {
			changeGenerate(false);
			isGenerate = false;
		}
	}

	/**
	 * 自分(MK2)の周囲のAEGUに既に生成モードになっているものがあった場合
	 * (すでにそのAEGUが他のMK2に使われているということなので)
	 * falseを返す
	 * @return
	 */
	public boolean checkCanUseAEGU() {
		int i = 0;

		for(; i < coordAEGU[0].length; i++) {
			if (coordAEGU[0][i] == null || coordAEGU[0][i].equals("")){continue;}

			int x = Integer.parseInt(coordAEGU[0][i]);
			int y = Integer.parseInt(coordAEGU[1][i]);
			int z = Integer.parseInt(coordAEGU[2][i]);
			Block aegu = this.worldObj.getBlock(x, y, z);
			if (aegu instanceof BlockAEGUEX) {
				if(((BlockAEGUEX) aegu).isGenerate() == true)
					return false;
			}
		}

		return true;
	}

	public void changeGenerate(boolean bool) {
		int i = 0;
		for(; i < coordAEGU[0].length; i++) {
			if (coordAEGU[0][i] != null && !coordAEGU[0][i].equals("")) {
				int x = Integer.parseInt(coordAEGU[0][i]);
				int y = Integer.parseInt(coordAEGU[1][i]);
				int z = Integer.parseInt(coordAEGU[2][i]);
				Block aegu = this.worldObj.getBlock(x, y, z);
				if (aegu instanceof BlockAEGUEX) {
					if (((BlockAEGUEX) aegu).isGenerate() != bool) {
						((BlockAEGUEX) aegu).changeGenerate(worldObj, x, y, z);
					}
				} else {
				}
			}
		}
	}

	/**
	 * そのAEGUの座標が登録されていた場合その座標が登録されている配列の要素番号を、
	 * 登録されていなければ-1を返す
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public int checkStore(int x, int y, int z)
	{
		int i = 0;
		for(; i < coordAEGU[0].length; i++) {
			if (coordAEGU[0][i].equals(String.valueOf(x)) && coordAEGU[1][i].equals(String.valueOf(y)) && coordAEGU[2][i].equals(String.valueOf(z)))
			{
				return i;
			}
		}

		return -1;
	}

	/**
	 * 周囲のAEGUを検索して登録
	 * @param world
	 * @param xCoord
	 * @param yCoord
	 * @param zCoord
	 */
	public void checkAroundAEGU(World world, int xCoord, int yCoord, int zCoord) {
		Block block;
		for (int x = xCoord - 1; x <= xCoord + 1; x++)
			for (int y = yCoord - 1; y <= yCoord + 1; y++)
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					//System.out.println("x "+ x + " y " + y + " z " + z + " block name : " + world.getBlock(x, y, z));
					block = world.getBlock(x, y, z);
					if (block instanceof BlockAEGUEX) {
						this.storeAEGUCoord(block, x, y, z);
					}
				}
	}
}
