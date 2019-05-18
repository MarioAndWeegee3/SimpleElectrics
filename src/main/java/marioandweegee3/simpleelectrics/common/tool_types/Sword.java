package marioandweegee3.simpleelectrics.common.tool_types;

import marioandweegee3.simpleelectrics.SimpleElectrics;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemSword;

public class Sword extends ItemSword {
	private static final float speed = -2.4f;
	public Sword(IItemTier tier, int attackDamageIn, Item.Properties builder) {
		super(tier, attackDamageIn, speed, builder);
	}
	
	public static Item create(IItemTier tier, int attackDamageIn, ItemGroup group, String name) {
    	return new Sword(tier, attackDamageIn, SimpleElectrics.createProperties(group)).setRegistryName(SimpleElectrics.location(name));
    }
}
