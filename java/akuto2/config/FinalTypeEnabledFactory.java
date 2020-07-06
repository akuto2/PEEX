package akuto2.config;

import java.util.function.BooleanSupplier;

import com.google.gson.JsonObject;

import net.minecraftforge.common.crafting.IConditionFactory;
import net.minecraftforge.common.crafting.JsonContext;

/**
 * 最終型のレシピを追加するかどうかをjsonに送るクラス
 */
public class FinalTypeEnabledFactory implements IConditionFactory{
	@Override
	public BooleanSupplier parse(JsonContext context, JsonObject json) {
		return () -> PEEXConfig.recipes.isFinalType;
	}
}
