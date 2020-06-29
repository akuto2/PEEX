package akuto2.recipes;

import akuto2.ObjHandlerPEEX;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.gameObjs.items.KleinStar;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IShapedRecipe;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * AEGUMK3にフル充電のクラインの星オメガが必要なためそれを使えるようにするクラス
 */
public class RecipeAEGUMk3 extends IForgeRegistryEntry.Impl<IRecipe> implements IShapedRecipe{
	public static final int recipeWidth = 3;
	public static final int recipeHeight = 3;
	private static ItemStack output = new ItemStack(ObjHandlerPEEX.aeguMk3_off);
	private static final ItemStack fullKleinOmega = new ItemStack(ObjHandler.kleinStars, 1, 5);
	private static ItemStack mk2 = new ItemStack(ObjHandlerPEEX.aeguMk2_off);
	private static NonNullList<Ingredient> ingredients = NonNullList.create();
	private static ItemStack[] stacks = {mk2, mk2, mk2, mk2, fullKleinOmega, mk2, mk2, mk2, mk2};

	public RecipeAEGUMk3() {
		KleinStar.setEmc(fullKleinOmega, EMCHelper.getKleinStarMaxEmc(fullKleinOmega));
		setIngredient();
		setRegistryName(new ResourceLocation("peex", "aegumk3"));
	}


	/**
	 * 配列にあるItemStackをIngredientに変換してListに入れる
	 */
	private void setIngredient() {
		for(ItemStack stack : stacks) {
			Ingredient ingredient = CraftingHelper.getIngredient(stack);
			ingredients.add(ingredient);
		}
	}

	@Override
	public boolean matches(InventoryCrafting inv, World worldIn) {
		if(inv.getSizeInventory() < 9)	return false;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			ItemStack input = inv.getStackInSlot(i);
			if(input == null)	return false;
			if(i == 4) {
				if(!(ItemStack.areItemStacksEqual(input, fullKleinOmega) && ItemStack.areItemStackTagsEqual(input, fullKleinOmega))) {
					return false;
				}
			}
			else if(!(ItemStack.areItemsEqual(input, mk2))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean canFit(int width, int height) {
		return width >= recipeWidth && height >= recipeHeight;
	}

	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return getRecipeOutput().copy();
	}

	@Override
	public int getRecipeWidth() {
		return recipeWidth;
	}

	@Override
	public int getRecipeHeight() {
		return recipeHeight;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	@Override
	public NonNullList<Ingredient> getIngredients() {
		return ingredients;
	}
}
