package akuto2.peex.blocks;

import akuto2.peex.PEEXCore;
import akuto2.peex.tiles.TileEntityCollectorFinal;
import akuto2.peex.tiles.TileEntityCollectorMk10;
import akuto2.peex.tiles.TileEntityCollectorMk4;
import akuto2.peex.tiles.TileEntityCollectorMk5;
import akuto2.peex.tiles.TileEntityCollectorMk6;
import akuto2.peex.tiles.TileEntityCollectorMk7;
import akuto2.peex.tiles.TileEntityCollectorMk8;
import akuto2.peex.tiles.TileEntityCollectorMk9;
import akuto2.peex.utils.Constants;
import moze_intel.projecte.gameObjs.blocks.Collector;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCollector extends Collector {

	public int tier;

	public BlockCollector(int tier) {
		super(3);
		setUnlocalizedName("collector_Mk" + tier);
		this.tier = tier;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			switch(tier) {
			case 4:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_MK4_GUI, worldIn, x, y, z);
				break;
			case 5:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_MK5_GUI, worldIn, x, y, z);
				break;
			case 6:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_MK6_GUI, worldIn, x, y, z);
				break;
			case 7:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_MK7_GUI, worldIn, x, y, z);
				break;
			case 8:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_MK8_GUI, worldIn, x, y, z);
				break;
			case 9:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_MK9_GUI, worldIn, x, y, z);
				break;
			case 10:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_MK10_GUI, worldIn, x, y, z);
				break;
			case 99:
				playerIn.openGui(PEEXCore.instance, Constants.COLLECTOR_FINAL_GUI, worldIn, x, y, z);
				break;
			}
		}
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch(tier) {
		case 4:
			return new TileEntityCollectorMk4();
		case 5:
			return new TileEntityCollectorMk5();
		case 6:
			return new TileEntityCollectorMk6();
		case 7:
			return new TileEntityCollectorMk7();
		case 8:
			return new TileEntityCollectorMk8();
		case 9:
			return new TileEntityCollectorMk9();
		case 10:
			return new TileEntityCollectorMk10();
		case 99:
			return new TileEntityCollectorFinal();
		default:
			return null;
		}
	}
}
