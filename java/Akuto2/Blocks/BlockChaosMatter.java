package Akuto2.Blocks;

import Akuto2.PEEXCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class BlockChaosMatter extends Block {
	@SideOnly(Side.CLIENT)
	private IIcon blockIcon;

	public BlockChaosMatter(){
		super(Material.iron);
		setCreativeTab(PEEXCore.tabPEEX);
		setBlockName("chaosmatterblock");
		setHardness(2000000.0F);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister){
		this.blockIcon = iconRegister.registerIcon("peex:chaosmatterblock");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta){
		return this.blockIcon;
	}
}
