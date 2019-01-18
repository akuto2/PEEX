package Akuto2.Proxies;

import Akuto2.ObjHandlerPEEX;
import Akuto2.PlayerData.TransmutationMk2Props;
import Akuto2.Rendering.CondensearMk3ItemRenderer;
import Akuto2.Rendering.CondenserGrade0ItemRender;
import Akuto2.Rendering.CondenserGrade0Render;
import Akuto2.Rendering.CondenserMk3Renderer;
import Akuto2.TileEntity.TileEntityCondenserGrade0;
import Akuto2.TileEntity.TileEntityCondenserMk3;
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
