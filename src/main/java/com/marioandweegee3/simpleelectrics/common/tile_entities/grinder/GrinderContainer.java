package com.marioandweegee3.simpleelectrics.common.tile_entities.grinder;

import com.marioandweegee3.simpleelectrics.SimpleElectrics;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.slots.GrinderFuel;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.slots.GrinderOutput;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderTile;
import com.marioandweegee3.simpleelectrics.common.recipes.GrinderRecipes;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;

public class GrinderContainer extends Container {
    private final GrinderTile te;
    private int cookTime, totalCookTime, burnTime, currentBurnTime;

    public GrinderContainer(InventoryPlayer plrInv, GrinderTile te) {
		this.te = te;
		this.addSlot(new SlotItemHandler(te.getInventory(), 0, 56, 53));
		this.addSlotFuel(new GrinderFuel(te.getInventory(), 1, 56, 17));
		this.addSlotOutput(new GrinderOutput(plrInv.player, te.getInventory(), 2, 116, 35));
        
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 9; ++j)
			{
				this.addSlot(new Slot(plrInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}
		
		for(int k = 0; k < 9; k++)
		{
			this.addSlot(new Slot(plrInv, k, 8 + k * 18, 142));
		}
	}

    protected Slot addSlot(Slot slotIn) {
        return super.addSlot(slotIn);
    }

    protected Slot addSlotFuel(GrinderFuel slotIn){
        return super.addSlot(slotIn);
    }

    protected Slot addSlotOutput(GrinderOutput slotIn){
        return super.addSlot(slotIn);
    }

    @Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();
		
		for(int i = 0; i < this.listeners.size(); i++) {
			IContainerListener icontainerlistener = this.listeners.get(i);

	        if (this.cookTime != this.te.getField(2))
	        {
	            icontainerlistener.sendWindowProperty(this, 2, this.te.getField(2));
	        }

	        if (this.burnTime != this.te.getField(0))
	        {
	            icontainerlistener.sendWindowProperty(this, 0, this.te.getField(0));
	        }

	        if (this.currentBurnTime != this.te.getField(1))
	        {
	            icontainerlistener.sendWindowProperty(this, 1, this.te.getField(1));
	        }

	        if (this.totalCookTime != this.te.getField(3))
	        {
	            icontainerlistener.sendWindowProperty(this, 3, this.te.getField(3));
	        }
		}
	    this.cookTime = this.te.getField(2);
	    this.burnTime = this.te.getField(0);
	    this.currentBurnTime = this.te.getField(1);
	    this.totalCookTime = this.te.getField(3);
	}
	
	@Override
	public void updateProgressBar(int id, int data) {
		this.te.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return playerIn.getDistanceSq((double)te.getPos().getX() + 0.5d, (double)te.getPos().getY() + 0.5d, (double)te.getPos().getZ() + 0.5d) <= 64.0d;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index == 2)
            {
                if (!this.mergeItemStack(itemstack1, 3, 39, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index != 1 && index != 0)
            {
                if (!GrinderRecipes.instance().getCookingResult(itemstack1).isEmpty())
                {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (GrinderTile.isItemFuel(itemstack1))
                {
                    if (!this.mergeItemStack(itemstack1, 1, 2, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 3 && index < 30)
                {
                    if (!this.mergeItemStack(itemstack1, 30, 39, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
                else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 3, 39, false))
            {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty())
            {
                slot.putStack(ItemStack.EMPTY);
            }
            else
            {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount())
            {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
}
}