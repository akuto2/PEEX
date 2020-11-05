package akuto2.peex.blocks;

import akuto2.peex.PEEXCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockBlueMatter extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon icon;

	public BlockBlueMatter() {
		super(Material.iron);
		setCreativeTab(PEEXCore.tabPEEX);
		setBlockName("bluematterblock");
		setHardness(1000000.0F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister){
		this.blockIcon = iconRegister.registerIcon("peex:bluematterblock");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta){
		return this.blockIcon;
	}
}
