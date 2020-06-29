package Akuto2.blocks;

import java.util.Random;

import Akuto2.ObjHandlerPEEX;
import Akuto2.PEEXCore;
import Akuto2.tiles.TileEntityCondenserMk3;
import Akuto2.utils.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAEGUEX extends BlockAEGU{

	public BlockAEGUEX(int tier, boolean isGenerate) {
		super(tier, isGenerate);

		setUnlocalizedName("AEGUEX_MK" + tier);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		switch(tier) {
		case 1:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXMk1_off);
		case 2:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXMk2_off);
		case 3:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXMk3_off);
		case 4:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXFinal_off);
		}
		return null;
	}

	@Override
	public void changetGenerate(World world, int x, int y, int z) {
		switch(tier) {
		case 1:
			if(isGenerate) {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXMk1_off.getDefaultState(), 3);
			} else {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXMk1_on.getDefaultState(), 3);
			}
		case 2:
			if(isGenerate) {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXMk2_off.getDefaultState(), 3);
			} else {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXMk2_on.getDefaultState(), 3);
			}
		case 3:
			if(isGenerate) {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXMk3_off.getDefaultState(), 3);
			} else {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXMk3_on.getDefaultState(), 3);
			}
		case 4:
			if(isGenerate) {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXFinal_off.getDefaultState(), 3);
			} else {
				world.setBlockState(new BlockPos(x, y, z), ObjHandlerPEEX.aeguEXFinal_on.getDefaultState(), 3);
			}
		}
	}

	@Override
	public boolean checkCondenser(World world, int xCoord, int yCoord, int zCoord) {
		String xCon = "";
		String yCon = "";
		String zCon = "";

		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					if(world.getBlockState(new BlockPos(x, y, z)).getBlock() instanceof BlockCondenserMk3) {
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

	@Override
	public void notifyToCondenser(World world, int xCoord, int yCoord, int zCoord) {
		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityCondenserMk3) {
						TileEntityCondenserMk3 tile = (TileEntityCondenserMk3)world.getTileEntity(new BlockPos(x, y, z));
						tile.storeAEGUCoord(this, xCoord, yCoord, zCoord);
					}
				}
			}
		}
	}

	@Override
	public boolean notifyBreak(World world, int xCoord, int yCoord, int zCoord) {
		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityCondenserMk3) {
						TileEntityCondenserMk3 tile = (TileEntityCondenserMk3)world.getTileEntity(new BlockPos(x, y, z));
						if(tile.destoreAEGUCoord(this, x, y, z)) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean openCondenserGui(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player) {
		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y <= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityCondenserMk3) {
						TileEntityCondenserMk3 tile = (TileEntityCondenserMk3)world.getTileEntity(new BlockPos(x, y, z));
						if(tile.checkStore(xCoord, yCoord, zCoord) != -1) {
							if(tile.isGenerate)
								player.openGui(PEEXCore.instance, Constants.CONDENSER_MK3_GUI, world, x, y, z);
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
