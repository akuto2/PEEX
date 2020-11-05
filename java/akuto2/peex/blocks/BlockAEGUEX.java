package akuto2.peex.blocks;

import java.util.Random;

import akuto2.peex.ObjHandlerPEEX;
import akuto2.peex.PEEXCore;
import akuto2.peex.tile.TileEntityCondenserMk3;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockAEGUEX extends Block {

	private int tier;

	protected boolean isGenerate;
	String xMK3 = "", yMK3 = "", zMK3 = "";

	public BlockAEGUEX(int tier, boolean isGenerate){
		super(Material.grass);
		setBlockName("AEGUEX_MK" + tier);
		setHardness(0.3F);
		setLightLevel(1.0F);
		setStepSound(soundTypeGlass);

		this.tier = tier;
		this.isGenerate = isGenerate;
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata) {
		if (!world.isRemote) {
			if(this.checkMK3(world, x, y, z) == true) {
				this.notifyToMK3(world, x, y, z);
			}
		}

		return metadata;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int size, float posX, float posY, float posZ)
	{
		if (!world.isRemote)
			openMK3GUI(world, x, y, z, player);
		return isGenerate;
	}

	public void changeGenerate(World world, int x, int y, int z) {
		switch (tier) {
		case 1:
			if (this.isGenerate) {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXMk1_off);
			} else {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXMk1_on);
			}
			break;
		case 2:
			if (this.isGenerate) {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXMk2_off);
			} else {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXMk2_on);
			}
			break;
		case 3:
			if (this.isGenerate) {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXMK3_off);
			} else {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXMK3_on);
			}
			break;
		case 4:
			if (this.isGenerate) {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXFinal_off);
			} else {
				world.setBlock(x, y, z, ObjHandlerPEEX.aeguEXFinal_on);
			}
		}

	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metadata)
	{
		if (!world.isRemote) {
			notifyBreak(world, x, y, z);
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon("peex:aeguex" + this.tier + (isGenerate ? "gene" : ""));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return this.blockIcon;
	}

	@Override
	public Item getItemDropped(int no, Random rnd, int clue)
	{
		switch (tier) {
		case 1:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXMk1_off);
		case 2:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXMk2_off);
		case 3:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXMK3_off);
		case 4:
			return Item.getItemFromBlock(ObjHandlerPEEX.aeguEXFinal_off);
		}
		return null;
	}

	public boolean checkMK3(World world, int xCoord, int yCoord, int zCoord) {
		xMK3 = "";yMK3 = "";zMK3 = "";

		for (int x = xCoord - 1; x <= xCoord + 1; x++) {
			for (int y = yCoord - 1; y <= yCoord + 1; y++) {
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					if (world.getBlock(x, y, z) instanceof BlockCondenserMk3) {
						if (xMK3 == "" && yMK3 == "" && zMK3 == "") {
							xMK3 = String.valueOf(x);
							yMK3 = String.valueOf(y);
							zMK3 = String.valueOf(z);
						} else {
							return false;
						}
					}
				}
			}
		}
		if (xMK3 != "" && yMK3 != "" && zMK3 != "") {
			return true;
		}
		return false;
	}

	public void notifyToMK3(World world, int xCoord, int yCoord, int zCoord)
	{
		int x = Integer.parseInt(xMK3);
		int y = Integer.parseInt(yMK3);
		int z = Integer.parseInt(zMK3);
		if (world.getTileEntity(x, y, z) instanceof TileEntityCondenserMk3) {
			TileEntityCondenserMk3 tile = (TileEntityCondenserMk3)world.getTileEntity(x, y, z);
			tile.storeAEGUCoord(this, xCoord, yCoord, zCoord);
		}
	}

	public boolean notifyBreak(World world, int xCoord, int yCoord, int zCoord) {
		xMK3 = "";yMK3 = "";zMK3 = "";

		for (int x = xCoord - 1; x <= xCoord + 1; x++) {
			for (int y = yCoord - 1; y <= yCoord + 1; y++) {
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					if (world.getBlock(x, y, z) instanceof BlockCondenserMk3 && world.getTileEntity(x, y, z) instanceof TileEntityCondenserMk3) {
						TileEntityCondenserMk3 tile = (TileEntityCondenserMk3)world.getTileEntity(x, y, z);
						if (tile.destoreAEGUCoord(this, xCoord, yCoord, zCoord) == true) {
							return true;
						}
					}
				}
			}
		}
		if (xMK3 != "" && yMK3 != "" && zMK3 != "") {
			return false;
		}
		return false;
	}

	public boolean openMK3GUI(World world, int xCoord, int yCoord, int zCoord, EntityPlayer player) {
		xMK3 = "";yMK3 = "";zMK3 = "";

		for (int x = xCoord - 1; x <= xCoord + 1; x++) {
			for (int y = yCoord - 1; y <= yCoord + 1; y++) {
				for (int z = zCoord - 1; z <= zCoord + 1; z++) {
					if (world.getBlock(x, y, z) instanceof BlockCondenserMk3 && world.getTileEntity(x, y, z) instanceof TileEntityCondenserMk3) {
						TileEntityCondenserMk3 tile = (TileEntityCondenserMk3)world.getTileEntity(x, y, z);
						if (tile.checkStore(xCoord, yCoord, zCoord) != -1) {
							if (tile.isGenerate)
								player.openGui(PEEXCore.instance, 1, world, x, y, z);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public boolean isGenerate() {
		return this.isGenerate;
	}

	public int getTier() {
		return this.tier;
	}
}
