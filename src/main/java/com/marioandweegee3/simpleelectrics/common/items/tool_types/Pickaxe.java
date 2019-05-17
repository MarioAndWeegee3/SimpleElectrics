package com.marioandweegee3.simpleelectrics.common.items.tool_types;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;

public class Pickaxe extends ItemPickaxe{
    public Pickaxe(IItemTier tier, int attackDamage, float attackSpeed, Item.Properties builder){
        super(tier, attackDamage, attackSpeed, builder);
    }
}