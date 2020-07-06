package akuto2.utils;

import akuto2.ObjHandlerPEEX;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabPEEX extends CreativeTabs{

	public CreativeTabPEEX(String label) {
		super(label);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(ObjHandlerPEEX.matterBlock, 1, 1);
	}

	@Override
	public String getTranslatedTabLabel() {
		return "PEEX";
	}
}
