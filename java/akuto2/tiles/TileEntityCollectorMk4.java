package akuto2.tiles;

import akuto2.blocks.BlockAEGU;
import akuto2.utils.Constants;
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
		if(world.getBlockState(getPos().up()).getBlock() instanceof BlockAEGU) {
			return 16;
		}
		return 0;
	}
}
