package Akuto2;

import Akuto2.Blocks.BlockAEGUEX;
import Akuto2.Blocks.BlockBlueMatter;
import Akuto2.Blocks.BlockChaosMatter;
import Akuto2.Blocks.BlockCollector;
import Akuto2.Blocks.BlockCondenserGrade0;
import Akuto2.Blocks.BlockCondenserMk3;
import Akuto2.Blocks.BlockRelay;
import Akuto2.Blocks.BlockTransmutaionStoneMk2;
import Akuto2.Items.Armor.BMArmor;
import Akuto2.TileEntity.TileEntityCollectorFinal;
import Akuto2.TileEntity.TileEntityCollectorMk10;
import Akuto2.TileEntity.TileEntityCollectorMk6;
import Akuto2.TileEntity.TileEntityCollectorMk7;
import Akuto2.TileEntity.TileEntityCollectorMk8;
import Akuto2.TileEntity.TileEntityCollectorMk9;
import Akuto2.TileEntity.TileEntityCondenserGrade0;
import Akuto2.TileEntity.TileEntityCondenserMk3;
import Akuto2.TileEntity.TileEntityRelayFinal;
import Akuto2.TileEntity.TileEntityRelayMk4;
import Akuto2.TileEntity.TileEntityRelayMk5;
import Akuto2.Utils.EnumArmorType;
import cpw.mods.fml.common.registry.GameRegistry;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import peaa.gameObjs.ObjHandlerPEAA;

public class ObjHandlerPEEX {
	public static Block condenserMk3;
	public static Block condenserGrade0;
	public static Block aeguEXMk1_off;
	public static Block aeguEXMk1_on;
	public static Block aeguEXMk2_off;
	public static Block aeguEXMk2_on;
	public static Block aeguEXMK3_off;
	public static Block aeguEXMK3_on;
	public static Block aeguEXFinal_off;
	public static Block aeguEXFinal_on;
	public static Block collectorMk6;
	public static Block collectorMk7;
	public static Block collectorMk8;
	public static Block collectorMk9;
	public static Block collectorMk10;
	public static Block collectorFinal;
	public static Block relayMk4;
	public static Block relayMk5;
	public static Block relayFinal;
	public static Block bluematterBlock;
	public static Block chaosmatterBlock;
	public static Block transmutationStoneMk2;
	public static Item transmutationMk2Tablet;
	public static Item bluematter;
	public static Item chaosmatter;

	public static Item bmHelmet;
	public static Item bmChest;
	public static Item bmLegs;
	public static Item bmFeet;

	public static Item cmHelmet;
	public static Item cmChest;
	public static Item cmLegs;
	public static Item cmFeet;

	public static ArmorMaterial BMMaterial;
	public static ArmorMaterial CMMaterial;

	public static void register() {
		relayMk4 = new BlockRelay(4);
		GameRegistry.registerBlock(relayMk4, "relayMk4");
		relayMk5 = new BlockRelay(5);
		GameRegistry.registerBlock(relayMk5, "relayMk5");
		bluematter = new Item().setCreativeTab(PEEXCore.tabPEEX).setTextureName("peex:bluematter").setUnlocalizedName("bluematter");
		GameRegistry.registerItem(bluematter, "bluematter");
		chaosmatter = new Item().setCreativeTab(PEEXCore.tabPEEX).setTextureName("peex:chaosmatter").setUnlocalizedName("chaosmatter");
		GameRegistry.registerItem(chaosmatter, "chaosmatter");
		bluematterBlock = new BlockBlueMatter();
		GameRegistry.registerBlock(bluematterBlock, "bluematterBlock");
		chaosmatterBlock = new BlockChaosMatter();
		GameRegistry.registerBlock(chaosmatterBlock, "chaosmatterBlock");
		BMMaterial = EnumHelper.addArmorMaterial("BMMaterial", 9999, new int[] {8, 12, 10, 6}, 15);
		CMMaterial = EnumHelper.addArmorMaterial("CMMaterial", 9999, new int[] {10, 15, 13, 8}, 25);
		bmHelmet = new BMArmor(EnumArmorType.HEAD);
		GameRegistry.registerItem(bmHelmet, "bmHelmet");
		bmChest = new BMArmor(EnumArmorType.CHEST);
		GameRegistry.registerItem(bmChest, "bmChest");
		bmLegs = new BMArmor(EnumArmorType.LEGS);
		GameRegistry.registerItem(bmLegs, "bmLegs");
		bmFeet = new BMArmor(EnumArmorType.FEET);
		GameRegistry.registerItem(bmFeet, "bmFeet");
		transmutationStoneMk2 = new BlockTransmutaionStoneMk2();
		GameRegistry.registerBlock(transmutationStoneMk2, "transmutationStonePEEX");
		GameRegistry.registerTileEntity(TileEntityRelayMk4.class, "tile.relayMk4");
		GameRegistry.registerTileEntity(TileEntityRelayMk5.class, "tile.relayMk5");
	}

