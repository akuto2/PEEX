package Akuto2.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMatter extends ItemBlock{
	public ItemBlockMatter(Block block) {
		super(block);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		if(stack.getItemDamage() == 0) {
			return "tile.bluematterblock";
		}
		else {
			return "tile.chaosmatterblock";
		}
	}

	@Override
	public int getMetadata(int damage) {
		return damage;
	}
}
