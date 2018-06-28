package Akuto2.Gui;

import org.lwjgl.opengl.GL11;

import Akuto2.Container.ContainerCondenserGrade0;
import Akuto2.TileEntity.TileEntityCondenserGrade0;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCondenserGrade0 extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("PEEX".toLowerCase(), "textures/gui/condenser_grade0.png");
	private TileEntityCondenserGrade0 tile;

	public GuiCondenserGrade0(InventoryPlayer invPlayer, TileEntityCondenserGrade0 tile){
		super(new ContainerCondenserGrade0(invPlayer, tile));
		this.tile = tile;
		xSize = 255;
		ySize = 255;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3){
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		int x = (width - xSize) / 2;
		int y = (height - ySize) / 2;

		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
}
