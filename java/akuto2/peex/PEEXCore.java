package akuto2.peex;

import akuto2.peex.creativetab.CreativeTabPEEX;
import akuto2.peex.gui.GuiHandler;
import akuto2.peex.packet.PEEXPacketHandler;
import akuto2.peex.proxies.CommonProxy;
import akuto2.peex.utils.ModInfo;
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

@Mod(modid = "PEEX", name = "PEEX", version = "1.2.6", dependencies = "required-after:AkutoLib@[1.0.4,);required-after:ProjectE;after:PEAA")
public class PEEXCore {
	@Instance("PEEX")
	public static PEEXCore instance;
	@Metadata("PEEX")
	public static ModMetadata meta;
	@SidedProxy(clientSide = "akuto2.peex.proxies.ClientProxy", serverSide = "akuto2.peex.proxies.CommonProxy")
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
