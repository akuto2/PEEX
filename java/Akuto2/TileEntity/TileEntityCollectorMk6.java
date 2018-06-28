package Akuto2.TileEntity;

import Akuto2.Blocks.BlockAEGUEX;
import moze_intel.projecte.gameObjs.tiles.CollectorMK1Tile;

public class TileEntityCollectorMk6 extends CollectorMK1Tile {
	public TileEntityCollectorMk6(){
		super(60000, 5120, 17, 18);
	}

	public TileEntityCollectorMk6(int maxEmc, int emcGen, int upgradedSlot, int lockSlot)
	{
		super(maxEmc, emcGen, upgradedSlot, lockSlot);
	}

	@Override
	public int getSunLevel()
	{
		if ((worldObj.getBlock(xCoord, yCoord + 1, zCoord) instanceof BlockAEGUEX))
		{
			return 16;
		}
		return 0;
	}
}
