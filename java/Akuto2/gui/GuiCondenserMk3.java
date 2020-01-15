package Akuto2.gui;

import org.lwjgl.opengl.GL11;

import Akuto2.gui.container.ContainerCondenserMk3;
import Akuto2.tiles.TileEntityCondenserMk3;
import moze_intel.projecte.gameObjs.gui.GUICondenser;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCondenserMk3 extends GUICondenser{
	private static final ResourceLocation texture = new ResourceLocation("peex", "textures/gui/condenser_mk3.png");
	private ContainerCondenserMk3 container;

	public GuiCondenserMk3(InventoryPlayer invPlayer, TileEntityCondenserMk3 tile) {
		super(invPlayer, tile);
		container = ((ContainerCondenserMk3)inventorySlots);
		xSize = 255;
		ySize = 233;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = container.getProgressScaled();
		drawTexturedModalRect(x + 33, y + 10, 0, 235, progress, 10);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		int toDisplay = container.displayEmc > container.requiredEmc ? container.requiredEmc : container.displayEmc;
		fontRendererObj.drawString(Integer.toString(toDisplay), 140, 10, 4210752);
	}
}
