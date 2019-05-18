package marioandweegee3.simpleelectrics.common.tool_types;

import marioandweegee3.simpleelectrics.SimpleElectrics;
import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemSpade;

public class Shovel extends ItemSpade {
	private static final float damage = -2.0f, speed = -2.8f;
	public Shovel(IItemTier tier, Item.Properties builder) {
		super(tier, damage, speed, builder);
	}
	
	public static Item create(IItemTier tier, ItemGroup group, String name) {
		return new Shovel(tier, SimpleElectrics.createProperties(group)).setRegistryName(SimpleElectrics.location(name));
	}
}
