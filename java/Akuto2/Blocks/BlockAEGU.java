package Akuto2.blocks;

import java.util.Random;

import Akuto2.ObjHandlerPEEX;
import Akuto2.PEEXCore;
import Akuto2.tiles.TileEntityCondenserMk2PEEX;
import Akuto2.tiles.TileEntityCondenserMk3;
import Akuto2.utils.Constants;
import moze_intel.projecte.PECore;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAEGU extends Block {

	protected int tier;
	protected boolean isGenerate;

	public BlockAEGU(int tier, boolean isGenerate) {
		super(Material.GRASS);
		setUnlocalizedName("AEGU_MK" + tier);
		setHardness(0.3F);
		setLightLevel(1.0F);
		setSoundType(SoundType.GLASS);

		this.tier = tier;
		this.isGenerate = isGenerate;
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		if(!worldIn.isRemote) {
			if(checkCondenser(worldIn, pos.getX(), pos.getY(), pos.getZ())) {
				notifyToCondenser(worldIn, pos.getX(), pos.getY(), pos.getZ());
			}
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		if(!worldIn.isRemote) {
			notifyBreak(worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			openCondenserGui(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn);
		}
		return isGenerate;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		switch(tier) {
		case 1:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguMk1_off);
		case 2:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguMk2_off);
		case 3:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguMk3_off);
		}
		return null;
	}

	public void changeGenerate(World world, int x, int y, int z) {
		switch(tier) {
		case 1:
			if(isGenerate) {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguMk1_off.getDefaultState());
			}
			else {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguMk1_on.getDefaultState());
			}
			break;
		case 2:
			if(isGenerate) {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguMk2_off.getDefaultState());
			}
			else {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguMk2_on.getDefaultState());
			}
			break;
		case 3:
			if(isGenerate) {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguMk3_off.getDefaultState());
			}
			else {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguMk3_on.getDefaultState());
			}
		}
	}

	/**
	 * コンデンサーMk2かMk3が周囲にあるか検索する
	 */
	public boolean checkCondenser(World world, int xCoord, int yCoord, int zCoord) {
		String xCon = "";
		String yCon = "";
		String zCon = "";

		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					if(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockCondenserMk2PEEX) {
						if(xCon == "" && yCon == "" && zCon == "") {
							xCon = String.valueOf(x);
							yCon = String.valueOf(y);
							zCon = String.valueOf(z);
						}
						else {
							return false;
						}
					}
				}
			}
		}
		if(xCon != "" && yCon != "" && zCon != "") {
			return true;
		}
		return false;
	}

	/**
	 * コンデンサーに自身の存在を通知する
	 */
	public void notifyToCondenser(World world, int xCoord, int yCoord, int zCoord) {
		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityCondenserMk2PEEX) {
						TileEntityCondenserMk2PEEX tile = (TileEntityCondenserMk2PEEX)world.getTileEntity(new BlockPos(x, y, z));
						tile.storeAEGUCoord(this, xCoord, yCoord, zCoord);
					}
				}
			}
		}
	}

	/**
	 * 破壊時にコンデンサーMk2かMk3があるか確認し、あった場合は破壊されたことを通知する
	 */
	public boolean notifyBreak(World world, int xCoord, int yCoord, int zCoord) {
			for(int x = xCoord - 1; x <= xCoord + 1; x++) {
				for(int y = yCoord - 1; y <= yCoord + 1; y++) {
					for(int z = zCoord - 1; z <= zCoord + 1; z++) {
						if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityCondenserMk2PEEX) {
							TileEntityCondenserMk2PEEX tile = (TileEntityCondenserMk2PEEX)world.getTileEntity(new BlockPos(x, y, z));
							if(tile.destoreAEGUCoord(this, xCoord, yCoord, zCoord) == true) {
								return true;
							}
						}
					}
				}
			}

		return false;
	}

	public boolean openCondenserGui(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player) {
		boolean isMk3 = false;

		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityCondenserMk2PEEX) {
						TileEntityCondenserMk2PEEX tile = (TileEntityCondenserMk2PEEX)world.getTileEntity(new BlockPos(x, y, z));
						if(tile instanceof TileEntityCondenserMk3)	isMk3 = true;
						if(tile.checkStore(xCoord, yCoord, zCoord) != -1) {
							if(tile.isGenerate) {
								if(isMk3) {
									player.openGui(PEEXCore.instance, Constants.CONDENSER_MK3_GUI, world, x, y, z);
								}
								else {
									player.openGui(PECore.instance, moze_intel.projecte.utils.Constants.CONDENSER_MK2_GUI, world, x, y, z);
								}
							}
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public boolean isGenerate() {
		return isGenerate;
	}

	public int getTier() {
		return tier;
	}
}
