package Akuto2;

import Akuto2.blocks.BlockAEGU;
import Akuto2.blocks.BlockAEGUEX;
import Akuto2.blocks.BlockCollector;
import Akuto2.blocks.BlockCondenserMk3;
import Akuto2.recipes.RecipeAEGUMk3;
import Akuto2.tiles.TileEntityCollectorMk4;
import Akuto2.tiles.TileEntityCollectorMk5;
import Akuto2.utils.PEEXConfig;
import lib.utils.Register;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.RecipeSorter;
import net.minecraftforge.oredict.RecipeSorter.Category;

public class ObjHandlerPEEX {

	// PEAAで追加されていたもの
	public static Block aeguMk1_off;
	public static Block aeguMk1_on;
	public static Block aeguMk2_off;
	public static Block aeguMk2_on;
	public static Block aeguMk3_off;
	public static Block aeguMk3_on;
	public static Block collectorMk4;
	public static Block collectorMk5;

	public static Block aeguEXMk1_off;
	public static Block aeguEXMk1_on;
	public static Block aeguEXMk2_off;
	public static Block aeguEXMk2_on;
	public static Block aeguEXMk3_off;
	public static Block aeguEXMk3_on;
	public static Block aeguEXFinal_off;
	public static Block aeguEXFinal_on;
	public static Block condenserMk3;
	public static Block condenserGrade0;
	public static Block collectorMk6;
	public static Block collectorMk7;
	public static Block collectorMk8;
	public static Block collectorMk9;
	public static Block collectorMk10;
	public static Block collectorFinal;
	public static Block relayMk4;
	public static Block relayMk5;
	public static Block relayFinal;
	public static Block matterBlock;
	public static Item matter;

	public static Register register = new Register("peex", PEEXCore.peexTab);


	/**
	 * アイテムとブロック登録
	 */
	public static void register() {
		// PEAAで追加されていたもの
		aeguMk1_off = new BlockAEGU(1, false);
		aeguMk1_on = new BlockAEGU(1, true);
		aeguMk2_off = new BlockAEGU(2, false);
		aeguMk2_on = new BlockAEGU(2, true);
		aeguMk3_off = new BlockAEGU(3, false);
		aeguMk3_on = new BlockAEGU(3, true);
		collectorMk4 = new BlockCollector(4);
		collectorMk5 = new BlockCollector(5);

		// PEEX
		aeguEXMk1_off = new BlockAEGUEX(1, false);
		aeguEXMk1_on = new BlockAEGUEX(1, true);
		aeguEXMk2_off = new BlockAEGUEX(2, false);
		aeguEXMk2_on = new BlockAEGUEX(2, true);
		aeguEXMk3_off = new BlockAEGUEX(3, false);
		aeguEXMk3_on = new BlockAEGUEX(3, true);
		condenserMk3 = new BlockCondenserMk3();
		collectorMk6 = new BlockCollector(6);
		collectorMk7 = new BlockCollector(7);
		collectorMk8 = new BlockCollector(8);
		collectorMk9 = new BlockCollector(9);
		collectorMk10 = new BlockCollector(10);
		collectorFinal = new BlockCollector(99);

		register.register(aeguMk1_off, new ItemBlock(aeguMk1_off), "aegu/aegumk1_off");
		register.register(aeguMk1_on, new ItemBlock(aeguMk1_on), "aegu/aegumk1_on", false);
		register.register(aeguMk2_off, new ItemBlock(aeguMk2_off), "aegu/aegumk2_off");
		register.register(aeguMk2_on, new ItemBlock(aeguMk2_on), "aegu/aegumk2_on", false);
		register.register(aeguMk3_off, new ItemBlock(aeguMk3_off), "aegu/aegumk3_off");
		register.register(aeguMk3_on, new ItemBlock(aeguMk3_on), "aegu/aegumk3_on", false);
		register.register(collectorMk4, new ItemBlock(collectorMk4), "collectors/collectormk4");
		register.register(collectorMk5, new ItemBlock(collectorMk5), "collectors/collectormk5");

		GameRegistry.registerTileEntity(TileEntityCollectorMk4.class, "CollectorMk4Tile");
		GameRegistry.registerTileEntity(TileEntityCollectorMk5.class, "CollectorMk5Tile");
	}

	/**
	 * レシピ追加
	 */
	public static void addRecipes() {
		// PEAAで追加されていたもの
		// Collector
		GameRegistry.addRecipe(new ItemStack(collectorMk4), "ccc", "cmc", "ccc", 'c', ObjHandler.collectorMK3, 'm', new ItemStack(ObjHandler.matter, 1, 1));
		GameRegistry.addRecipe(new ItemStack(collectorMk5), "ccc", "cmc", "ccc", 'c', collectorMk4, 'm', new ItemStack(ObjHandler.matter, 1, 1));

		// AEGU
		GameRegistry.addRecipe(new ItemStack(aeguMk1_off), "ccc", "cpc", "ccc", 'c', ObjHandler.collectorMK3, 'p', ObjHandler.dmPedestal);
		GameRegistry.addRecipe(new ItemStack(aeguMk2_off), "aaa", "asa", "aaa", 'a', aeguMk1_off, 's', new ItemStack(ObjHandler.kleinStars, 1, 4));
		GameRegistry.addRecipe(new RecipeAEGUMk3());
		RecipeSorter.register("Ultimate AEGU Recipes", RecipeAEGUMk3.class, Category.SHAPED, "");

		if(PEEXConfig.isFinalType) {

		}
		if(PEEXConfig.isGradeZero) {

		}
	}
}
