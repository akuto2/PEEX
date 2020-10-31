package akuto2.peex.blocks;

import akuto2.peex.PEEXCore;
import akuto2.peex.utils.Constants;
import moze_intel.projecte.gameObjs.blocks.Relay;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayFinal;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk4;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk5;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockRelay extends Relay{
	private int tier;

	public BlockRelay(int tier) {
		super(3);
		setUnlocalizedName("relay_Mk" + tier);
		this.tier = tier;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			switch(tier) {
			case 4:
				playerIn.openGui(PEEXCore.instance, Constants.RELAY_MK4_GUI, worldIn, x, y, z);
				break;
			case 5:
				playerIn.openGui(PEEXCore.instance, Constants.RELAY_MK5_GUI, worldIn, x, y, z);
				break;
			case 99:
				playerIn.openGui(PEEXCore.instance, Constants.RELAY_FINAL_GUI, worldIn, x, y, z);
				break;
			}
		}

		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		switch(tier) {
		case 4:
			return new TileEntityRelayMk4();
		case 5:
			return new TileEntityRelayMk5();
		case 99:
			return new TileEntityRelayFinal();
		default:
			return null;
		}
	}
}
