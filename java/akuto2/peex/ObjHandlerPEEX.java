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
import lib.utils.Register;
import moze_intel.projecte.api.ProjectEAPI;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayFinal;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk4;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk5;
import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber(modid = "peex")
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

	// PEEXで追加されるもの
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

	public static Register register = new Register("peex", PEEXCore.tabPEEX);

	/**
	 * Blockの登録
	 */
	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		aeguMk1_off = new BlockAEGU(1, false);
		aeguMk1_on = new BlockAEGU(1, true);
		aeguMk2_off = new BlockAEGU(2, false);
		aeguMk2_on = new BlockAEGU(2, true);
		aeguMk3_off = new BlockAEGU(3, false);
		aeguMk3_on = new BlockAEGU(3, true);
		collectorMk4 = new BlockCollector(4);
		collectorMk5 = new BlockCollector(5);

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

		register.setRegistry(event.getRegistry());
		register.register(aeguMk1_off, "aegu/aegumk1_off");
		register.register(aeguMk1_on, "aegu/aegumk1_on", false);
		register.register(aeguMk2_off, "aegu/aegumk2_off");
		register.register(aeguMk2_on, "aegu/aegumk2_on", false);
		register.register(aeguMk3_off, "aegu/aegumk3_off");
		register.register(aeguMk3_on, "aegu/aegumk3_on", false);
		register.register(collectorMk4, "collectors/collectormk4");
		register.register(collectorMk5, "collectors/collectormk5");

		register.register(aeguEXMk1_off, "aegu/aeguexmk1_off");
		register.register(aeguEXMk1_on, "aegu/aeguexmk1_on", false);
		register.register(aeguEXMk2_off, "aegu/aeguexmk2_off");
		register.register(aeguEXMk2_on, "aegu/aeguexmk2_on", false);
		register.register(aeguEXMk3_off, "aegu/aeguexmk3_off");
		register.register(aeguEXMk3_on, "aegu/aeguexmk3_on", false);
		register.register(aeguEXFinal_off, "aegu/aeguexfinal_off");
		register.register(aeguEXFinal_on, "aegu/aeguexfinal_on", false);
		register.register(condenserMk3, "condenser_mk3");
		register.register(condenserGrade0, "condenser_grade0");
		register.register(collectorMk6, "collectors/collectormk6");
		register.register(collectorMk7, "collectors/collectormk7");
		register.register(collectorMk8, "collectors/collectormk8");
		register.register(collectorMk9, "collectors/collectormk9");
		register.register(collectorMk10, "collectors/collectormk10");
		register.register(collectorFinal, "collectors/collector_final");
		register.register(relayMk4, "relays/relaymk4");
		register.register(relayMk5, "relays/relaymk5");
		register.register(relayFinal, "relays/relay_final");
		register.register(matterBlock, "matterblock");
	}

	/**
	 * Itemの登録
	 */
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		matter = new ItemMatter();

		bmMaterial = EnumHelper.addArmorMaterial("bm_material", "", 9999, new int[] { 8, 12, 10, 6 }, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F);
		cmMaterial = EnumHelper.addArmorMaterial("cm_material", "", 9999, new int[] { 10, 15, 13, 8 }, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 4.0F);

		bmHelmet = new ItemBMArmor(EntityEquipmentSlot.HEAD);
		bmChest = new ItemBMArmor(EntityEquipmentSlot.CHEST);
		bmLegs = new ItemBMArmor(EntityEquipmentSlot.LEGS);
		bmFeet = new ItemBMArmor(EntityEquipmentSlot.FEET);

		cmHelmet = new ItemCMArmor(EntityEquipmentSlot.HEAD);
		cmChest = new ItemCMArmor(EntityEquipmentSlot.CHEST);
		cmLegs = new ItemCMArmor(EntityEquipmentSlot.LEGS);
		cmFeet = new ItemCMArmor(EntityEquipmentSlot.FEET);

		register.setRegistry(event.getRegistry());
		register.register(new ItemBlock(aeguMk1_off).setRegistryName(aeguMk1_off.getRegistryName()));
		register.register(new ItemBlock(aeguMk1_on).setRegistryName(aeguMk1_on.getRegistryName()));
		register.register(new ItemBlock(aeguMk2_off).setRegistryName(aeguMk2_off.getRegistryName()));
		register.register(new ItemBlock(aeguMk2_on).setRegistryName(aeguMk2_on.getRegistryName()));
		register.register(new ItemBlock(aeguMk3_off).setRegistryName(aeguMk3_off.getRegistryName()));
		register.register(new ItemBlock(aeguMk3_on).setRegistryName(aeguMk3_on.getRegistryName()));
		register.register(new ItemBlock(collectorMk4).setRegistryName(collectorMk4.getRegistryName()));
		register.register(new ItemBlock(collectorMk5).setRegistryName(collectorMk5.getRegistryName()));

		register.register(new ItemBlock(aeguEXMk1_off).setRegistryName(aeguEXMk1_off.getRegistryName()));
		register.register(new ItemBlock(aeguEXMk1_on).setRegistryName(aeguEXMk1_on.getRegistryName()));
		register.register(new ItemBlock(aeguEXMk2_off).setRegistryName(aeguEXMk2_off.getRegistryName()));
		register.register(new ItemBlock(aeguEXMk2_on).setRegistryName(aeguEXMk2_on.getRegistryName()));
		register.register(new ItemBlock(aeguEXMk3_off).setRegistryName(aeguEXMk3_off.getRegistryName()));
		register.register(new ItemBlock(aeguEXMk3_on).setRegistryName(aeguEXMk3_on.getRegistryName()));
		register.register(new ItemBlock(aeguEXFinal_off).setRegistryName(aeguEXFinal_off.getRegistryName()));
		register.register(new ItemBlock(aeguEXFinal_on).setRegistryName(aeguEXFinal_on.getRegistryName()));
		register.register(new ItemBlock(condenserMk3).setRegistryName(condenserMk3.getRegistryName()));
		register.register(new ItemBlock(condenserGrade0).setRegistryName(condenserGrade0.getRegistryName()));
		register.register(new ItemBlock(collectorMk6).setRegistryName(collectorMk6.getRegistryName()));
		register.register(new ItemBlock(collectorMk7).setRegistryName(collectorMk7.getRegistryName()));
		register.register(new ItemBlock(collectorMk8).setRegistryName(collectorMk8.getRegistryName()));
		register.register(new ItemBlock(collectorMk9).setRegistryName(collectorMk9.getRegistryName()));
		register.register(new ItemBlock(collectorMk10).setRegistryName(collectorMk10.getRegistryName()));
		register.register(new ItemBlock(collectorFinal).setRegistryName(collectorFinal.getRegistryName()));
		register.register(new ItemBlock(relayMk4).setRegistryName(relayMk4.getRegistryName()));
		register.register(new ItemBlock(relayMk5).setRegistryName(relayMk5.getRegistryName()));
		register.register(new ItemBlock(relayFinal).setRegistryName(relayFinal.getRegistryName()));
		register.register(new ItemBlockMatter(matterBlock).setRegistryName(matterBlock.getRegistryName()));
		register.register(matter, "matter");
		register.register(bmHelmet, "bm_helmet");
		register.register(bmChest, "bm_chest");
		register.register(bmLegs, "bm_legs");
		register.register(bmFeet, "bm_feet");
		register.register(cmHelmet, "cm_helmet");
		register.register(cmChest, "cm_chest");
		register.register(cmLegs, "cm_legs");
		register.register(cmFeet, "cm_feet");
	}

	public static void register() {
		// TileEntityの登録
		GameRegistry.registerTileEntity(TileEntityCollectorMk4.class, new ResourceLocation("peex", "collectors/collectormk4"));
		GameRegistry.registerTileEntity(TileEntityCollectorMk5.class, new ResourceLocation("peex", "collectors/collectormk5"));
		GameRegistry.registerTileEntity(TileEntityCollectorMk6.class, new ResourceLocation("peex", "collectors/collectormk6"));
		GameRegistry.registerTileEntity(TileEntityCollectorMk7.class, new ResourceLocation("peex", "collectors/collectormk7"));
		GameRegistry.registerTileEntity(TileEntityCollectorMk8.class, new ResourceLocation("peex", "collectors/collectormk8"));
		GameRegistry.registerTileEntity(TileEntityCollectorMk9.class, new ResourceLocation("peex", "collectors/collectormk9"));
		GameRegistry.registerTileEntity(TileEntityCollectorMk10.class, new ResourceLocation("peex", "collectors/collectormk10"));
		GameRegistry.registerTileEntity(TileEntityCollectorFinal.class, new ResourceLocation("peex", "collectors/collectorfinal"));
		GameRegistry.registerTileEntity(TileEntityCondenserMk3.class, new ResourceLocation("peex", "condenser_mk3"));
		GameRegistry.registerTileEntity(TileEntityCondenserGrade0.class, new ResourceLocation("peex", "condenser_grade0"));
		GameRegistry.registerTileEntity(TileEntityRelayMk4.class, new ResourceLocation("peex", "relays/relaymk4"));
		GameRegistry.registerTileEntity(TileEntityRelayMk5.class, new ResourceLocation("peex", "relays/relaymk5"));
		GameRegistry.registerTileEntity(TileEntityRelayFinal.class, new ResourceLocation("peex", "relays/relayfinal"));
	}

	@SubscribeEvent
	public static void addRecipes(RegistryEvent.Register<IRecipe> event) {
		// AEGUMK3(指定のレシピを必要とするため)
		event.getRegistry().register(new RecipeAEGUMk3());
		// 究極型AEGUのEMC設定
		ProjectEAPI.getEMCProxy().registerCustomEMC(new ItemStack(aeguMk3_off), 737153536L);
	}
}
