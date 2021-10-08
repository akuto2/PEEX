package akuto2.peex.items.armor;

import akuto2.peex.ObjHandlerPEEX;
import akuto2.peex.PEEXCore;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemBMArmor extends ItemArmor{
	private final EntityEquipmentSlot armorType;

	public ItemBMArmor(EntityEquipmentSlot equipmentSlotIn) {
		super(ObjHandlerPEEX.bmMaterial, 0, equipmentSlotIn);
		armorType = equipmentSlotIn;
		setHasSubtypes(false);
		setCreativeTab(PEEXCore.peexTab);
		setUnlocalizedName("bm_armor_" + equipmentSlotIn.getName());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		char index = (armorType == EntityEquipmentSlot.LEGS) ? '2' : '1';
		return "peex:textures/models/bm_armor" + index + ".png";
	}

	@Override
	public void setDamage(ItemStack stack, int damage) {
		super.setDamage(stack, 0);
	}

}
