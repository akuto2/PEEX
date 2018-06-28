package Akuto2.Gui;

import org.lwjgl.opengl.GL11;

import Akuto2.Container.ContainerTransmutationMk2;
import Akuto2.Container.Inventory.TransmutationMk2Inventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiTransmutationMk2 extends GuiContainer{
	private static final ResourceLocation texture = new ResourceLocation("projecte:textures/gui/transmute.png");
	TransmutationMk2Inventory inv;
	private GuiTextField textBoxFilter;
	int xLocation;
	int yLocation;

	public GuiTransmutationMk2(InventoryPlayer player, TransmutationMk2Inventory inventory) {
		super(new ContainerTransmutationMk2(player, inventory));
		inv = inventory;
		xSize = 228;
		ySize = 196;
	}

	@Override
	public void initGui() {
		super.initGui();

		xLocation = ((width - xSize) / 2);
		yLocation = ((height - ySize) / 2);

		textBoxFilter = new GuiTextField(fontRendererObj, xLocation + 88, yLocation + 8, 45, 10);
		textBoxFilter.setText(inv.Filter);

		buttonList.add(new GuiButton(1, xLocation + 125, yLocation + 100, 14, 14, "<"));
		buttonList.add(new GuiButton(2, xLocation + 193, yLocation + 100, 14, 14, ">"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		textBoxFilter.drawTextBox();
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int var1, int var2) {
	    this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.transmute"), 6, 8, 4210752);
	    String emc = String.format(StatCollector.translateToLocal("pe.emc.emc_tooltip_prefix") + " %,d", new Object[] { Float.valueOf((int)this.inv.emc) });
	    this.fontRendererObj.drawString(emc, 6, this.ySize - 94, 4210752);
	    if (this.inv.learnFlag > 0)
	    {
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned0"), 98, 30, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned1"), 99, 38, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned2"), 100, 46, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned3"), 101, 54, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned4"), 102, 62, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned5"), 103, 70, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned6"), 104, 78, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.learned7"), 107, 86, 4210752);

	      this.inv.learnFlag -= 1;
	    }
	    if (this.inv.unlearnFlag > 0)
	    {
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned0"), 97, 22, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned1"), 98, 30, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned2"), 99, 38, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned3"), 100, 46, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned4"), 101, 54, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned5"), 102, 62, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned6"), 103, 70, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned7"), 104, 78, 4210752);
	      this.fontRendererObj.drawString(StatCollector.translateToLocal("pe.transmutation.unlearned8"), 107, 86, 4210752);

	      this.inv.unlearnFlag -= 1;
	    }
	}
}
