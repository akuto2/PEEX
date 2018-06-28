package Akuto2.Blocks;

import java.util.Random;

import Akuto2.ObjHandlerPEEX;
import Akuto2.PEEXCore;
import Akuto2.TileEntity.TileEntityCondenserMk3;
import Akuto2.Utils.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.gameObjs.blocks.CondenserMK2;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCondenserMk3 extends CondenserMK2 {
	int x,y,z;

	public BlockCondenserMk3()
	{
		super();
		this.setBlockName("pe_condenser_mk3");
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par2)
	{
		return Item.getItemFromBlock(ObjHandlerPEEX.condenserMk3);
	}

	@Override
	public int getRenderType()
	{
		return Constants.CONDENSER_MK3_RENDER_ID;
	}

	@Override
	public TileEntity createTileEntity(World var1, int var2)
	{
		TileEntity tile = new TileEntityCondenserMk3();
		if (!var1.isRemote) {
			((TileEntityCondenserMk3) tile).checkAroundAEGU(var1, x, y, z);
		}

		return tile;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			player.openGui(PEEXCore.instance, Constants.CONDENSER_MK3_GUI, world, x, y, z);
		}

		return true;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		this.x = x;
		this.y = y;
		this.z = z;

		return metadata;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int noclue)
	{
		IInventory tile = (IInventory) world.getTileEntity(x, y, z);

		if (tile == null)
		{
			return;
		}

		for (int i = 1; i < tile.getSizeInventory(); i++)
		{
			ItemStack stack = tile.getStackInSlot(i);

			if (stack == null)
			{
				continue;
			}

			WorldHelper.spawnEntityItem(world, stack, x, y, z);
		}

		if (!world.isRemote) {
			if(tile instanceof TileEntityCondenserMk3) {
					if (((TileEntityCondenserMk3)tile).isGenerate) {
						((TileEntityCondenserMk3)tile).changeGenerate(false);
					}
			}
		}

		world.func_147453_f(x, y, z, block);
		world.removeTileEntity(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("obsidian");
	}
}
