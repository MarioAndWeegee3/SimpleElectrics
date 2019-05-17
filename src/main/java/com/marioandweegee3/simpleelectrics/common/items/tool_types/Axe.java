package com.marioandweegee3.simpleelectrics.common.items.tool_types;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;

public class Axe extends ItemAxe{
    public Axe(IItemTier tier, float attackDamage, float attackSpeed, Item.Properties builder){
        super(tier, attackDamage, attackSpeed, builder);
    }
}