package Akuto2.Events;

import Akuto2.PlayerData.TransmutationMk2Props;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class PlayerEvents {
	@SubscribeEvent
	public void cloneEvent(PlayerEvent.Clone event) {
		if(!event.wasDeath) {
			return;
		}
		NBTTagCompound transmute = new NBTTagCompound();

		TransmutationMk2Props.getDataFor(event.original).saveNBTData(transmute);

		TransmutationMk2Props.getDataFor(event.entityPlayer).loadNBTData(transmute);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if((event.entity instanceof EntityPlayer) && (!(event.entity instanceof FakePlayer))) {
			EntityPlayerMP playerMP = (EntityPlayerMP)event.entity;
			TransmutationMk2Props.register(playerMP);
		}
	}
}
