package marioandweegee3.simpleelectrics.common.item_groups;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import marioandweegee3.simpleelectrics.common.items.tools.IronTools;

public class SETools extends ItemGroup{
    public SETools(){
        super("setools");
    }

    @Override
    public ItemStack createIcon(){
        return new ItemStack(IronTools.iron_hammer);
    }
}