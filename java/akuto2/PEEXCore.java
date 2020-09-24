package akuto2;

import akuto2.gui.GuiHandler;
import akuto2.proxies.CommonProxy;
import akuto2.utils.CreativeTabPEEX;
import akuto2.utils.ModInfo;
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

@Mod(modid = "peex", name = "PEEX", version = "3.0.3", dependencies = "required-after:akutolib;required-after:projecte")
public class PEEXCore {
	@Instance("peex")
	public static PEEXCore instance;
	@Metadata("peex")
	public static ModMetadata meta;
	@SidedProxy(serverSide = "akuto2.proxies.CommonProxy", clientSide = "akuto2.proxies.ClientProxy")
	public static CommonProxy proxy;
	public static UpdateChecker update = null;

	public static CreativeTabs tabPEEX = new CreativeTabPEEX("PEEX");

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ModInfo.registerInfo(meta);
		update = new UpdateChecker("peex", meta);
		update.checkUpdate();
		proxy.registerRenderers();
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		ObjHandlerPEEX.register();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event) {
		if(update != null && event.getSide() == Side.SERVER) {
			update.notifyUpdate(event.getServer(), event.getSide());
		}
	}
}