	public static void addRecipe() {
		ItemStack darkMatter = new ItemStack(ObjHandler.matter, 1, 0);
		ItemStack redMatter = new ItemStack(ObjHandler.matter, 1, 1);
		ItemStack relayMk3 = new ItemStack(ObjHandler.relayMK3);
		GameRegistry.addRecipe(new ItemStack(bluematter), "ddd", "rrr", "ddd", 'd', darkMatter, 'r', redMatter);
		GameRegistry.addRecipe(new ItemStack(chaosmatter), "ddd", "drd", "ddd", 'd', bluematter, 'r', redMatter);
		GameRegistry.addRecipe(new ItemStack(relayMk4), "rrr", "rdr", "rrr", 'r', relayMk3, 'd', bluematter);
		GameRegistry.addRecipe(new ItemStack(relayMk5), "rrr", "rar", "rrr", 'r', relayMk4, 'a', chaosmatter);
		GameRegistry.addRecipe(new ItemStack(bluematterBlock), "bbb", "bbb", "bbb", 'b', bluematter);
		GameRegistry.addRecipe(new ItemStack(chaosmatterBlock), "ccc", "ccc", "ccc", 'c', chaosmatter);
		GameRegistry.addRecipe(new ItemStack(bmHelmet), "xxx", "x x", 'x', bluematter);
		GameRegistry.addRecipe(new ItemStack(bmChest), "x x", "xxx", "xxx", 'x', bluematter);
		GameRegistry.addRecipe(new ItemStack(bmLegs), "xxx", "x x", "x x", 'x', bluematter);
		GameRegistry.addRecipe(new ItemStack(bmFeet), "x x", "x x", 'x', bluematter);
	}

	public static void registerPEAA() {
		condenserMk3 = new BlockCondenserMk3().setCreativeTab(PEEXCore.tabPEEX);
		GameRegistry.registerBlock(condenserMk3, "condenserMk3");
		aeguEXMk1_off = new BlockAEGUEX(1, false);
		GameRegistry.registerBlock(aeguEXMk1_off, "aeguEXMk1_off");
		aeguEXMk1_on = new BlockAEGUEX(1, true);
		GameRegistry.registerBlock(aeguEXMk1_on, "aeguEXMk1_on");
		aeguEXMk2_off = new BlockAEGUEX(2, false);
		GameRegistry.registerBlock(aeguEXMk2_off, "aeguEXMk2_off");
		aeguEXMk2_on = new BlockAEGUEX(2, true);
		GameRegistry.registerBlock(aeguEXMk2_on, "aeguEXMk2_on");
		aeguEXMK3_off = new BlockAEGUEX(3, false);
		GameRegistry.registerBlock(aeguEXMK3_off, "aeguEXMK3_off");
		aeguEXMK3_on = new BlockAEGUEX(3, true);
		GameRegistry.registerBlock(aeguEXMK3_on, "aeguEXMK3_on");
		collectorMk6 = new BlockCollector(6);
		GameRegistry.registerBlock(collectorMk6, "collectorMk6");
		collectorMk7 = new BlockCollector(7);
		GameRegistry.registerBlock(collectorMk7, "collectorMk7");
		collectorMk8 = new BlockCollector(8);
		GameRegistry.registerBlock(collectorMk8, "collectorMk8");
		collectorMk9 = new BlockCollector(9);
		GameRegistry.registerBlock(collectorMk9, "collectorMk9");
		collectorMk10 = new BlockCollector(10);
		GameRegistry.registerBlock(collectorMk10, "collectorMk10");
		GameRegistry.registerTileEntity(TileEntityCondenserMk3.class, "tile.condenserMk3");
		GameRegistry.registerTileEntity(TileEntityCollectorMk6.class, "tile.collectorMk6");
		GameRegistry.registerTileEntity(TileEntityCollectorMk7.class, "tile.collectorMk7");
		GameRegistry.registerTileEntity(TileEntityCollectorMk8.class, "tile.collectorMk8");
		GameRegistry.registerTileEntity(TileEntityCollectorMk9.class, "tile.collectorMk9");
		GameRegistry.registerTileEntity(TileEntityCollectorMk10.class, "tile.collectorMk10");
		if(PEEXCore.isFinalType){
			collectorFinal = new BlockCollector(99);
			GameRegistry.registerBlock(collectorFinal, "collectorFinal");
			relayFinal = new BlockRelay(99);
			GameRegistry.registerBlock(relayFinal, "relayFinal");
			aeguEXFinal_off = new BlockAEGUEX(4, false);
			GameRegistry.registerBlock(aeguEXFinal_off, "aeguEXFinal_off");
			aeguEXFinal_on = new BlockAEGUEX(4, true);
			GameRegistry.registerBlock(aeguEXFinal_on, "aeguEXFinal_on");
			GameRegistry.registerTileEntity(TileEntityCollectorFinal.class, "tile.collectorFinal");
			GameRegistry.registerTileEntity(TileEntityRelayFinal.class, "tile.relayFinal");
		}
		if(PEEXCore.isGradeZero) {
			condenserGrade0 = new BlockCondenserGrade0().setCreativeTab(PEEXCore.tabPEEX);
			GameRegistry.registerBlock(condenserGrade0, "condenserGrade0");
			GameRegistry.registerTileEntity(TileEntityCondenserGrade0.class, "tile.condenserGrade0");
		}
	}

