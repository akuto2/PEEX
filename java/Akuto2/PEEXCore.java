package Akuto2;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid="PEEX", name="PEEX", version="2.0.0")
public class PEEXCore{
	@Instance("PEEX")
	public static PEEXCore instance;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {

	}
}
