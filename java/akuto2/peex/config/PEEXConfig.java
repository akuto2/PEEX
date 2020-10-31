package akuto2.peex.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.Type;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = "peex", name = "PEEX")
@EventBusSubscriber(modid = "peex")
public class PEEXConfig {
	public static final Recipes recipes = new Recipes();
	public static class Recipes {
		@Comment("Can create Final Type equipment in survival mode")
		public boolean isFinalType = false;

		@Comment("Can create Condenser Grade 0 in survival mode")
		public boolean isGradeZero = false;
	}

	@SubscribeEvent
	public static void onConfigChanged(OnConfigChangedEvent event) {
		if(event.getModID().equals("peex")) {
			ConfigManager.sync("peex", Type.INSTANCE);
		}
	}
}
