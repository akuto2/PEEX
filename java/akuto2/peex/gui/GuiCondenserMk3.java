package akuto2.peex.gui;

import org.lwjgl.opengl.GL11;

import akuto2.peex.gui.container.ContainerCondenserMk3;
import akuto2.peex.tiles.TileEntityCondenserMk3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCondenserMk3 extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("peex", "textures/gui/condenser_mk3.png");
	private ContainerCondenserMk3 container;

	public GuiCondenserMk3(InventoryPlayer invPlayer, TileEntityCondenserMk3 tile) {
		super(new ContainerCondenserMk3(invPlayer, tile));
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