package akuto2.peex.gui;

import org.lwjgl.opengl.GL11;

import akuto2.peex.gui.container.ContainerRelayMk5;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk5;
import moze_intel.projecte.utils.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiRelayMk5 extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("peex", "textures/gui/relay3.png");
	private final TileEntityRelayMk5 tile;
	private final ContainerRelayMk5 container;

	public GuiRelayMk5(InventoryPlayer invPlayer, TileEntityRelayMk5 tile) {
		super(new ContainerRelayMk5(invPlayer, tile));
		this.tile = tile;
		this.container = (ContainerRelayMk5)inventorySlots;
		xSize = 212;
		ySize = 194;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(I18n.format("pe.relay.mk4"), 38, 6, 4210752);
		fontRenderer.drawString(Constants.EMC_FORMATTER.format(container.emc), 125, 39, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = (int)(container.emc / tile.getMaximumEmc() * 102);
		drawTexturedModalRect(x + 105, y + 6, 30, 195, progress, 10);

		progress = (int)(container.kleinChargeProgress * 30);
		drawTexturedModalRect(x + 153, y + 82, 0, 195, progress, 10);

		progress = (int)(container.inputBurnProgress * 30);
		drawTexturedModalRect(x + 102, y + 82, 0, 195, progress, 10);
	}
}
