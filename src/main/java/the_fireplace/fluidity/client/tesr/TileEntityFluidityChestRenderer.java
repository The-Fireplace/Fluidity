package the_fireplace.fluidity.client.tesr;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import the_fireplace.fluidity.blocks.BlockFluidityIronChest;
import the_fireplace.fluidity.compat.FluidityIronChests;
import the_fireplace.fluidity.tileentity.TileEntityFluidityIronChest;
import the_fireplace.fluidity.enums.FluidityIronChestType;

@SideOnly(Side.CLIENT)
public class TileEntityFluidityChestRenderer extends TileEntitySpecialRenderer<TileEntityFluidityIronChest> {
	private ModelChest model = new ModelChest();
	private static float halfPI = 1.5707964F;

	public TileEntityFluidityChestRenderer() {
	}

	@Override
	public void renderTileEntityAt(TileEntityFluidityIronChest tile, double x, double y, double z, float partialTick, int breakStage) {
		if(tile != null && !tile.isInvalid()) {
			EnumFacing facing = EnumFacing.SOUTH;
			FluidityIronChestType type = tile.getType();
			if(tile.hasWorldObj() && tile.getWorld().getBlockState(tile.getPos()).getBlock() == FluidityIronChests.fluidityChest) {
				facing = tile.getFacing();
				IBlockState chest = tile.getWorld().getBlockState(tile.getPos());
				type = chest.getValue(BlockFluidityIronChest.VARIANT_PROP);
			}

			if(breakStage >= 0) {
				this.bindTexture(DESTROY_STAGES[breakStage]);
				GlStateManager.matrixMode(5890);
				GlStateManager.pushMatrix();
				GlStateManager.scale(4.0F, 4.0F, 1.0F);
				GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
				GlStateManager.matrixMode(5888);
			} else {
				this.bindTexture(type.modelTexture);
			}

			GlStateManager.pushMatrix();

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.translate((float)x, (float)y + 1.0F, (float)z + 1.0F);
			GlStateManager.scale(1.0F, -1.0F, -1.0F);
			GlStateManager.translate(0.5F, 0.5F, 0.5F);
			switch(facing) {
				case NORTH:
					GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
					break;
				case SOUTH:
					GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
					break;
				case WEST:
					GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
					break;
				case EAST:
					GlStateManager.rotate(270.0F, 0.0F, 1.0F, 0.0F);
					break;
				default:
					GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
			}

			GlStateManager.translate(-0.5F, -0.5F, -0.5F);
			float next = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partialTick;
			next = 1.0F - next;
			next = 1.0F - next * next * next;

			this.model.chestLid.rotateAngleX = -next * halfPI;
			this.model.renderAll();
			if(breakStage >= 0) {
				GlStateManager.matrixMode(5890);
				GlStateManager.popMatrix();
				GlStateManager.matrixMode(5888);
			}

			GlStateManager.popMatrix();
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
