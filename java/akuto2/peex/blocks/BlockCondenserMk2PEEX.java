package akuto2.peex.blocks;

import akuto2.peex.tiles.TileEntityCondenserMk2PEEX;
import moze_intel.projecte.gameObjs.blocks.CondenserMK2;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCondenserMk2PEEX extends CondenserMK2{
	protected int x, y, z;

	public BlockCondenserMk2PEEX() {
		super();
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		TileEntity tile = new TileEntityCondenserMk2PEEX();

		if(!world.isRemote) {
			((TileEntityCondenserMk2PEEX)tile).checkAroundAEGU(world, x, y, z);
		}

		return tile;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(!worldIn.isRemote) {
			if(tile instanceof TileEntityCondenserMk2PEEX) {
				if(((TileEntityCondenserMk2PEEX)tile).isGenerate) {
					((TileEntityCondenserMk2PEEX)tile).changeGenerate(false);
				}
			}
		}
		super.breakBlock(worldIn, pos, state);
	}
}
