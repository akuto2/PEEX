package akuto2.peex.rendering;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import akuto2.peex.tile.TileEntityCondenserGrade0;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.ForgeDirection;

@SideOnly(Side.CLIENT)
public class CondenserGrade0Render extends TileEntitySpecialRenderer{
	private final ResourceLocation texture = new ResourceLocation("PEEX".toLowerCase(), "textures/blocks/condenser_grade0.png");
	private final ModelChest model = new ModelChest();

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float var8)
	{
		if (!(tile instanceof TileEntityCondenserGrade0))
		{
			return;
		}

		TileEntityCondenserGrade0 condenser = (TileEntityCondenserGrade0) tile;
		ForgeDirection direction = null;

		if (condenser.getWorldObj() != null)
		{
			direction = condenser.getOrientation();
		}

		this.bindTexture(texture);
		GL11.glPushMatrix();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
		GL11.glScalef(1.0F, -1.0F, -1.0F);
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		short angle = 0;

		if (direction != null)
		{
			if (direction == ForgeDirection.NORTH)
			{
				angle = 180;
			}
			else if (direction == ForgeDirection.SOUTH)
			{
				angle = 0;
			}
			else if (direction == ForgeDirection.WEST)
			{
				angle = 90;
			}
			else if (direction == ForgeDirection.EAST)
			{
				angle = -90;
			}
		}

		GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		float adjustedLidAngle = condenser.prevLidAngle + (condenser.lidAngle - condenser.prevLidAngle) * var8;
		adjustedLidAngle = 1.0F - adjustedLidAngle;
		adjustedLidAngle = 1.0F - adjustedLidAngle * adjustedLidAngle * adjustedLidAngle;
		model.chestLid.rotateAngleX = -(adjustedLidAngle * (float) Math.PI / 2.0F);
		model.renderAll();
		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
