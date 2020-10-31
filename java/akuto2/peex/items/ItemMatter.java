package akuto2.peex.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemMatter extends Item{
	private final String[] names = new String[] { "bluematter", "chaosmatter" };

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
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if(isInCreativeTab(tab)) {
			for(int i = 0; i < 2; i++) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
}
