package Akuto2.utils;

import Akuto2.ObjHandlerPEEX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabPEEX extends CreativeTabs {

	public CreativeTabPEEX(String label) {
		super(label);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return new ItemStack(ObjHandlerPEEX.matterBlock, 1, 1).getItem();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return "PEEX";
	}
}
