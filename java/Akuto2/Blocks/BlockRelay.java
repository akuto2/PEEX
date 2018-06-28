package Akuto2.Blocks;

import Akuto2.PEEXCore;
import Akuto2.TileEntity.TileEntityRelayFinal;
import Akuto2.TileEntity.TileEntityRelayMk4;
import Akuto2.TileEntity.TileEntityRelayMk5;
import Akuto2.Utils.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.gameObjs.blocks.Relay;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockRelay extends Relay {
	@SideOnly(Side.CLIENT)
	public IIcon top;
	@SideOnly(Side.CLIENT)
	public IIcon front;
	public int tier;

	public BlockRelay(int tier){
		super(3);
		setBlockName("relay_Mk" + tier);
		setCreativeTab(PEEXCore.tabPEEX);
		this.tier = tier;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entity, int side, float hitX, float hitY, float hitZ){
		if(!world.isRemote){
			switch(tier){
			case 4:
				entity.openGui(PEEXCore.instance, Constants.RELAY_MK4_GUI, world, x, y, z);
				break;
			case 5:
				entity.openGui(PEEXCore.instance, Constants.RELAY_MK5_GUI, world, x, y, z);
				break;
			case 99:
				entity.openGui(PEEXCore.instance, Constants.RELAY_FINAL_GUI, world, x, y, z);
				break;
			}
		}
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, int meta){
		if(tier == 4){
			return new TileEntityRelayMk4();
		}
		if(tier == 5){
			return new TileEntityRelayMk5();
		}
		if(tier == 99){
			return new TileEntityRelayFinal();
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister register){
		this.blockIcon = register.registerIcon("peex:relays/relay_Mk" + Integer.toString(tier));
		this.front = register.registerIcon("peex:relays/relay_front");
		this.top = register.registerIcon("peex:relays/relay_top_Mk" + Integer.toString(tier));
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
