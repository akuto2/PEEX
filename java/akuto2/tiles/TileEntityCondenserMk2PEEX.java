package akuto2.tiles;

import akuto2.blocks.BlockAEGU;
import akuto2.blocks.BlockAEGUEX;
import moze_intel.projecte.gameObjs.blocks.CondenserMK2;
import moze_intel.projecte.gameObjs.blocks.MatterFurnace;
import moze_intel.projecte.gameObjs.blocks.Relay;
import moze_intel.projecte.gameObjs.tiles.CondenserMK2Tile;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityCondenserMk2PEEX extends CondenserMK2Tile{

	protected final int[] generateEmcOfAEGU = {0, 40, 1000, 20000};
	protected String[][] coordAEGU = new String[3][26];
	protected int numAEGU = 0;
	public boolean isGenerate = false;
	long generateEmc = 0;

	@Override
	public void update() {
		super.func_73660_a();

		if(!world.isRemote) {
			// 作成したアイテムを隣接しているインベントリに送る
			if(getWorld().getBlockState(getPos()).getBlock() instanceof CondenserMK2) {
				pushToInventories();
			}
			checkGenerate();

			if(isGenerate) {
				addEMC(generateEmc / 20);
			}
		}
	}

	@Override
	protected void condense() {
		for(int i = 0; i < getInput().getSlots(); i++) {
			ItemStack stack = getInput().getStackInSlot(i);

			if(stack == null || isStackEqualToLock(stack)) {
				continue;
			}

			if(EMCHelper.getEmcValue(stack) * stack.getCount() + getStoredEmc() > getMaximumEmc()) {
				addEMC(EMCHelper.getEmcValue(stack));
				stack.shrink(1);
			}
			else {
				addEMC(EMCHelper.getEmcValue(stack) * stack.getCount());
				getInput().setStackInSlot(i, null);
			}
			break;
		}

		while(hasSpace() && getStoredEmc() >= requiredEmc) {

		}
	}

	@Override
	protected boolean hasSpace() {
		for(int i = 0; i < getOutput().getSlots(); i++) {
			ItemStack stack = getOutput().getStackInSlot(i);
			if(stack == null) {
				return true;
			}

			if(isStackEqualToLock(stack) && stack.getCount() < stack.getMaxStackSize()) {
				return true;
			}
		}

		return false;
	}

	private void pushToInventories() {
		for(EnumFacing face : EnumFacing.VALUES) {
			if(face.getFrontOffsetY() > 0) {
				continue;
			}

			int x = pos.getX() + face.getFrontOffsetX();
			int y = pos.getY() + face.getFrontOffsetY();
			int z = pos.getZ() + face.getFrontOffsetZ();

			TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));

			if(tile == null) {
				continue;
			}

			Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();

			if(block instanceof Relay || block instanceof MatterFurnace) {
				continue;
			}

			IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.UP);

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
				// nullはスロットにアイテムがない
				if(extractTest == null) {
					continue;
				}

				// 相手側に搬入できるかを試す
				ItemStack remainderTest = ItemHandlerHelper.insertItemStacked(handler, extractTest, true);
				int successfullyTransferred = extractTest.getCount() - (remainderTest == null ? 0 : remainderTest.getCount());

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
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		isGenerate = compound.getBoolean("IsGenerate");
		numAEGU = compound.getInteger("NumAEGU");
		generateEmc = compound.getLong("GenerateEmc");

		for(int i = 0; i < coordAEGU[0].length; i++) {
			coordAEGU[0][i] = compound.getString("coordAEGUX" + i);
			coordAEGU[1][i] = compound.getString("coordAEGUY" + i);
			coordAEGU[2][i] = compound.getString("coordAEGUZ" + i);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setBoolean("IsGenerate", isGenerate);
		compound.setInteger("NumAEGU", numAEGU);
		compound.setLong("GenerateEmc", generateEmc);

		for(int i = 0; i < coordAEGU[0].length; i++) {
			if(coordAEGU[0][i] == null && coordAEGU[1][i] == null && coordAEGU[2][i] == null) {
				continue;
			}

			compound.setString("coordAEGUX" + i, coordAEGU[0][i]);
			compound.setString("coordAEGUY" + i, coordAEGU[1][i]);
			compound.setString("coordAEGUZ" + i, coordAEGU[2][i]);
		}

		return compound;
	}

	/**
	 * EMC生産量をAEGUに応じて追加する
	 */
	public void addGenerateEmc(Block block) {
		// AEGUEXは対応しないように
		if(block instanceof BlockAEGUEX) {
			return;
		}
		BlockAEGU aegu = (BlockAEGU)block;
		generateEmc += generateEmcOfAEGU[aegu.getTier()];
	}

	/**
	 * EMC生産量をAEGUに応じて減算する
	 */
	public void decGenerateEmc(Block block) {
		BlockAEGU aegu = (BlockAEGU)block;
		generateEmc -= generateEmcOfAEGU[aegu.getTier()];
	}

	/**
	 * 置かれたAEGUの座標を記録する
	 */
	public void addCoord(int x, int y, int z) {
		for(int i = 0; i < coordAEGU[0].length; i++) {
			if(coordAEGU[0][i] == null || coordAEGU[0][i].equals("")) {
				coordAEGU[0][i] = String.valueOf(x);
				coordAEGU[1][i] = String.valueOf(y);
				coordAEGU[2][i] = String.valueOf(z);
				return;
			}
		}
	}

	/**
	 * 破壊されたAEGUの座標を削除する
	 */
	public boolean decCoord(int x, int y, int z) {
		int num = checkStore(x, y, z);
		if(num != -1) {
			coordAEGU[0][num] = "";
			coordAEGU[1][num] = "";
			coordAEGU[2][num] = "";
			decAEGUNum();
			return true;
		}

		return false;
	}

	public void addAEGUNum() {
		numAEGU++;
	}

	public void decAEGUNum() {
		numAEGU--;
	}

	/**
	 * AEGUを追加する
	 */
	public void storeAEGUCoord(Block block, int x, int y, int z) {
		addCoord(x, y, z);
		addGenerateEmc(block);
	}

	public boolean destoreAEGUCoord(Block block, int x, int y, int z) {
		if(decCoord(x, y, z)) {
			decGenerateEmc(block);
			return true;
		}
		return false;
	}

	/**
	 * 座標がAEGUの配列として登録されていたらその配列の要素番号を返す
	 */
	public int checkStore(int x, int y, int z) {
		for(int i = 0; i < coordAEGU[0].length; i++) {
			if(coordAEGU[0][i].equals(String.valueOf(x)) && coordAEGU[1][i].equals(String.valueOf(y)) && coordAEGU[2][i].equals(String.valueOf(z))) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * EMC生成ができる状態かチェックする
	 */
	public void checkGenerate() {
		if(numAEGU < 0 || numAEGU > 26)	{}
		else if(numAEGU >= 25) {
			if(isGenerate == false && checkCanUseAEGU() == false)
				return;

			changeGenerate(true);
			isGenerate = true;
		}
		else if(isGenerate = true) {
			changeGenerate(false);
			isGenerate = false;
		}
	}

	/**
	 * 周りに置かせているAEGUが既に生成モードになっていないかを調べる
	 */
	public boolean checkCanUseAEGU() {
		for(int i = 0; i < coordAEGU[0].length; i++) {
			if(coordAEGU[0][i] == null || coordAEGU[0][i].equals(""))	continue;

			int x = Integer.parseInt(coordAEGU[0][i]);
			int y = Integer.parseInt(coordAEGU[1][i]);
			int z = Integer.parseInt(coordAEGU[2][i]);
			Block aegu = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			if(aegu instanceof BlockAEGU && !(aegu instanceof BlockAEGUEX)) {
				if(((BlockAEGU)aegu).isGenerate() == true) {
					return false;
				}
			}
		}

		return true;
	}

	public void changeGenerate(boolean generate) {
		for(int i = 0; i < coordAEGU[0].length; i++) {
			if(coordAEGU[0][i] != null && !coordAEGU[0][i].equals("")) {
				int x = Integer.parseInt(coordAEGU[0][i]);
				int y = Integer.parseInt(coordAEGU[1][i]);
				int z = Integer.parseInt(coordAEGU[2][i]);

				Block aegu = world.getBlockState(new BlockPos(x, y, z)).getBlock();
				if(aegu instanceof BlockAEGU && !(aegu instanceof BlockAEGU)) {
					if(((BlockAEGU)aegu).isGenerate() != generate) {
						((BlockAEGU)aegu).changetGenerate(world, x, y, z);
					}
				}
			}
		}
	}

	/**
	 * 周囲のAEGUを検索して登録
	 */
	public void checkAroundAEGU(World world, int xCoord, int yCoord, int zCoord) {
		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
					if(block instanceof BlockAEGU && !(block instanceof BlockAEGUEX)) {
						storeAEGUCoord(block, x, y, z);
					}
				}
			}
		}
	}
}
