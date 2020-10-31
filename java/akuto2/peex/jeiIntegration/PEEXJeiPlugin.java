package akuto2.peex.jeiIntegration;

import java.util.ArrayList;

import akuto2.peex.jeiIntegration.recipe.RecipeAEGUMk3Wrapper;
import akuto2.peex.recipes.RecipeAEGUMk3;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.crafting.IRecipe;

@JEIPlugin
public class PEEXJeiPlugin implements IModPlugin{
	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		registry.addRecipes(new ArrayList<IRecipe>(){}, "minecraft.crafting");
		registry.handleRecipes(RecipeAEGUMk3.class, recipe -> new RecipeAEGUMk3Wrapper(jeiHelpers, recipe), "minecraft.crafting");
	}
}
