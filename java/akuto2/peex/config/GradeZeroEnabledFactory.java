package akuto2.peex.config;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

/**
 * グレード0のレシピを追加するかどうかをjsonに送るクラス
 */
public class GradeZeroEnabledFactory implements IConditionFactory{
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> PEEXConfig.recipes.isGradeZero;
	}
}
