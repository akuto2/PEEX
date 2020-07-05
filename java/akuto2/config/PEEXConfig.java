package akuto2.config;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;

@Config(modid = "peex", name = "PEEX")
public class PEEXConfig {
	public static final Recipes recipes = new Recipes();
	public static class Recipes {
		@Comment("Can create Final Type equipment in survival mode")
		public boolean isFinalType = false;

		@Comment("Can create Condenser Grade 0 in survival mode")
		public boolean isGradeZero;
	}
}
