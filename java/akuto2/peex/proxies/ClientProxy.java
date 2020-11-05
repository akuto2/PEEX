package akuto2.peex.proxies;

import akuto2.peex.ObjHandlerPEEX;
import akuto2.peex.playerdata.TransmutationMk2Props;
import akuto2.peex.rendering.CondensearMk3ItemRenderer;
import akuto2.peex.rendering.CondenserGrade0ItemRender;
import akuto2.peex.rendering.CondenserGrade0Render;
import akuto2.peex.rendering.CondenserMk3Renderer;
import akuto2.peex.tile.TileEntityCondenserGrade0;
import akuto2.peex.tile.TileEntityCondenserMk3;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy{
	public void registerRenderInformation(){}

	@Override
	public TransmutationMk2Props getClientTransmutationProps() {
		return TransmutationMk2Props.getDataFor(FMLClientHandler.instance().getClientPlayerEntity());
	}

	@Override
	public void registerTileEntitySpecialRenderer() {
		if(Loader.isModLoaded("PEAA")){
			 MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ObjHandlerPEEX.condenserMk3), new CondensearMk3ItemRenderer());
			 MinecraftForgeClient.registerItemRenderer(Item.getItemFromBlock(ObjHandlerPEEX.condenserGrade0), new CondenserGrade0ItemRender());
       	 	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCondenserMk3.class, new CondenserMk3Renderer());
       	 	 ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCondenserGrade0.class, new CondenserGrade0Render());
		}
	}

	@Override
	public World getClientWorld() {
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
