package Akuto2;

import Akuto2.blocks.BlockAEGU;
import Akuto2.blocks.BlockCollector;
import Akuto2.tiles.TileEntityCollectorFinal;
import Akuto2.tiles.TileEntityCollectorMk10;
import Akuto2.tiles.TileEntityCollectorMk4;
import Akuto2.tiles.TileEntityCollectorMk5;
import Akuto2.tiles.TileEntityCollectorMk6;
import Akuto2.tiles.TileEntityCollectorMk7;
import Akuto2.tiles.TileEntityCollectorMk8;
import Akuto2.tiles.TileEntityCollectorMk9;
import lib.utils.Register;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber(modid = "PEEX")
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

		collectorMk6 = new BlockCollector(6);
		collectorMk7 = new BlockCollector(7);
		collectorMk8 = new BlockCollector(8);
		collectorMk9 = new BlockCollector(9);
		collectorMk10 = new BlockCollector(10);
		collectorFinal = new BlockCollector(99);

		register.setRegistry(event.getRegistry());
		register.register(aeguMk1_off, "aegu/aegumk1_off");
		register.register(aeguMk1_on, "aegu/aeguml1_on", false);
		register.register(aeguMk2_off, "aegu/aegumk2_off");
		register.register(aeguMk2_on, "aegu/aegumk2_on", false);
		register.register(aeguMk3_off, "aegu/aegumk3_off");
		register.register(aeguMk3_on, "aegu/aegumk3_on", false);
		register.register(collectorMk4, "collectors/collectormk4");
		register.register(collectorMk5, "collectors/collectormk5");

		register.register(collectorMk6, "collectors/collectormk6");
		register.register(collectorMk7, "collectors/collectormk7");
		register.register(collectorMk8, "collectors/collectormk8");
		register.register(collectorMk9, "collectors/collectormk9");
		register.register(collectorMk10, "collectors/collectormk10");
		register.register(collectorFinal, "collectors/collectorfinal");
	}

	/**
	 * Itemの登録
	 */
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		register.setRegistry(event.getRegistry());
		register.register(new ItemBlock(aeguMk1_off).setRegistryName(aeguMk1_off.getRegistryName()));
		register.register(new ItemBlock(aeguMk1_on).setRegistryName(aeguMk1_on.getRegistryName()));
		register.register(new ItemBlock(aeguMk2_off).setRegistryName(aeguMk2_off.getRegistryName()));
		register.register(new ItemBlock(aeguMk2_on).setRegistryName(aeguMk2_on.getRegistryName()));
		register.register(new ItemBlock(aeguMk3_off).setRegistryName(aeguMk3_off.getRegistryName()));
		register.register(new ItemBlock(aeguMk3_on).setRegistryName(aeguMk3_on.getRegistryName()));
		register.register(new ItemBlock(collectorMk4).setRegistryName(collectorMk4.getRegistryName()));
		register.register(new ItemBlock(collectorMk5).setRegistryName(collectorMk5.getRegistryName()));

		register.register(new ItemBlock(collectorMk6).setRegistryName(collectorMk6.getRegistryName()));
		register.register(new ItemBlock(collectorMk7).setRegistryName(collectorMk7.getRegistryName()));
		register.register(new ItemBlock(collectorMk8).setRegistryName(collectorMk8.getRegistryName()));
		register.register(new ItemBlock(collectorMk9).setRegistryName(collectorMk9.getRegistryName()));
		register.register(new ItemBlock(collectorMk10).setRegistryName(collectorMk10.getRegistryName()));
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
	}

	/**
	 * レシピの追加
	 */
	public static void addRecipes() {
		// 最終型とグレード0のみ(Configによって追加するかしないか分かれるため)
		// 他はJson
	}
}
