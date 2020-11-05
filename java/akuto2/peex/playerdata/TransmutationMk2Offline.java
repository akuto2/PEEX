package akuto2.peex.playerdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cpw.mods.fml.common.FMLCommonHandler;
import moze_intel.projecte.utils.PELogger;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.DimensionManager;

public class TransmutationMk2Offline {
	private static Map<UUID, List<ItemStack>> cachedKnowledge;
	private static Map<UUID, Float> cachedEmc = Maps.newHashMap();
	private static Map<UUID, Boolean> cachedFullKnowoledge = Maps.newHashMap();

	public static void cleanAll() {
		cachedKnowledge.clear();
		cachedEmc.clear();
		cachedFullKnowoledge.clear();
	}

	public static void clear(UUID playerUUID) {
		cachedKnowledge.remove(playerUUID);
		cachedEmc.remove(playerUUID);
		cachedFullKnowoledge.remove(playerUUID);
	}

	public static List<ItemStack> getKnowledge(UUID playerUUID){
		if(!cachedKnowledge.containsKey(playerUUID)) {
			cacheOfflineData(playerUUID);
		}
		return (List)cachedKnowledge.get(playerUUID);
	}

	private static void cacheOfflineData(UUID playerUUID) {
		Preconditions.checkState(FMLCommonHandler.instance().getEffectiveSide().isServer(), "CRITICAL: Trying to read filesystem on client!");
		File playerData = new File(DimensionManager.getCurrentSaveRootDirectory(), "playerData");
		if(playerData.exists()) {
			File player = new File(playerData, playerUUID.toString() + ".dat");
			if((player.exists()) && (player.isFile())) {
				try {
					NBTTagCompound props = CompressedStreamTools.readCompressed(new FileInputStream(player)).getCompoundTag("TransmutationMk2");
					cachedEmc.put(playerUUID, Float.valueOf(props.getFloat("transumtationMk2EMC")));
					cachedFullKnowoledge.put(playerUUID, Boolean.valueOf(props.getBoolean("tome")));

					List<ItemStack> knowledge = Lists.newArrayList();
					NBTTagList list = props.getTagList("knowledge", 10);
					for(int i = 0; i < list.tagCount(); i++) {
						ItemStack item = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
						if(item != null) {
							knowledge.add(item);
						}
					}
					cachedKnowledge.put(playerUUID, knowledge);
					PELogger.logDebug("Caching offline data for UUID: %s", new Object[] {playerUUID});
				}
				catch(IOException e) {
					PELogger.logWarn("Failed to cache offline data for API calls UUID: %s", new Object[] {playerUUID});
				}
			}
		}
	}
}
