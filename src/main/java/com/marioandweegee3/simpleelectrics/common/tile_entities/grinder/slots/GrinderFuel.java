package com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.slots;

import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderTile;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GrinderFuel extends SlotItemHandler{
    public GrinderFuel(ItemStackHandler inventory, int slotIndex, int xPos, int yPos){
        super(inventory, slotIndex, xPos, yPos);
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return GrinderTile.isItemFuel(stack);
    }
}