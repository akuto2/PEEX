package Akuto2;

import Akuto2.CreativeTab.CreativeTabPEEX;
import Akuto2.Gui.GuiHandler;
import Akuto2.Packet.PEEXPacketHandler;
import Akuto2.Proxies.CommonProxy;
import Akuto2.Utils.ModInfo;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.Metadata;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import lib.utils.UpdateChecker;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.config.Configuration;

@Mod(modid = "PEEX", name = "PEEX", version = "1.2.4", dependencies = "required-after:AkutoLib;required-after:ProjectE;after:PEAA")
public class PEEXCore {
	@Instance("PEEX")
	public static PEEXCore instance;
	@Metadata("PEEX")
	public static ModMetadata meta;
	@SidedProxy(clientSide = "Akuto2.Proxies.ClientProxy", serverSide = "Akuto2.Proxies.CommonProxy")
	public static CommonProxy proxy;
	public static UpdateChecker update = null;

	public static final CreativeTabs tabPEEX = new CreativeTabPEEX("PEEX");
	protected static boolean isFinalType;
	protected static boolean isGradeZero;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event){
		Configuration config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		isFinalType = config.get("Finaltype", "mode", false, "Add Final type education").getBoolean();
		isGradeZero = config.get("GradeZero", "mode", false, "Add Grade Zero education").getBoolean();
		config.save();
		ModInfo.load(meta);
		update = new UpdateChecker("PEEX", meta);
		PEEXPacketHandler.register();
		ObjHandlerPEEX.register();
		if(Loader.isModLoaded("PEAA")){
			ObjHandlerPEEX.registerPEAA();
		}
	}

	@Mod.EventHandler
	public void Init(FMLInitializationEvent event){
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
		proxy.registerTileEntitySpecialRenderer();
		ObjHandlerPEEX.addRecipe();
		if(Loader.isModLoaded("PEAA")){
			ObjHandlerPEEX.addRecipePEAA();
		}
	}
}
