package com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.slots;

import com.marioandweegee3.simpleelectrics.common.recipes.GrinderRecipes;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class GrinderOutput extends SlotItemHandler{
    private final EntityPlayer player;
    private int removeCount;

    public GrinderOutput(EntityPlayer plr, ItemStackHandler inventory, int slotIndex, int xPos, int yPos){
        super(inventory, slotIndex, xPos, yPos);
        this.player = plr;
    }

    @Override
    public boolean isItemValid(ItemStack stack) {
        return false;
    }

    @Override
	public ItemStack decrStackSize(int amount) {
		if(this.getHasStack()) {
			this.removeCount += Math.min(amount, this.getStack().getCount());
		}
		return super.decrStackSize(amount);
    }

    @Override
	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
		this.onCrafting(stack);
		super.onTake(thePlayer, stack);
		return stack;
    }

    @Override
	protected void onCrafting(ItemStack stack, int amount) {
		this.removeCount += amount;
		this.onCrafting(stack);
    }

    @Override
	protected void onCrafting(ItemStack stack) {
		stack.onCrafting(this.player.world, this.player, this.removeCount);
		if(!this.player.world.isRemote) {
			int i = this.removeCount;
			float f = GrinderRecipes.instance().getCookingExperience(stack);
			
			if(f == 0.0f) {
				i = 0;
			} else if(f < 1.0f) {
				int j = MathHelper.floor((float)i + f);
				
				if(j < MathHelper.ceil((float)i * f) && Math.random() < (double)((float)i * f - (float)j)) {
					j++;
				}
				
				i = j;
			}
			
			while(i > 0) {
				int k = EntityXPOrb.getXPSplit(i);
				i -= k;
				this.player.world.spawnEntity(new EntityXPOrb(this.player.world, this.player.posX, this.player.posY + 0.5D, this.player.posZ + 0.5D, k));
			}
		}
		this.removeCount = 0;
		net.minecraftforge.fml.hooks.BasicEventHooks.firePlayerSmeltedEvent(this.player, stack);
    }
}