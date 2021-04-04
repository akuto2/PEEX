package akuto2.peex;

import akuto2.peex.gui.GuiHandler;
import akuto2.peex.proxies.CommonProxy;
import akuto2.peex.utils.CreativeTabPEEX;
import akuto2.peex.utils.ModInfo;
import akuto2.peex.utils.PEEXConfig;
import lib.utils.UpdateChecker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.Mod.Metadata;
import net.minecraftforge.fml.common.ModMetadata;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid="PEEX", name="PEEX", version="2.0.5", dependencies="required-after:AkutoLib;required-after:ProjectE")
public class PEEXCore{
	@Instance("PEEX")
	public static PEEXCore instance;
	@Metadata("PEEX")
	public static ModMetadata meta;
	@SidedProxy(serverSide = "akuto2.peex.proxies.CommonProxy", clientSide = "akuto2.peex.proxies.ClientProxy")
	public static CommonProxy proxy;
	public static UpdateChecker update = null;

	public static CreativeTabs peexTab = new CreativeTabPEEX("PEEX");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		PEEXConfig.init(event);
		ModInfo.registerInfo(meta);
		update = new UpdateChecker("PEEX", meta);
		update.checkUpdate();
		ObjHandlerPEEX.register();
		proxy.registerModels();
		proxy.registerRenderers();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		ObjHandlerPEEX.addRecipes();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		if((update != null) && (event.getSide() == Side.SERVER)) {
			update.notifyUpdate(event.getServer(), event.getSide());
		}
	}
}
