package Akuto2.blocks;

import java.util.Random;

import Akuto2.ObjHandlerPEEX;
import Akuto2.tiles.TileEntityCondenserMk3;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCondenserMk3 extends BlockCondenserMk2PEEX {
	public BlockCondenserMk3() {
		setUnlocalizedName("pe_condenser_mk3");
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ObjHandlerPEEX.condenserMk3);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCondenserMk3();
	}


}
