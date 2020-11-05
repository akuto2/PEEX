package akuto2.peex.playerdata;

import java.util.Iterator;
import java.util.List;

import com.google.common.collect.Lists;

import moze_intel.projecte.utils.EMCHelper;
import moze_intel.projecte.utils.ItemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

public class TransmutationMk2Props implements IExtendedEntityProperties{

	private final EntityPlayer player;
	private float transmutaionEmc;
	private List<ItemStack> knowledge = Lists.newArrayList();
	private ItemStack[] inputLocks = new ItemStack[9];

	public static void register(EntityPlayer player) {
		player.registerExtendedProperties("TransmutationMk2", new TransmutationMk2Props(player));
	}

	public static TransmutationMk2Props getDataFor(EntityPlayer player) {
		return (TransmutationMk2Props)player.getExtendedProperties("TransmutationMk2");
	}

	public TransmutationMk2Props(EntityPlayer player) {
		this.player = player;
	}

	public ItemStack[] getInputLocks() {
		return inputLocks;
	}

	public void setInputLocks(ItemStack[] inputLocks) {
		this.inputLocks = inputLocks;
	}

	public float getTransmutaionEmc() {
		return transmutaionEmc;
	}

	public void setTransmutaionEmc(float transmutaionEmc) {
		this.transmutaionEmc = transmutaionEmc;
	}

	public List<ItemStack> getKnowledge() {
		pruneStaleKnowledge();
		return knowledge;
	}

	private void pruneDuplicateKnowledge() {
		ItemHelper.compactItemListNoStacksize(knowledge);
		for(ItemStack s : knowledge) {
			if(s.stackSize > 2) {
				s.stackSize = 1;
			}
		}
	}

	private void pruneStaleKnowledge() {
		Iterator<ItemStack> iter = knowledge.iterator();
		while(iter.hasNext()) {
			if(!EMCHelper.doesItemHaveEmc((ItemStack)iter.next())) {
				iter.remove();
			}
		}
	}

	protected NBTTagCompound saveForPacket() {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setFloat("transumtationMk2EMC", transmutaionEmc);

		pruneStaleKnowledge();
		NBTTagList knowledgeWrite = new NBTTagList();
		for(ItemStack i : knowledge) {
			NBTTagCompound tag = i.writeToNBT(new NBTTagCompound());
			knowledgeWrite.appendTag(tag);
		}
		NBTTagList inputLockWrite = ItemHelper.toIndexedNBTList(inputLocks);
		compound.setTag("knowledge", knowledgeWrite);
		compound.setTag("inputLocks", inputLockWrite);
		return compound;
	}

	public void readFromPacket(NBTTagCompound compound) {
		transmutaionEmc = compound.getFloat("transumtationMk2EMC");

		NBTTagList list = compound.getTagList("knowledge", 10);
		knowledge.clear();
		for(int i = 0; i < list.tagCount(); i++) {
			ItemStack stack = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
			if(stack != null) {
				knowledge.add(stack);
			}
		}
		NBTTagList list2 = compound.getTagList("inputLocks", 10);
		inputLocks = ItemHelper.copyIndexedNBTToArray(list2, new ItemStack[9]);
	}

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();
		properties.setFloat("transumutationMk2Emc", transmutaionEmc);

		pruneStaleKnowledge();
		NBTTagList knowledgeWrite = new NBTTagList();
		for(ItemStack i : knowledge) {
			NBTTagCompound tag = i.writeToNBT(new NBTTagCompound());
			knowledgeWrite.appendTag(tag);
		}
		NBTTagList inputLockWrite = ItemHelper.toIndexedNBTList(inputLocks);
		properties.setTag("knowledge", knowledgeWrite);
		properties.setTag("inputlock", inputLockWrite);
		compound.setTag("TransmutationMk2", properties);
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		NBTTagCompound properties = compound.getCompoundTag("TransmutationMk2");

		transmutaionEmc = properties.getFloat("transumutationMk2Emc");

		NBTTagList list = properties.getTagList("knowledge", 10);
		for(int i = 0; i < list.tagCount(); i++) {
			ItemStack itemStack = ItemStack.loadItemStackFromNBT(list.getCompoundTagAt(i));
			if(itemStack != null) {
				knowledge.add(itemStack);
			}
		}
		pruneDuplicateKnowledge();
		NBTTagList list2 = properties.getTagList("inputlock", 10);
		inputLocks = ItemHelper.copyIndexedNBTToArray(list2, new ItemStack[9]);
	}

	@Override
	public void init(Entity entity, World world) {

	}

}
