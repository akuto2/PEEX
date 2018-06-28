package Akuto2.Blocks;

import java.util.Random;

import Akuto2.ObjHandlerPEEX;
import Akuto2.PEEXCore;
import Akuto2.Utils.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.gameObjs.blocks.TransmutationStone;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockTransmutaionStoneMk2 extends TransmutationStone {
	@SideOnly(Side.CLIENT)
	public IIcon[] icon;

	public BlockTransmutaionStoneMk2(){
		setCreativeTab(PEEXCore.tabPEEX);
		setBlockName("transmutation_stone_peex");
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par2) {
		return Item.getItemFromBlock(ObjHandlerPEEX.transmutationStoneMk2);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			player.openGui(PEEXCore.instance, Constants.TRANSMUTATIONSTONE_Mk2_GUI, world, x, y, z);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		if(side < 2) {
			return icon[side];
		}
		return icon[2];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register) {
		icon = new IIcon[3];
		icon[0] = register.registerIcon("peex:transmutation_stone_peex/bottom");
		icon[1] = register.registerIcon("peex:transmutation_stone_peex/top");
		icon[2] = register.registerIcon("peex:transmutation_stone_peex/side");
	}
}