	public static void addRecipePEAA() {
		ItemStack redMatter = new ItemStack(ObjHandler.matter, 1, 1);
		ItemStack collectorMk5 = new ItemStack(ObjHandlerPEAA.collectorMK5);
		ItemStack AEGU = new ItemStack(ObjHandlerPEAA.aeguMK3_off);
		ItemStack condenserMk2 = new ItemStack(ObjHandler.condenserMk2);
		GameRegistry.addRecipe(new ItemStack(collectorMk6), "ccc", "crc", "ccc", 'c', collectorMk5, 'r', redMatter);
		GameRegistry.addRecipe(new ItemStack(collectorMk7), "ccc", "crc", "ccc", 'c', collectorMk6, 'r', redMatter);
		GameRegistry.addRecipe(new ItemStack(collectorMk8), "ccc", "cdc", "ccc", 'c', collectorMk7, 'd', bluematter);
		GameRegistry.addRecipe(new ItemStack(collectorMk9), "ccc", "cdc", "ccc", 'c', collectorMk8, 'd', bluematter);
		GameRegistry.addRecipe(new ItemStack(collectorMk10), "ccc", "cac", "ccc", 'c', collectorMk9, 'a', chaosmatter);
		GameRegistry.addRecipe(new ItemStack(aeguEXMk1_off), "aaa", "ada", "aaa", 'a', AEGU, 'd', bluematter);
		GameRegistry.addRecipe(new ItemStack(aeguEXMk2_off), "aaa", "ana", "aaa", 'a', aeguEXMk1_off, 'n', chaosmatter);
		GameRegistry.addRecipe(new ItemStack(aeguEXMK3_off), "aaa", "aca", "aaa", 'a', aeguEXMk2_off, 'c', chaosmatterBlock);
		GameRegistry.addRecipe(new ItemStack(condenserMk3), "ada", "dcd", "ada", 'a', chaosmatter, 'd', bluematter, 'c', condenserMk2);
		if(PEEXCore.isFinalType){
			GameRegistry.addRecipe(new ItemStack(collectorFinal), "bcb", "cmc", "bcb", 'b', Blocks.bedrock, 'c', collectorMk10, 'm', chaosmatterBlock);
			GameRegistry.addRecipe(new ItemStack(relayFinal), "brb", "bcb", "brb", 'b', Blocks.bedrock, 'r', relayMk5, 'c', chaosmatterBlock);
			GameRegistry.addRecipe(new ItemStack(aeguEXFinal_off), "bab", "aca", "bab", 'b', Blocks.bedrock, 'a', aeguEXMK3_off, 'c', chaosmatterBlock);
		}
		if(PEEXCore.isGradeZero) {
			GameRegistry.addRecipe(new ItemStack(condenserGrade0), "aba", "lcr", "aoa", 'a', aeguEXFinal_off, 'b', bluematterBlock, 'l', collectorFinal, 'r', relayFinal, 'c', condenserMk3, 'o', chaosmatterBlock);
		}
	}
}
