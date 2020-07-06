package akuto2.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockMatter extends Block{
	public static final PropertyEnum<EnumMatterType> TIER = PropertyEnum.create("tier", EnumMatterType.class);

	public BlockMatter() {
		super(Material.IRON);
		setUnlocalizedName("matterblock");
		setDefaultState(blockState.getBaseState().withProperty(TIER, EnumMatterType.BLUE_MATTER));
	}

	@Override
	public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
		EnumMatterType type = blockState.getValue(TIER);

		if(type == EnumMatterType.BLUE_MATTER) {
			return 1000000.0F;
		}
		else {
			return 2000000.0F;
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return getMetaFromState(state);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TIER).ordinal();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TIER, EnumMatterType.values()[meta]);
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, TIER);
	}

	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
		for(int i = 0; i < 2; i++) {
			items.add(new ItemStack(this, 1, i));
		}
	}

	public enum EnumMatterType implements IStringSerializable{
		BLUE_MATTER("bluematter"),
		CHAOS_MATTER("chaosmatter");

		private final String name;

		EnumMatterType(String name){
			this.name = name;
		}

		@Override
		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
