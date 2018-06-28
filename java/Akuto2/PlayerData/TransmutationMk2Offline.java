package Akuto2.PlayerData;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Maps;

import net.minecraft.item.ItemStack;

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

	}
}
