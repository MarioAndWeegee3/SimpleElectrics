package com.marioandweegee3.simpleelectrics.common.recipes;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.marioandweegee3.simpleelectrics.SimpleElectrics;
import com.marioandweegee3.simpleelectrics.common.items.BasicItems;

public class GrinderRecipes{
    private static final GrinderRecipes COOKING_BASE = new GrinderRecipes();
    private final Map<ItemStack, ItemStack> cookingList = Maps.newHashMap();
    private final Map<ItemStack, Float> experienceList = Maps.newHashMap();

    private GrinderRecipes(){
        this.addCookingRecipe(new ItemStack(Blocks.IRON_ORE), new ItemStack(BasicItems.iron_dust, 3), 3.0f);
    }

    public static GrinderRecipes instance(){
        return COOKING_BASE;
    }

    public void addCookingBlock(Block input, ItemStack stack, float experience){
        this.addCookingItem(input.asItem(), stack, experience);
    }

    public void addCookingItem(Item input, ItemStack stack, float experience){
        this.addCookingRecipe(new ItemStack(input,1), stack, experience);
    }

    public void addCookingRecipe(ItemStack input, ItemStack stack, float experience){
        if(getCookingResult(input) != ItemStack.EMPTY){
            SimpleElectrics.logger.info("Ignored cooking recipe with conflicting input: {} = {}", input, stack);
            return;
        }
        this.cookingList.put(input,stack);
        this.experienceList.put(stack, Float.valueOf(experience));
    }

    public ItemStack getCookingResult(ItemStack stack){
        SimpleElectrics.logger.info("Checking Cooking Result");
        for(Map.Entry<ItemStack, ItemStack> entry : this.cookingList.entrySet()){
            if(this.compareItemStacks(stack, entry.getKey())){
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }

    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2){
        return stack2.getItem() == stack1.getItem();
    }

    public Map<ItemStack, ItemStack> getCookingList(){
        return this.cookingList;
    }

    public float getCookingExperience(ItemStack stack){
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1) return ret;
        for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet()){
            if (this.compareItemStacks(stack, entry.getKey())){
                return ((Float)entry.getValue()).floatValue();
            }
        }
        return 0.0F;
    }
}