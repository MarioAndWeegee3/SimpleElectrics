package marioandweegee3.simpleelectrics.common.tool_types;

import java.util.Random;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTiered;

import marioandweegee3.simpleelectrics.SimpleElectrics;

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
    
    public static Item create(IItemTier tier, ItemGroup group, String name) {
    	return new Hammer(tier, SimpleElectrics.createProperties(group)).setRegistryName(SimpleElectrics.location(name));
    }
}