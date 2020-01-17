package Akuto2.recipes;

import Akuto2.ObjHandlerPEEX;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.gameObjs.items.KleinStar;
import moze_intel.projecte.utils.EMCHelper;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

public class RecipeAEGUMk3 extends ShapedRecipes{

	public static final int recipeWidth = 3;
	public static final int recipeHeight = 3;
	public final ItemStack[] recipeItems;
	private static ItemStack output = new ItemStack(ObjHandlerPEEX.aeguMk3_off);

	private static final ItemStack fullKleinOmega = new ItemStack(ObjHandler.kleinStars, 1, 5);
	private static ItemStack mk2 = new ItemStack(ObjHandlerPEEX.aeguMk2_off);
	private static ItemStack[] stack = {mk2, mk2, mk2, mk2, fullKleinOmega, mk2, mk2, mk2, mk2};

	public RecipeAEGUMk3() {
		super(recipeWidth, recipeHeight, stack, output);
		KleinStar.setEmc(fullKleinOmega, EMCHelper.getKleinStarMaxEmc(fullKleinOmega));
		recipeItems = stack;
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
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		return getRecipeOutput().copy();
	}

	@Override
	public int getRecipeSize() {
		return recipeWidth * recipeHeight;
	}

	@Override
	public ItemStack getRecipeOutput() {
		return output;
	}

	public static ItemStack getKleinStack() {
		if(KleinStar.getEmc(fullKleinOmega) != EMCHelper.getKleinStarMaxEmc(fullKleinOmega)) {
			KleinStar.setEmc(fullKleinOmega, EMCHelper.getKleinStarMaxEmc(fullKleinOmega));
		}

		return fullKleinOmega;
	}
}
