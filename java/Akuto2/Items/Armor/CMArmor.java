package Akuto2.Items.Armor;

import Akuto2.ObjHandlerPEEX;
import Akuto2.PEEXCore;
import Akuto2.Utils.EnumArmorType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class CMArmor extends ItemArmor{
	private final EnumArmorType armorType;
	public CMArmor(EnumArmorType type) {
		super(ObjHandlerPEEX.CMMaterial, 0, type.ordinal());
		armorType = type;
		setHasSubtypes(false);
		setCreativeTab(PEEXCore.tabPEEX);
		setUnlocalizedName("cm_armor_" + armorType.name.toLowerCase());
		setTextureName("peex:cm_armor/" + armorType.name.toLowerCase() + ".png");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
		char index = (armorType == EnumArmorType.FEET) ? '2' : '1';
		return "peex:textures/models/cm_armor" + index + ".png";
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		super.setDamage(stack, 0);
	}
}
