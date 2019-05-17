package com.marioandweegee3.simpleelectrics.item_groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import com.marioandweegee3.simpleelectrics.common.items.Tools;

public class SETools extends ItemGroup{
    public SETools(){
        super("setools");
    }

    @Override
    public ItemStack createIcon(){
        return new ItemStack(Tools.iron_hammer);
    }
}