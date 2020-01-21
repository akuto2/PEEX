package Akuto2.gui;

import org.lwjgl.opengl.GL11;

import Akuto2.gui.container.ContainerCondenserGrade0;
import Akuto2.tiles.TIleEntityCondenserGrade0;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCondenserGrade0 extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("peex", "textures/gui/condenser_grade0.png");
	private TIleEntityCondenserGrade0 tile;

	public GuiCondenserGrade0(InventoryPlayer invPlayer, TIleEntityCondenserGrade0 tile) {
		super(new ContainerCondenserGrade0(invPlayer, tile));
		this.tile = tile;
		xSize = 255;
		ySize = 255;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
