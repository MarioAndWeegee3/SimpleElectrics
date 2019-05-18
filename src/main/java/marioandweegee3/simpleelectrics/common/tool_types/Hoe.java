package marioandweegee3.simpleelectrics.common.tool_types;

import marioandweegee3.simpleelectrics.SimpleElectrics;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemHoe;

public class Hoe extends ItemHoe {
	private static final float speed = 4.0f;
	public Hoe(IItemTier tier, Item.Properties builder) {
		super(tier, speed, builder);
	}
	
	public static Item create(IItemTier tier, ItemGroup group, String name) {
		return new Hoe(tier, SimpleElectrics.createProperties(group)).setRegistryName(SimpleElectrics.location(name));
	}
}
