package marioandweegee3.simpleelectrics.common.item_groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import marioandweegee3.simpleelectrics.common.items.DustItems;

public class SEMachines extends ItemGroup{
    public SEMachines(){
        super("semachines");
    }

    @Override
    public ItemStack createIcon(){
        return new ItemStack(DustItems.steel_dust);
    }
}