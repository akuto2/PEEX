package akuto2.peex.tiles;

import akuto2.peex.blocks.BlockAEGU;
import akuto2.peex.blocks.BlockAEGUEX;
import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityCondenserMk3 extends TileEntityCondenserMk2PEEX{

	private final long[] generateEmcOfAEGUEX = { 0, 100000, 1000000, 35000000, 10000000000000000L };

	@Override
	public void addGenerateEmc(Block block) {
		BlockAEGU aegu = (BlockAEGU)block;
		if(aegu instanceof BlockAEGUEX) {
			generateEmc += generateEmcOfAEGUEX[aegu.getTier()];
		}
		else {
			generateEmc += generateEmcOfAEGU[aegu.getTier()];
		}
	}

	@Override
	public void decGenerateEmc(Block block) {
		BlockAEGU aegu = (BlockAEGU)block;
		if(aegu instanceof BlockAEGUEX) {
			generateEmc -= generateEmcOfAEGUEX[aegu.getTier()];
		}
		else {
			generateEmc -= generateEmcOfAEGU[aegu.getTier()];
		}
	}

	@Override
	public boolean checkCanUseAEGU() {
		for(int i = 0; i < coordAEGU[0].length; i++) {
			if(coordAEGU[0][i] == null || coordAEGU[0][i].equals(""))	continue;

			int x = Integer.parseInt(coordAEGU[0][i]);
			int y = Integer.parseInt(coordAEGU[1][i]);
			int z = Integer.parseInt(coordAEGU[2][i]);
			Block aegu = world.getBlockState(new BlockPos(x, y, z)).getBlock();
			if(aegu instanceof BlockAEGU) {
				if(((BlockAEGU)aegu).isGenerate() == true)
					return false;
			}
 		}

		return true;
	}

	@Override
	public void changeGenerate(boolean generate) {
		for(int i = 0; i < coordAEGU[0].length; i++) {
			if(coordAEGU[0][i] != null && !coordAEGU[0][i].equals("")) {
				int x = Integer.parseInt(coordAEGU[0][i]);
				int y = Integer.parseInt(coordAEGU[1][i]);
				int z = Integer.parseInt(coordAEGU[2][i]);
				Block aegu = world.getBlockState(new BlockPos(x, y, z)).getBlock();
				if(aegu instanceof BlockAEGU) {
					if(((BlockAEGU)aegu).isGenerate() != generate) {
						((BlockAEGU)aegu).changetGenerate(world, x, y, z);
					}
				}
			}
		}
	}

	@Override
	public void checkAroundAEGU(World world, int xCoord, int yCoord, int zCoord) {
		for(int x = xCoord - 1; x <= xCoord + 1; x++) {
			for(int y = yCoord - 1; y<= yCoord + 1; y++) {
				for(int z = zCoord - 1; z <= zCoord + 1; z++) {
					Block block = world.getBlockState(new BlockPos(x, y, z)).getBlock();
					if(block instanceof BlockAEGU) {
						storeAEGUCoord(block, x, y, z);
					}
				}
			}
		}
	}
}
