package akuto2.gui;

import java.util.Arrays;

import org.lwjgl.opengl.GL11;

import akuto2.gui.container.ContainerCondenserMk3;
import akuto2.tiles.TileEntityCondenserMk3;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.TransmutationEMCFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
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
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		long toDisplay = container.displayEmc > container.requiredEmc ? container.requiredEmc : container.displayEmc;
		String emc = TransmutationEMCFormatter.EMCFormat(toDisplay);
		fontRenderer.drawString(emc, 140, 10, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);

		int progress = container.getProgressScaled();
		drawTexturedModalRect(x + 33, y + 10, 0, 235, progress, 10);
	}

	@Override
	protected void renderHoveredToolTip(int mouseX, int mouseY) {
		long toDisplay = container.displayEmc > container.requiredEmc ? container.requiredEmc : container.displayEmc;

		if(toDisplay < 1e12) {
			super.renderHoveredToolTip(mouseX, mouseY);
			return;
		}

		int emcLeft = 140 + (width - xSize) / 2;
		int emcRight = emcLeft + 110;
		int emcTop = 6 + (height - ySize) / 2;
		int emcButtom = emcTop + 15;

		if(mouseX > emcLeft && mouseX < emcRight && mouseY > emcTop && mouseY < emcButtom) {
			String emcAsString = I18n.format("pe.emc.emc_tooltip_prefix") + " " + Constants.EMC_FORMATTER.format(toDisplay);
			drawHoveringText(Arrays.asList(emcAsString), mouseX, mouseY);
		}
		else {
			super.renderHoveredToolTip(mouseX, mouseY);
		}
	}
}
