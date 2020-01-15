package Akuto2.gui;

import org.lwjgl.opengl.GL11;

import Akuto2.gui.container.ContainerCollectorMk10;
import Akuto2.tiles.TileEntityCollectorMk10;
import moze_intel.projecte.utils.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCollectorMk10 extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("peex", "textures/gui/collector3.png");
	private final TileEntityCollectorMk10 tile;
	private final ContainerCollectorMk10 container;

	public GuiCollectorMk10(InventoryPlayer invPlayer, TileEntityCollectorMk10 collector) {
		super(new ContainerCollectorMk10(invPlayer, collector));
		tile = collector;
		container = ((ContainerCollectorMk10)inventorySlots);
		xSize = 218;
		ySize = 165;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(Integer.toString(container.emc), 91, 32, 4210752);

		double kleinCharge = container.kleinEmc;

		if(kleinCharge > 0)
			fontRendererObj.drawString(Constants.EMC_FORMATTER.format(kleinCharge), 91, 44, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = (int)(container.sunLevel * 12.0 / 16);
		drawTexturedModalRect(x + 160, y + 49 - progress, 220, 13 - progress, 12, progress);

		drawTexturedModalRect(x + 98, y + 18, 0, 166, (int)(container.emc / tile.getMaximumEmc() * 48), 10);

		progress = (int)(container.kleinChargeProgress * 48);
		drawTexturedModalRect(x + 98, y + 58, 0, 166, progress, 10);

		progress = (int)(container.fuelProgress * 24);
		drawTexturedModalRect(x + 172, y + 55 - progress, 219, 38 - progress, 10, progress + 1);
	}
}
