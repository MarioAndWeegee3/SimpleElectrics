package marioandweegee3.simpleelectrics.common.tool_types;

import marioandweegee3.simpleelectrics.SimpleElectrics;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPickaxe;

public class Pickaxe extends ItemPickaxe{
	private static final float speed = -2.8f;
	private static final int damage = -2;
    public Pickaxe(IItemTier tier, Item.Properties builder){
        super(tier, damage, speed, builder);
    }
    
    public static Item create(IItemTier tier, ItemGroup group, String name) {
    	return new Pickaxe(tier, SimpleElectrics.createProperties(group)).setRegistryName(SimpleElectrics.location(name));
    }
}