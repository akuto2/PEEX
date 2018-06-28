package Akuto2.PlayerData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import Akuto2.Packet.PEEXKnowledgeSyncPKT;
import Akuto2.Packet.PEEXPacketHandler;
import moze_intel.projecte.api.event.PlayerKnowledgeChangeEvent;
import moze_intel.projecte.emc.EMCMapper;
import moze_intel.projecte.emc.SimpleStack;
import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import moze_intel.projecte.utils.PELogger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

public class TransmutationMk2 {
	private static final List<ItemStack> CACHED_TOME_KNOWLEDGE = new ArrayList();

	public static void clearCache() {
		CACHED_TOME_KNOWLEDGE.clear();
	}

	public static void cacheFullKnowledge() {
		for(SimpleStack stack : EMCMapper.emc.keySet()) {
			if(stack.isValid()) {
				try {
					ItemStack s = stack.toItemStack();
					s.stackSize = 1;
					if((EMCHelper.doesItemHaveEmc(s)) && (EMCHelper.getEmcValue(s) > 0) && (!ItemHelper.containsItemStack(CACHED_TOME_KNOWLEDGE, s))) {
						CACHED_TOME_KNOWLEDGE.add(s);
					}
				}
				catch(Exception e) {
					PELogger.logInfo("Failed to cache knowledge for " + stack + ": " + e.toString());
				}
			}
		}
	}

	public static List<ItemStack> getKowledge(EntityPlayer player){
		TransmutationMk2Props data = TransmutationMk2Props.getDataFor(player);
		return data.getKnowledge();
	}

	public static void addKnowledge(ItemStack stack, EntityPlayer player) {
		TransmutationMk2Props data = TransmutationMk2Props.getDataFor(player);
		if(!hasKnowlefgeForStack(stack, player)) {
			data.getKnowledge().add(stack);
			if(!player.worldObj.isRemote) {
				MinecraftForge.EVENT_BUS.post(new PlayerKnowledgeChangeEvent(player));
			}
		}
	}

	public static void removeKnowledge(ItemStack stack, EntityPlayer player) {
		TransmutationMk2Props data = TransmutationMk2Props.getDataFor(player);
		if(hasKnowlefgeForStack(stack, player)) {
			Iterator<ItemStack> iter = data.getKnowledge().iterator();
			while(iter.hasNext()) {
				if(ItemStack.areItemStacksEqual(stack, (ItemStack)iter.next())) {
					iter.remove();
					if(!player.worldObj.isRemote) {
						MinecraftForge.EVENT_BUS.post(new PlayerKnowledgeChangeEvent(player));
					}
				}
			}
		}
	}

	public static void setInputAndLocks(ItemStack[] stacks, EntityPlayer player) {
		TransmutationMk2Props data = TransmutationMk2Props.getDataFor(player);
		data.setInputLocks(stacks);
	}

	public static ItemStack[] getInputAndLock(EntityPlayer player) {
		ItemStack[] locks = TransmutationMk2Props.getDataFor(player).getInputLocks();
		return (ItemStack[])Arrays.copyOf(locks, locks.length);
	}

	public static boolean hasKnowlefgeForStack(ItemStack stack, EntityPlayer player) {
		TransmutationMk2Props data = TransmutationMk2Props.getDataFor(player);
		for(ItemStack s : data.getKnowledge()) {
			if(ItemHelper.basicAreStacksEqual(s, stack)) {
				return true;
			}
		}
		return false;
	}

	public static void setFullKnowledge(EntityPlayer player) {
		TransmutationMk2Props.getDataFor(player).getKnowledge().clear();
		TransmutationMk2Props.getDataFor(player).getKnowledge().addAll(CACHED_TOME_KNOWLEDGE);
		if(!player.worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new PlayerKnowledgeChangeEvent(player));
		}
	}

	public static void clearKnowledge(EntityPlayer player) {
		TransmutationMk2Props data = TransmutationMk2Props.getDataFor(player);
		data.getKnowledge().clear();
		if(!player.worldObj.isRemote) {
			MinecraftForge.EVENT_BUS.post(new PlayerKnowledgeChangeEvent(player));
		}
	}

	public static float getEmc(EntityPlayer player) {
		return TransmutationMk2Props.getDataFor(player).getTransmutaionEmc();
	}

	public static void setEmc(EntityPlayer player, float emc) {
		TransmutationMk2Props.getDataFor(player).setTransmutaionEmc(emc);
	}

	public static void sync(EntityPlayer player) {
		PEEXPacketHandler.sendTo(new PEEXKnowledgeSyncPKT(TransmutationMk2Props.getDataFor(player).saveForPacket()), (EntityPlayerMP)player);
	}
}
