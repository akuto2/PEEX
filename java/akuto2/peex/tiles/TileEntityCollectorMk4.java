package akuto2.peex.tiles;

import akuto2.peex.blocks.BlockAEGU;
import akuto2.peex.utils.Constants;
import moze_intel.projecte.gameObjs.tiles.CollectorMK1Tile;

public class TileEntityCollectorMk4 extends CollectorMK1Tile{
	public TileEntityCollectorMk4() {
		super(Constants.COLLECTOR_MK4_MAX, Constants.COLLECTOR_MK4_GEN);
	}

	public TileEntityCollectorMk4(int maxEmc, int emcGen) {
		super(maxEmc, emcGen);
	}

	@Override
	protected int getInvSize() {
		return 16;
	}

	@Override
	public int getSunLevel() {
		if(worldObj.getBlockState(getPos().up()).getBlock() instanceof BlockAEGU) {
			return 16;
		}
		return 0;
	}
}
