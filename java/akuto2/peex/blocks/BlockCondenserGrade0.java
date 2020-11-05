package akuto2.peex.blocks;

import java.util.Random;

import akuto2.peex.ObjHandlerPEEX;
import akuto2.peex.PEEXCore;
import akuto2.peex.tile.TileEntityCondenserGrade0;
import akuto2.peex.utils.Constants;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockCondenserGrade0 extends BlockDirection{
	int x, y, z;

	public BlockCondenserGrade0() {
		super(Material.rock);
		setBlockName("pe_condenser_grade0");
		setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		setHardness(10.0F);
		setResistance(6000000.0F);
	}

	@Override
	public Item getItemDropped(int par1, Random random, int par2){
		return Item.getItemFromBlock(ObjHandlerPEEX.condenserGrade0);
	}

	@Override
	public boolean renderAsNormalBlock(){
		return false;
	}

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public int getRenderType(){
		return Constants.CONDENSER_GRADE0_RENDER_ID;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ){
		if (!world.isRemote)
		{
			player.openGui(PEEXCore.instance, Constants.CONDENSER_GRADE0_GUI, world, x, y, z);
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
	public TileEntity createTileEntity(World var1, int var2)
	{
		return new TileEntityCondenserGrade0();
	}

	@Override
	public boolean hasTileEntity(int meta){
		return true;
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

		world.func_147453_f(x, y, z, block);
		world.removeTileEntity(x, y, z);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister register)
	{
		this.blockIcon = register.registerIcon("obsidian");
	}

	@Override
	public boolean hasComparatorInputOverride(){
		return true;
	}

	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int meta){
		return Container.calcRedstoneFromInventory((TileEntityCondenserGrade0)world.getTileEntity(x, y, z));
	}
}
