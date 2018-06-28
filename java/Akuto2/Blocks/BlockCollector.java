package Akuto2.Blocks;

import Akuto2.PEEXCore;
import Akuto2.TileEntity.TileEntityCollectorFinal;
import Akuto2.TileEntity.TileEntityCollectorMk10;
import Akuto2.TileEntity.TileEntityCollectorMk6;
import Akuto2.TileEntity.TileEntityCollectorMk7;
import Akuto2.TileEntity.TileEntityCollectorMk8;
import Akuto2.TileEntity.TileEntityCollectorMk9;
import Akuto2.Utils.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.gameObjs.blocks.Collector;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockCollector extends Collector {
	@SideOnly(Side.CLIENT)
	public IIcon top;
	@SideOnly(Side.CLIENT)
	public IIcon front;
	public int tier;

	public BlockCollector(int tier){
		super(3);
		setBlockName("collector_Mk" + tier);
		setCreativeTab(PEEXCore.tabPEEX);
		this.tier = tier;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entity, int side, float hitX, float hitY, float hitZ){
		if(!world.isRemote){
			switch(tier){
			case 6:
				entity.openGui(PEEXCore.instance, Constants.COLLECTOR_MK6_GUI, world, x, y, z);
				break;
			case 7:
				entity.openGui(PEEXCore.instance, Constants.COLLECTOR_MK7_GUI, world, x, y, z);
				break;
			case 8:
				entity.openGui(PEEXCore.instance, Constants.COLLECTOR_MK8_GUI, world, x, y, z);
				break;
			case 9:
				entity.openGui(PEEXCore.instance, Constants.COLLECTOR_MK9_GUI, world, x, y, z);
				break;
			case 10:
				entity.openGui(PEEXCore.instance, Constants.COLLECTOR_MK10_GUI, world, x, y, z);
				break;
			case 99:
				entity.openGui(PEEXCore.instance, Constants.COLLECTOR_FINAL_GUI, world, x, y, z);
			}

		}
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int var2){
		if(tier == 6){
			return new TileEntityCollectorMk6();
		}
		if(tier == 7){
			return new TileEntityCollectorMk7();
		}
		if(tier == 8){
			return new TileEntityCollectorMk8();
		}
		if(tier == 9){
			return new TileEntityCollectorMk9();
		}
		if(tier == 10){
			return new TileEntityCollectorMk10();
		}
		if(tier == 99){
			return new TileEntityCollectorFinal();
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register){
		this.blockIcon = register.registerIcon("peex:collectors/collector_other_" + Integer.toString(this.tier));
		this.front = register.registerIcon("peex:collectors/collector_front");
		this.top = register.registerIcon("peex:collectors/collector_top_Mk" + Integer.toString(tier));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (meta == 0 && side == 3)
		{
			return front;
		}

		if (side == 1)
		{
			return top;
		}

		return side != meta ? this.blockIcon : front;
	}
}
