package com.marioandweegee3.simpleelectrics.common.items.tool_types;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTiered;
import net.minecraft.item.ItemTool;

public class Hammer extends ItemTiered{
    public Hammer(IItemTier tier, Item.Properties builder){
        super(tier, builder);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        itemStack.attemptDamageItem(1, new Random(), null);
        return new ItemStack(itemStack.getItem());
    }
}