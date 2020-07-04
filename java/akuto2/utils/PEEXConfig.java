package akuto2.utils;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class PEEXConfig {
	public static boolean isFinalType;
	public static boolean isGradeZero;

	public static void init(FMLPreInitializationEvent event) {
		Configuration config = new Configuration(event.getSuggestedConfigurationFile(), true);

		isFinalType = config.getBoolean("EnableFinalTypeRecipe", "recipes", false, "Can create Final Type equipment in survival mode");
		isGradeZero = config.getBoolean("EnableGradeZeroRecipe", "recipes", false, "Can create Condenser Grade 0 in survival mode");

		// Grade0だけオンにしても意味ないのでその場合は最終型も作れるようにする
		if(!isFinalType && isGradeZero) {
			isFinalType = true;
		}

		config.save();
	}
}
