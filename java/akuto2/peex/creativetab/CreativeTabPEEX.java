package akuto2.peex.creativetab;

import akuto2.peex.ObjHandlerPEEX;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class CreativeTabPEEX extends CreativeTabs {
	public CreativeTabPEEX(String type){
		super(type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(ObjHandlerPEEX.chaosmatterBlock);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel(){
		return "PEEX";
	}
}
