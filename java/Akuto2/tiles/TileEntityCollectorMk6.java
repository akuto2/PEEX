package Akuto2.tiles;

import Akuto2.blocks.BlockAEGUEX;
import Akuto2.utils.Constants;
import net.minecraft.util.math.BlockPos;

public class TileEntityCollectorMk6 extends TileEntityCollectorMk4{
	public TileEntityCollectorMk6() {
		super(Constants.COLLECTOR_MK6_MAX, Constants.COLLECTOR_MK6_GEN);
	}

	public TileEntityCollectorMk6(int maxEmc, int emcGen) {
		super(maxEmc, emcGen);
	}

	@Override
	public int getSunLevel() {
		if(worldObj.getBlockState(new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ())).getBlock() instanceof BlockAEGUEX) {
			return 16;
		}
		return 0;
	}
}
