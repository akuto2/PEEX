package Akuto2.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemMatter extends Item {
	private final String[] names = new String[] {"bluematter", "chaosmatter"};

	public ItemMatter() {
		setUnlocalizedName("matter");
		setHasSubtypes(true);
		setMaxDamage(0);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "item." + names[stack.getItemDamage()];
	}

	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for(int i = 0; i < 2; i++) {
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}
}
