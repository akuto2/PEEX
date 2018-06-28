package Akuto2.Blocks;

import Akuto2.TileEntity.TileEntityDirection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockDirection extends Block{
	public BlockDirection(Material material){
		super(material);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase livingBase, ItemStack stack){
		TileEntity tile = world.getTileEntity(x, y, z);
		if((tile instanceof TileEntityDirection)){
			((TileEntityDirection)tile).setRelativeOrientation(livingBase, false);
		}
	}
}
