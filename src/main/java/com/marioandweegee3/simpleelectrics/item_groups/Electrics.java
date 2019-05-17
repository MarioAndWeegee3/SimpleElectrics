package com.marioandweegee3.simpleelectrics.item_groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import com.marioandweegee3.simpleelectrics.common.items.BasicItems;

public class Electrics extends ItemGroup{
    public Electrics(){
        super("electrics");
    }

    @Override
    public ItemStack createIcon(){
        return new ItemStack(BasicItems.red_iron_ingot);
    }
}