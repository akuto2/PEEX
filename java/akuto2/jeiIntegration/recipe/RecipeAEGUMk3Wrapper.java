package akuto2.jeiIntegration.recipe;

import akuto2.recipes.RecipeAEGUMk3;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;

public class RecipeAEGUMk3Wrapper extends ShapelessRecipeWrapper<RecipeAEGUMk3> implements IShapedCraftingRecipeWrapper{

	public RecipeAEGUMk3Wrapper(IJeiHelpers jeiHelpers, RecipeAEGUMk3 recipe) {
		super(jeiHelpers, recipe);
	}

	@Override
	public int getWidth() {
		return recipe.getRecipeWidth();
	}

	@Override
	public int getHeight() {
		return recipe.getRecipeHeight();
	}

}
