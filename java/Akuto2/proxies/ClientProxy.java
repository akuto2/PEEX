package Akuto2.proxies;

import Akuto2.ObjHandlerPEEX;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ClientProxy extends CommonProxy{
	@Override
	public void registerModels() {
		registerBlock(ObjHandlerPEEX.aeguMk1_off);
		registerBlock(ObjHandlerPEEX.aeguMk1_on);
		registerBlock(ObjHandlerPEEX.aeguMk2_off);
		registerBlock(ObjHandlerPEEX.aeguMk2_on);
		registerBlock(ObjHandlerPEEX.aeguMk3_off);
		registerBlock(ObjHandlerPEEX.aeguMk3_on);
		registerBlock(ObjHandlerPEEX.collectorMk4);
		registerBlock(ObjHandlerPEEX.collectorMk5);
	}

	private void registerBlock(Block block) {
		String name = ForgeRegistries.BLOCKS.getKey(block).toString();
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(name, "inventory"));
	}

	private void registerItem(Item item) {
		registerItem(item, 0);
	}

	private void registerItem(Item item, int meta) {
		String name = ForgeRegistries.ITEMS.getKey(item).toString();
		ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(name, "inventory"));
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getWorldClient();
	}
}
