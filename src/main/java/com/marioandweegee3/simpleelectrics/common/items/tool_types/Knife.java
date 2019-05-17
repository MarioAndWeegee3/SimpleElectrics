package com.marioandweegee3.simpleelectrics.common.items.tool_types;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

public class Knife extends ItemSword{
    public Knife(IItemTier tier, int attackDamageIn, Item.Properties builder){
        super(tier, attackDamageIn, -2.0f, builder);
    }
}