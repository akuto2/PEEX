package akuto2.peex.blocks;

import java.util.Random;

import akuto2.peex.ObjHandlerPEEX;
import akuto2.peex.PEEXCore;
import akuto2.peex.tiles.TileEntityCondenserGrade0;
import akuto2.peex.utils.Constants;
import moze_intel.projecte.api.state.PEStateProps;
import moze_intel.projecte.gameObjs.blocks.BlockDirection;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class BlockCondenserGrade0 extends BlockDirection{
	private static final AxisAlignedBB AABB = new AxisAlignedBB(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);

	public BlockCondenserGrade0() {
		super(Material.ROCK);
		setUnlocalizedName("pe_condenser_grade0");
		setDefaultState(blockState.getBaseState().withProperty(PEStateProps.FACING, EnumFacing.NORTH));
		setHardness(10.0F);
		setResistance(6000000.0F);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return AABB;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ObjHandlerPEEX.condenserGrade0);
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			playerIn.openGui(PEEXCore.instance, Constants.CONDENSER_GRADE_0_GUI, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		return false;
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityCondenserGrade0();
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state) {
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile != null) {
			IItemHandler handler = (IItemHandler)tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, (EnumFacing)null);
			return ItemHandlerHelper.calcRedstoneFromInventory(handler);
		}
		else {
			return 0;
		}
	}
}
