package marioandweegee3.simpleelectrics.common.tool_types;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemGroup;
import marioandweegee3.simpleelectrics.SimpleElectrics;

public class Axe extends ItemAxe{
	private static final float speed = -3.0f;
	
    public Axe(IItemTier tier, float attackDamage, Item.Properties builder){
        super(tier, attackDamage, speed, builder);
    }
    
    public static Item create(IItemTier tier, float attackDamage, ItemGroup group, String name) {
    	return new Axe(tier, attackDamage, SimpleElectrics.createProperties(group)).setRegistryName(SimpleElectrics.location(name));
    }
}