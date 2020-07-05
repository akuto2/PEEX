package akuto2.blocks;

import java.util.Random;

import akuto2.ObjHandlerPEEX;
import akuto2.PEEXCore;
import akuto2.tiles.TileEntityCondenserMk3;
import akuto2.utils.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCondenserMk3 extends BlockCondenserMk2PEEX{
	public BlockCondenserMk3() {
		setUnlocalizedName("pe_condenser_mk3");
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ObjHandlerPEEX.condenserMk3);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		TileEntity tile = new TileEntityCondenserMk3();

		if(!world.isRemote) {
			((TileEntityCondenserMk3)tile).checkAroundAEGU(world, x, y, z);
		}

		return tile;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			playerIn.openGui(PEEXCore.instance, Constants.CONDENSER_MK3_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}
}
