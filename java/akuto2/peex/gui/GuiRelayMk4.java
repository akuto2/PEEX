package akuto2.peex.gui;

import org.lwjgl.opengl.GL11;

import akuto2.peex.gui.container.ContainerRelayMk4;
import moze_intel.projecte.gameObjs.tiles.TileEntityRelayMk4;
import moze_intel.projecte.utils.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiRelayMk4 extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("peex", "textures/gui/relay3.png");
	private final TileEntityRelayMk4 tile;
	private final ContainerRelayMk4 container;

	public GuiRelayMk4(InventoryPlayer invPlayer, TileEntityRelayMk4 tile) {
		super(new ContainerRelayMk4(invPlayer, tile));
		this.tile = tile;
		this.container = (ContainerRelayMk4)inventorySlots;
		xSize = 212;
		ySize = 194;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(I18n.format("pe.relay.mk4"), 38, 6, 4210752);
		fontRendererObj.drawString(Constants.EMC_FORMATTER.format(container.emc), 125, 39, 4210752);
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
		drawTexturedModalRect(x + 101, y + 82, 0, 195, progress, 10);
	}
}
