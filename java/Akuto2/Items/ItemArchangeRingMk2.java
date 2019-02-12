package Akuto2.Items;

import java.util.List;

import com.google.common.collect.Lists;

import moze_intel.projecte.PECore;
import moze_intel.projecte.api.item.IPedestalItem;
import moze_intel.projecte.config.ProjectEConfig;
import moze_intel.projecte.gameObjs.entity.EntityHomingArrow;
import moze_intel.projecte.gameObjs.items.rings.RingToggle;
import moze_intel.projecte.gameObjs.tiles.DMPedestalTile;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.MathUtils;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.FakePlayerFactory;

public class ItemArchangeRingMk2 extends RingToggle implements IPedestalItem {

	public ItemArchangeRingMk2() {
		super("archangering.mk2");
		setMaxStackSize(1);
		setNoRepair();
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {
		if((!world.isRemote) && (getMode(stack) == 1) && (entity instanceof EntityLivingBase)) {
			fireArrow(stack, world, (EntityLivingBase)entity, 1);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(!world.isRemote) {
			fireArrow(stack, world, player, 1);
		}
		return stack;
	}

	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(!entityLiving.worldObj.isRemote) {
			fireArrow(stack, entityLiving.worldObj, entityLiving, 7);
		}
		return true;
	}

	@Override
	public void registerIcons(IIconRegister register) {
		itemIcon = register.registerIcon("peex:archangel_ring_mk2");
	}

	@Override
	public IIcon getIconFromDamage(int meta) {
		return itemIcon;
	}

	private void fireArrow(ItemStack stack, World world, EntityLivingBase shooter, int num) {
		EntityHomingArrow arrow = new EntityHomingArrow(world, shooter, 2.0F);
		if(!(shooter instanceof EntityPlayer) || consumeFuel((EntityPlayer)shooter, stack, EMCHelper.getEmcValue(Items.arrow) * num, true)) {
			for(int i = 0; i < num; i++) {
				world.playSoundAtEntity(shooter, "random.bow", 1.0f, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
				world.spawnEntityInWorld(arrow);
			}
		}
	}

	@Override
	public List<String> getPedestalDescription() {
		List<String> list = Lists.newArrayList();
		if (ProjectEConfig.archangelPedCooldown != -1) {
	      list.add(EnumChatFormatting.BLUE + StatCollector.translateToLocal("archangelmk2.pedestal1"));
	      list.add(EnumChatFormatting.BLUE + String.format(StatCollector.translateToLocal("archangelmk2.pedestal2"), new Object[] { MathUtils.tickToSecFormatted(ProjectEConfig.archangelPedCooldown) }));
	    }
	    return list;
	}

	@Override
	public void updateInPedestal(World world, int x, int y, int z) {
		if((!world.isRemote) && (ProjectEConfig.archangelPedCooldown != -1)) {
			DMPedestalTile tile = (DMPedestalTile)world.getTileEntity(x, y, z);
			if(tile.getActivityCooldown() == 0) {
				if(!world.getEntitiesWithinAABB(EntityLiving.class, tile.getEffectBounds()).isEmpty()) {
					for(int i = 0; i < 3; i++) {
						EntityHomingArrow arrow = new EntityHomingArrow(world, FakePlayerFactory.get((WorldServer)world, PECore.FAKEPLAYER_GAMEPROFILE), 2.0F);
						arrow.posX = tile.centeredX;
						arrow.posY = tile.centeredY + 2.0D;
						arrow.posZ = tile.centeredZ;
						arrow.motionX = 0.0D;
						arrow.motionZ = 0.0D;
						arrow.motionY = 1.0D;
						world.playSoundAtEntity(arrow, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 0.5F);
						world.spawnEntityInWorld(arrow);
					}
				}
				tile.setActivityCooldown(ProjectEConfig.archangelPedCooldown);
			}
			else {
				tile.decrementActivityCooldown();
			}
		}
	}

}
