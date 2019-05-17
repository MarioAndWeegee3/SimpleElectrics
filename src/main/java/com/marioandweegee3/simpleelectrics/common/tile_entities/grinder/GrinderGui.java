package com.marioandweegee3.simpleelectrics.common.tile_entities.grinder;

import com.marioandweegee3.simpleelectrics.util.GuiHandler;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderTile;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderContainer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;

public class GrinderGui extends GuiContainer{
    private final InventoryPlayer plrInv;
    private final GrinderTile te;

    public GrinderGui(InventoryPlayer plrInv, GrinderTile te){
        super(new GrinderContainer(plrInv, te));
        this.plrInv = plrInv;
        this.te = te;
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String s = this.te.getName().getUnformattedComponentText();
        this.fontRenderer.drawString(s, this.xSize/2 - this.fontRenderer.getStringWidth(s)/2, 6, 4210752);
        this.fontRenderer.drawString(this.plrInv.getDisplayName().getUnformattedComponentText(), 8, this.ySize - 96 + 2, 4210752);
    }
    
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.getTextureManager().bindTexture(GuiHandler.GUI.GRINDER.getTexture());
        this.drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);

        if(this.te.isGrinding()){
            int k = this.getBurnLeftScaled(13);
            this.drawTexturedModalRect(this.guiLeft + 56, this.guiTop + 48 - k, 176, 12 - k, 14, k + 1);
        }

        int l = this.getCookProgressScaled(24);
        this.drawTexturedModalRect(this.guiLeft + 79, this.guiTop + 34, 176, 14, 1 + l, 16);
    }

    private int getCookProgressScaled(int pixels) {
		int i = this.te.getField(2);
		int j = this.te.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
	
	private int getBurnLeftScaled(int pixels) {
		int i = this.te.getField(1);
		if(i == 0) {
			i = 200;
		}
		
		return this.te.getField(0) * pixels / i;
    }
}