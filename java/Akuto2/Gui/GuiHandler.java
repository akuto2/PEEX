package Akuto2.Gui;

import Akuto2.Container.ContainerCollectorFinal;
import Akuto2.Container.ContainerCollectorMk10;
import Akuto2.Container.ContainerCollectorMk6;
import Akuto2.Container.ContainerCollectorMk7;
import Akuto2.Container.ContainerCollectorMk8;
import Akuto2.Container.ContainerCollectorMk9;
import Akuto2.Container.ContainerCondenserGrade0;
import Akuto2.Container.ContainerCondenserMk3;
import Akuto2.Container.ContainerRelayFinal;
import Akuto2.Container.ContainerRelayMk4;
import Akuto2.Container.ContainerRelayMk5;
import Akuto2.Container.ContainerTransmutationMk2;
import Akuto2.Container.Inventory.TransmutationMk2Inventory;
import Akuto2.TileEntity.TileEntityCollectorFinal;
import Akuto2.TileEntity.TileEntityCollectorMk10;
import Akuto2.TileEntity.TileEntityCollectorMk6;
import Akuto2.TileEntity.TileEntityCollectorMk7;
import Akuto2.TileEntity.TileEntityCollectorMk8;
import Akuto2.TileEntity.TileEntityCollectorMk9;
import Akuto2.TileEntity.TileEntityCondenserGrade0;
import Akuto2.TileEntity.TileEntityCondenserMk3;
import Akuto2.TileEntity.TileEntityRelayFinal;
import Akuto2.TileEntity.TileEntityRelayMk4;
import Akuto2.TileEntity.TileEntityRelayMk5;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		switch(ID){
		case 0:
		if((tileEntity instanceof TileEntityCondenserGrade0)){
			return new ContainerCondenserGrade0(player.inventory, (TileEntityCondenserGrade0)tileEntity);
		}
		break;
		case 1:
		if((tileEntity instanceof TileEntityCondenserMk3)){
			return new ContainerCondenserMk3(player.inventory, (TileEntityCondenserMk3)tileEntity);
		}
		break;
		case 2:
		if((tileEntity instanceof TileEntityCollectorMk6)){
			return new ContainerCollectorMk6(player.inventory, (TileEntityCollectorMk6)tileEntity);
		}
		break;
		case 3:
		if((tileEntity instanceof TileEntityCollectorMk7)){
			return new ContainerCollectorMk7(player.inventory, (TileEntityCollectorMk7)tileEntity);
		}
		break;
		case 4:
		if((tileEntity instanceof TileEntityCollectorMk8)){
			return new ContainerCollectorMk8(player.inventory, (TileEntityCollectorMk8)tileEntity);
		}
		break;
		case 5:
		if((tileEntity instanceof TileEntityCollectorMk9)){
			return new ContainerCollectorMk9(player.inventory, (TileEntityCollectorMk9)tileEntity);
		}
		break;
		case 6:
		if((tileEntity instanceof TileEntityCollectorMk10)){
			return new ContainerCollectorMk10(player.inventory, (TileEntityCollectorMk10)tileEntity);
		}
		break;
		case 7:
		if((tileEntity instanceof TileEntityRelayMk4)){
			return new ContainerRelayMk4(player.inventory, (TileEntityRelayMk4)tileEntity);
		}
		break;
		case 8:
		if((tileEntity instanceof TileEntityRelayMk5)){
			return new ContainerRelayMk5(player.inventory, (TileEntityRelayMk5)tileEntity);
		}
		break;
		case 9:
		if((tileEntity instanceof TileEntityCollectorFinal)){
			return new ContainerCollectorFinal(player.inventory, (TileEntityCollectorFinal)tileEntity);
		}
		break;
		case 10:
		if((tileEntity instanceof TileEntityRelayFinal)){
			return new ContainerRelayFinal(player.inventory, (TileEntityRelayFinal)tileEntity);
		}
		break;
		case 11:
			return new ContainerTransmutationMk2(player.inventory, new TransmutationMk2Inventory(player));
		}
		return null;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
		TileEntity tileentity = world.getTileEntity(x, y, z);
		switch(ID){
		case 0:
		if(tileentity instanceof TileEntityCondenserGrade0){
			return new GuiCondenserGrade0(player.inventory, (TileEntityCondenserGrade0)tileentity);
		}
		break;
		case 1:
		if((tileentity instanceof TileEntityCondenserMk3)){
			return new GuiCondenserMk3(player.inventory, (TileEntityCondenserMk3)tileentity);
		}
		break;
		case 2:
		if((tileentity instanceof TileEntityCollectorMk6)){
			return new GuiCollectorMK6(player.inventory, (TileEntityCollectorMk6)tileentity);
		}
		break;
		case 3:
		if((tileentity instanceof TileEntityCollectorMk7)){
			return new GuiCollectorMK7(player.inventory, (TileEntityCollectorMk7)tileentity);
		}
		break;
		case 4:
		if((tileentity instanceof TileEntityCollectorMk8)){
			return new GuiCollectorMK8(player.inventory, (TileEntityCollectorMk8)tileentity);
		}
		break;
		case 5:
		if((tileentity instanceof TileEntityCollectorMk9)){
			return new GuiCollectorMK9(player.inventory, (TileEntityCollectorMk9)tileentity);
		}
		break;
		case 6:
		if((tileentity instanceof TileEntityCollectorMk10)){
			return new GuiCollectorMK10(player.inventory, (TileEntityCollectorMk10)tileentity);
		}
		break;
		case 7:
		if((tileentity instanceof TileEntityRelayMk4)){
			return new GuiRelayMk4(player.inventory, (TileEntityRelayMk4)tileentity);
		}
		break;
		case 8:
		if((tileentity instanceof TileEntityRelayMk5)){
			return new GuiRelayMk5(player.inventory, (TileEntityRelayMk5)tileentity);
		}
		break;
		case 9:
		if((tileentity instanceof TileEntityCollectorFinal)){
			return new GuiCollectorFinal(player.inventory, (TileEntityCollectorFinal)tileentity);
		}
		break;
		case 10:
		if((tileentity instanceof TileEntityRelayFinal)){
			return new GuiRelayFinal(player.inventory, (TileEntityRelayFinal)tileentity);
		}
		break;
		case 11:
			return new GuiTransmutationMk2(player.inventory, new TransmutationMk2Inventory(player));
		}
		return null;
	}
}
