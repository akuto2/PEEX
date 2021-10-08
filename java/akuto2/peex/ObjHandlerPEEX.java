package akuto2.peex;

import akuto2.peex.blocks.BlockAEGU;
import akuto2.peex.blocks.BlockAEGUEX;
import akuto2.peex.blocks.BlockCollector;
import akuto2.peex.blocks.BlockCondenserGrade0;
import akuto2.peex.blocks.BlockCondenserMk3;
import akuto2.peex.blocks.BlockMatter;
import akuto2.peex.blocks.BlockRelay;
import akuto2.peex.items.ItemBlockMatter;
import akuto2.peex.items.ItemMatter;
import akuto2.peex.items.armor.ItemBMArmor;
import akuto2.peex.items.armor.ItemCMArmor;
import akuto2.peex.recipes.RecipeAEGUMk3;
import akuto2.peex.tiles.TileEntityCollectorFinal;
import akuto2.peex.tiles.TileEntityCollectorMk10;
import akuto2.peex.tiles.TileEntityCollectorMk4;
import akuto2.peex.tiles.TileEntityCollectorMk5;
import akuto2.peex.tiles.TileEntityCollectorMk6;
import akuto2.peex.tiles.TileEntityCollectorMk7;
import akuto2.peex.tiles.TileEntityCollectorMk8;
import akuto2.peex.tiles.TileEntityCollectorMk9;
import akuto2.peex.tiles.TileEntityCondenserGrade0;
import akuto2.peex.tiles.TileEntityCondenserMk3;
import akuto2.peex.utils.PEEXConfig;
import lib.utils.RecipeManager;
import lib.utils.Register;
import moze_intel.projecte.gameObjs.ObjHandler;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayFinal;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk4;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk5;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
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

	public static Item bmHelmet;
	public static Item bmChest;
	public static Item bmLegs;
	public static Item bmFeet;

	public static Item cmHelmet;
	public static Item cmChest;
	public static Item cmLegs;
	public static Item cmFeet;

	public static ArmorMaterial bmMaterial;
	public static ArmorMaterial cmMaterial;

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

		// PEEX
		bmMaterial = EnumHelper.addArmorMaterial("bm_material", "", 9999, new int[] { 8, 12, 10, 6 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F);
		cmMaterial = EnumHelper.addArmorMaterial("cm_material", "", 9999, new int[] { 10, 15, 13, 8 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0F);
		aeguEXMk1_off = new BlockAEGUEX(1, false);
		aeguEXMk1_on = new BlockAEGUEX(1, true);
		aeguEXMk2_off = new BlockAEGUEX(2, false);
		aeguEXMk2_on = new BlockAEGUEX(2, true);
		aeguEXMk3_off = new BlockAEGUEX(3, false);
		aeguEXMk3_on = new BlockAEGUEX(3, true);
		aeguEXFinal_off = new BlockAEGUEX(4, false);
		aeguEXFinal_on = new BlockAEGUEX(4, true);
		condenserMk3 = new BlockCondenserMk3();
		condenserGrade0 = new BlockCondenserGrade0();
		collectorMk6 = new BlockCollector(6);
		collectorMk7 = new BlockCollector(7);
		collectorMk8 = new BlockCollector(8);
		collectorMk9 = new BlockCollector(9);
		collectorMk10 = new BlockCollector(10);
		collectorFinal = new BlockCollector(99);
		relayMk4 = new BlockRelay(4);
		relayMk5 = new BlockRelay(5);
		relayFinal = new BlockRelay(99);
		matterBlock = new BlockMatter();
		matter = new ItemMatter();
		bmHelmet = new ItemBMArmor(EntityEquipmentSlot.HEAD);
		bmChest = new ItemBMArmor(EntityEquipmentSlot.CHEST);
		bmLegs = new ItemBMArmor(EntityEquipmentSlot.LEGS);
		bmFeet = new ItemBMArmor(EntityEquipmentSlot.FEET);
		cmHelmet = new ItemCMArmor(EntityEquipmentSlot.HEAD);
		cmChest = new ItemCMArmor(EntityEquipmentSlot.CHEST);
		cmLegs = new ItemCMArmor(EntityEquipmentSlot.LEGS);
		cmFeet = new ItemCMArmor(EntityEquipmentSlot.FEET);

		register.register(aeguEXMk1_off, new ItemBlock(aeguEXMk1_off), "aegu/aeguexmk1_off");
		register.register(aeguEXMk1_on, new ItemBlock(aeguEXMk1_on), "aegu/aeguexmk1_on", false);
		register.register(aeguEXMk2_off, new ItemBlock(aeguEXMk2_off), "aegu/aeguexmk2_off");
		register.register(aeguEXMk2_on, new ItemBlock(aeguEXMk2_on), "aegu/aeguexmk2_on", false);
		register.register(aeguEXMk3_off, new ItemBlock(aeguEXMk3_off), "aegu/aeguexmk3_off");
		register.register(aeguEXMk3_on, new ItemBlock(aeguEXMk3_on), "aegu/aeguexmk3_on", false);
		register.register(aeguEXFinal_off, new ItemBlock(aeguEXFinal_off), "aegu/aeguexfinal_off");
		register.register(aeguEXFinal_on, new ItemBlock(aeguEXFinal_on), "aegu/aeguexfinal_on", false);
		register.register(condenserMk3, new ItemBlock(condenserMk3), "condenser_mk3");
		register.register(condenserGrade0, new ItemBlock(condenserGrade0), "condenser_grade0");
		register.register(collectorMk6, new ItemBlock(collectorMk6), "collectors/collectormk6");
		register.register(collectorMk7, new ItemBlock(collectorMk7), "collectors/collectormk7");
		register.register(collectorMk8, new ItemBlock(collectorMk8), "collectors/collectormk8");
		register.register(collectorMk9, new ItemBlock(collectorMk9), "collectors/collectormk9");
		register.register(collectorMk10, new ItemBlock(collectorMk10), "collectors/collectormk10");
		register.register(collectorFinal, new ItemBlock(collectorFinal), "collectors/collector_final");
		register.register(relayMk4, new ItemBlock(relayMk4), "relays/relaymk4");
		register.register(relayMk5, new ItemBlock(relayMk5), "relays/relaymk5");
		register.register(relayFinal, new ItemBlock(relayFinal), "relays/relay_final");
		register.register(matterBlock, new ItemBlockMatter(matterBlock), "matterblock");
		register.register(matter, "matter");
		register.register(bmHelmet, "bm_helmet");
		register.register(bmChest, "bm_chest");
		register.register(bmLegs, "bm_legs");
		register.register(bmFeet, "bm_feet");
		register.register(cmHelmet, "cm_helmet");
		register.register(cmChest, "cm_chest");
		register.register(cmLegs, "cm_legs");
		register.register(cmFeet, "cm_feet");

		GameRegistry.registerTileEntity(TileEntityCondenserMk3.class, "CondenserMk3Tile");
		GameRegistry.registerTileEntity(TileEntityCondenserGrade0.class, "CondenserGrade0Tile");
		GameRegistry.registerTileEntity(TileEntityCollectorMk6.class, "CollectorMk6Tile");
		GameRegistry.registerTileEntity(TileEntityCollectorMk7.class, "CollectorMk7Tile");
		GameRegistry.registerTileEntity(TileEntityCollectorMk8.class, "CollectorMk8Tile");
		GameRegistry.registerTileEntity(TileEntityCollectorMk9.class, "CollectorMk9Tile");
		GameRegistry.registerTileEntity(TileEntityCollectorMk10.class, "CollectorMk10Tile");
		GameRegistry.registerTileEntity(TileEntityCollectorFinal.class, "CollectorFinalTile");
		GameRegistry.registerTileEntity(TileEntityRelayMk4.class, "RelayMk4Tile");
		GameRegistry.registerTileEntity(TileEntityRelayMk5.class, "RelayMk5Tile");
		GameRegistry.registerTileEntity(TileEntityRelayFinal.class, "RelayFinalTile");
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

		// PEEX
		GameRegistry.addRecipe(new ItemStack(matter, 1, 0), "ddd", "rrr", "ddd", 'd', new ItemStack(ObjHandler.matter, 1, 0), 'r', new ItemStack(ObjHandler.matter, 1, 1));
		GameRegistry.addRecipe(new ItemStack(matter, 1, 1), "bbb", "brb", "bbb", 'b', new ItemStack(matter, 1, 0), 'r', new ItemStack(ObjHandler.matter, 1, 1));
		for(int i = 0; i < 2; i++) {
			GameRegistry.addRecipe(new ItemStack(matterBlock, 1, i), "mmm", "mmm", "mmm", 'm', new ItemStack(matter, 1, i));
			GameRegistry.addRecipe(new ItemStack(matter, 9, i), "b", 'b', new ItemStack(matterBlock, 1, i));
		}
		GameRegistry.addRecipe(new ItemStack(collectorMk6), "ccc", "crc", "ccc", 'c', collectorMk5, 'r', new ItemStack(ObjHandler.matter, 1, 1));
		GameRegistry.addRecipe(new ItemStack(collectorMk7), "ccc", "crc", "ccc", 'c', collectorMk6, 'r', new ItemStack(ObjHandler.matter, 1, 1));
		GameRegistry.addRecipe(new ItemStack(collectorMk8), "ccc", "cbc", "ccc", 'c', collectorMk7, 'b', new ItemStack(matter, 1, 0));
		GameRegistry.addRecipe(new ItemStack(collectorMk9), "ccc", "cbc", "ccc", 'c', collectorMk8, 'b', new ItemStack(matter, 1, 0));
		GameRegistry.addRecipe(new ItemStack(collectorMk10), "ccc", "cac", "ccc", 'c', collectorMk9, 'a', new ItemStack(matter, 1, 1));
		GameRegistry.addRecipe(new ItemStack(relayMk4), "rrr", "rbr", "rrr", 'r', ObjHandler.relayMK3, 'b', new ItemStack(matter, 1, 0));
		GameRegistry.addRecipe(new ItemStack(relayMk5), "rrr", "rar", "rrr", 'r', relayMk4, 'a', new ItemStack(matter, 1, 1));
		GameRegistry.addRecipe(new ItemStack(aeguEXMk1_off), "aaa", "aba", "aaa", 'a', aeguMk3_off, 'b', new ItemStack(matter, 1, 0));
		GameRegistry.addRecipe(new ItemStack(aeguEXMk2_off), "aaa", "aca", "aaa", 'a', aeguEXMk1_off, 'c', new ItemStack(matter, 1, 1));
		GameRegistry.addRecipe(new ItemStack(aeguEXMk3_off), "aaa", "aca", "aaa", 'a', aeguEXMk2_off, 'c', new ItemStack(matterBlock, 1, 1));
		GameRegistry.addRecipe(new ItemStack(condenserMk3), "aba", "bcb", "aba", 'a', new ItemStack(matter, 1, 1), 'b', new ItemStack(matter, 1, 0), 'c', ObjHandler.condenserMk2);

		RecipeManager.addArmorRecipe(new ItemStack(matter, 1, 0), new ItemStack(bmHelmet), new ItemStack(bmChest), new ItemStack(bmLegs), new ItemStack(bmFeet));
		RecipeManager.addArmorRecipe(new ItemStack(matter, 1, 1), new ItemStack(cmHelmet), new ItemStack(cmChest), new ItemStack(cmLegs), new ItemStack(cmFeet));

		if(PEEXConfig.isFinalType) {
			GameRegistry.addRecipe(new ItemStack(collectorFinal), "bcb", "cmc", "bcb", 'b', Blocks.BEDROCK, 'c', collectorMk10, 'm', new ItemStack(matterBlock, 1, 1));
			GameRegistry.addRecipe(new ItemStack(relayFinal), "brb", "bcb", "brb", 'b', Blocks.BEDROCK, 'r', relayMk5, 'c', new ItemStack(matterBlock, 1, 1));
			GameRegistry.addRecipe(new ItemStack(aeguEXFinal_off), "bab", "aca", "bab", 'b', Blocks.BEDROCK, 'a', aeguEXMk3_off, 'c', new ItemStack(matterBlock, 1, 1));
		}
		if(PEEXConfig.isGradeZero) {
			GameRegistry.addRecipe(new ItemStack(condenserGrade0), "aba", "lcr", "aoa", 'a', aeguEXFinal_off, 'b', new ItemStack(matterBlock, 1, 0), 'l', collectorFinal, 'r', relayFinal, 'c', condenserMk3, 'o', new ItemStack(matterBlock, 1, 1));
		}
	}
}
