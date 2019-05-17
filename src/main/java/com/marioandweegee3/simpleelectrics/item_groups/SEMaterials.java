package com.marioandweegee3.simpleelectrics.item_groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import com.marioandweegee3.simpleelectrics.common.items.BasicItems;

public class SEMaterials extends ItemGroup{
    public SEMaterials(){
        super("sematerials");
    }

    @Override
    public ItemStack createIcon(){
        return new ItemStack(BasicItems.steel_dust);
    }
}