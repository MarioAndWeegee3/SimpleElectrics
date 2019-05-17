package com.marioandweegee3.simpleelectrics.util;

import com.marioandweegee3.simpleelectrics.SimpleElectrics;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderGui;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderTile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.FMLPlayMessages;

public class GuiHandler{
    public static GuiScreen openGui(FMLPlayMessages.OpenContainer openContainer){
        BlockPos pos = openContainer.getAdditionalData().readBlockPos();
        EntityPlayerSP player = Minecraft.getInstance().player;

        if(openContainer.getId().equals(new ResourceLocation(SimpleElectrics.modid, "grinder"))){
            return new GrinderGui(player.inventory, (GrinderTile)Minecraft.getInstance().world.getTileEntity(pos));
        }

        return null;
    }

    public static enum GUI{
        GRINDER("simpleelectrics:grinder", new ResourceLocation(SimpleElectrics.modid, "textures/gui/grinder.png"));

        private ResourceLocation texture;
        private String guiId;

        GUI(String guiId, ResourceLocation texture){
            this.guiId = guiId;
            this.texture = texture;
        }

        public String getGuiID(){
            return this.guiId;
        }

        public ResourceLocation getTexture(){
            return this.texture;
        }
    }
}