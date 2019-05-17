package com.marioandweegee3.simpleelectrics.common.tool_materials;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

import com.marioandweegee3.simpleelectrics.common.items.BasicItems;

public enum Materials implements IItemTier{
    steel(6.5f,8.0f,1600,3,10,BasicItems.steel_ingot);

    private float attackDamage, efficiency;
    private int durability, harvestLevel, enchantability;
    private Item repairMaterial;

    private Materials(float attackDamage, float efficiency, int durability, int harvestLevel, int enchantability, Item repairMaterial){
        this.attackDamage = attackDamage;
        this.durability = durability;
        this.enchantability = enchantability;
        this.harvestLevel = harvestLevel;
        this.repairMaterial = repairMaterial;
        this.efficiency = efficiency;
    }

    @Override
    public float getAttackDamage(){return this.attackDamage;}
    @Override
    public float getEfficiency(){return this.efficiency;}
    @Override
    public int getMaxUses(){return this.durability;}
    @Override
    public int getHarvestLevel(){return this.harvestLevel;}
    @Override
    public int getEnchantability(){return this.enchantability;}
    @Override
    public Ingredient getRepairMaterial(){return Ingredient.fromItems(this.repairMaterial);}

}