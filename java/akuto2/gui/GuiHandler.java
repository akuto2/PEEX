package akuto2.gui;

import akuto2.gui.container.ContainerCollectorFinal;
import akuto2.gui.container.ContainerCollectorMk10;
import akuto2.gui.container.ContainerCollectorMk4;
import akuto2.gui.container.ContainerCollectorMk5;
import akuto2.gui.container.ContainerCollectorMk6;
import akuto2.gui.container.ContainerCollectorMk7;
import akuto2.gui.container.ContainerCollectorMk8;
import akuto2.gui.container.ContainerCollectorMk9;
import akuto2.tiles.TileEntityCollectorFinal;
import akuto2.tiles.TileEntityCollectorMk10;
import akuto2.tiles.TileEntityCollectorMk4;
import akuto2.tiles.TileEntityCollectorMk5;
import akuto2.tiles.TileEntityCollectorMk6;
import akuto2.tiles.TileEntityCollectorMk7;
import akuto2.tiles.TileEntityCollectorMk8;
import akuto2.tiles.TileEntityCollectorMk9;
import akuto2.utils.Constants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
		case Constants.CONDENSER_MK3_GUI:
			break;
		case Constants.CONDENSER_GRADE_0_GUI:
			break;
		case Constants.COLLECTOR_MK4_GUI:
			if(tile instanceof TileEntityCollectorMk4)
				return new GuiCollectorMk4(player.inventory, (TileEntityCollectorMk4)tile);
			break;
		case Constants.COLLECTOR_MK5_GUI:
			if(tile instanceof TileEntityCollectorMk5)
				return new GuiCollectorMk5(player.inventory, (TileEntityCollectorMk5)tile);
			break;
		case Constants.COLLECTOR_MK6_GUI:
			if(tile instanceof TileEntityCollectorMk6)
				return new GuiCollectorMk6(player.inventory, (TileEntityCollectorMk6)tile);
			break;
		case Constants.COLLECTOR_MK7_GUI:
			if(tile instanceof TileEntityCollectorMk7)
				return new GuiCollectorMk7(player.inventory, (TileEntityCollectorMk7)tile);
			break;
		case Constants.COLLECTOR_MK8_GUI:
			if(tile instanceof TileEntityCollectorMk8)
				return new GuiCollectorMk8(player.inventory, (TileEntityCollectorMk8)tile);
			break;
		case Constants.COLLECTOR_MK9_GUI:
			if(tile instanceof TileEntityCollectorMk9)
				return new GuiCollectorMk9(player.inventory, (TileEntityCollectorMk9)tile);
			break;
		case Constants.COLLECTOR_MK10_GUI:
			if(tile instanceof TileEntityCollectorMk10)
				return new GuiCollectorMk10(player.inventory, (TileEntityCollectorMk10)tile);
			break;
		case Constants.COLLECTOR_FINAL_GUI:
			if(tile instanceof TileEntityCollectorFinal)
				return new GuiCollectorFinal(player.inventory, (TileEntityCollectorFinal)tile);
			break;
		case Constants.RELAY_MK4_GUI:
			break;
		case Constants.RELAY_MK5_GUI:
			break;
		case Constants.RELAY_FINAL_GUI:
			break;
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		switch(ID) {
		case Constants.CONDENSER_MK3_GUI:
			break;
		case Constants.CONDENSER_GRADE_0_GUI:
			break;
		case Constants.COLLECTOR_MK4_GUI:
			if(tile instanceof TileEntityCollectorMk4)
				return new ContainerCollectorMk4(player.inventory, (TileEntityCollectorMk4)tile);
			break;
		case Constants.COLLECTOR_MK5_GUI:
			if(tile instanceof TileEntityCollectorMk5)
				return new ContainerCollectorMk5(player.inventory, (TileEntityCollectorMk5)tile);
			break;
		case Constants.COLLECTOR_MK6_GUI:
			if(tile instanceof TileEntityCollectorMk6)
				return new ContainerCollectorMk6(player.inventory, (TileEntityCollectorMk6)tile);
			break;
		case Constants.COLLECTOR_MK7_GUI:
			if(tile instanceof TileEntityCollectorMk7)
				return new ContainerCollectorMk7(player.inventory, (TileEntityCollectorMk7)tile);
			break;
		case Constants.COLLECTOR_MK8_GUI:
			if(tile instanceof TileEntityCollectorMk8)
				return new ContainerCollectorMk8(player.inventory, (TileEntityCollectorMk8)tile);
			break;
		case Constants.COLLECTOR_MK9_GUI:
			if(tile instanceof TileEntityCollectorMk9)
				return new ContainerCollectorMk9(player.inventory, (TileEntityCollectorMk9)tile);
			break;
		case Constants.COLLECTOR_MK10_GUI:
			if(tile instanceof TileEntityCollectorMk10)
				return new ContainerCollectorMk10(player.inventory, (TileEntityCollectorMk10)tile);
			break;
		case Constants.COLLECTOR_FINAL_GUI:
			if(tile instanceof TileEntityCollectorFinal)
				return new ContainerCollectorFinal(player.inventory, (TileEntityCollectorFinal)tile);
			break;
		case Constants.RELAY_MK4_GUI:
			break;
		case Constants.RELAY_MK5_GUI:
			break;
		case Constants.RELAY_FINAL_GUI:
			break;
		}
		return null;
	}
}
