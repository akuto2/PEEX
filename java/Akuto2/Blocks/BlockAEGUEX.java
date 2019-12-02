package Akuto2.blocks;

import java.util.Random;

import Akuto2.ObjHandlerPEEX;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.IStringSerializable;

public class BlockAEGUEX extends Block{

	public static final PropertyEnum<EnumAEGUEXTier> TIER = PropertyEnum.create("tier", EnumAEGUEXTier.class);
	protected boolean isGenerate;
	String xMK3 = "", yMK3 = "", zMK3 = "";

	public BlockAEGUEX(boolean isGenerate) {
		super(Material.GRASS);
		setUnlocalizedName("AEGUEX");
		setHardness(0.3f);
		setLightLevel(1.0f);
	}

	@Override
	public int damageDropped(IBlockState state) {
		return state.getValue(TIER).getMeta();
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(TIER).getMeta();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(TIER, EnumAEGUEXTier.fromMeta(meta));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { TIER });
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		int meta = state.getValue(TIER).getMeta();
		return new ItemStack(ObjHandlerPEEX.aeguEX_off, 1, meta).getItem();
	}

	public enum EnumAEGUEXTier implements IStringSerializable{
		mk1(0, 1, "mk1", 100000),
		mk2(1, 2, "mk2", 1000000),
		mk3(2, 3, "mk3", 35000000),
		mkFinal(3, 0, "final", 10000000000000000L);

		private static EnumAEGUEXTier[] aeguexTiers;
		private int meta;
		private int tier;
		private String name;
		private long generateEmc;

		static {
			aeguexTiers = new EnumAEGUEXTier[values().length];
			for(EnumAEGUEXTier aeguexTier : values()) {
				aeguexTiers[values().length] = aeguexTier;
			}
		}

		private EnumAEGUEXTier(int meta, int tier, String name, long generateEmc) {
			this.meta = meta;
			this.tier = tier;
			this.name = name;
			this.generateEmc = generateEmc;
		}

		public static EnumAEGUEXTier fromMeta(int meta) {
			if(meta < 0 || meta > values().length) {
				meta = 0;
			}
			return aeguexTiers[meta];
		}

		public int getMeta() {
			return meta;
		}

		public int getTier() {
			return tier;
		}

		public String getName() {
			return name;
		}

		public long getGenerateEmc() {
			return generateEmc;
		}
	}
}
