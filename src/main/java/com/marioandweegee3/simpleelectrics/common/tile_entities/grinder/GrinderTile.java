package com.marioandweegee3.simpleelectrics.common.tile_entities.grinder;

import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.function.Supplier;

import com.marioandweegee3.simpleelectrics.SimpleElectrics;
import com.marioandweegee3.simpleelectrics.common.recipes.GrinderRecipes;
import com.marioandweegee3.simpleelectrics.common.blocks.GrinderBlock;
import com.marioandweegee3.simpleelectrics.util.GuiHandler;
import com.marioandweegee3.simpleelectrics.common.tile_entities.TileTypes;

import com.mojang.datafixers.types.Type;

public class GrinderTile extends TileEntity implements ITickable, IInteractionObject{
    private ItemStackHandler inventory = new ItemStackHandler(3);
    private int burnTime, currentBurnTime, cookTime, totalCookTime;
    private ITextComponent customName;

    public GrinderTile(){
        super(TileTypes.GRINDER);
    }

    @Override
    public String getGuiID() {
        return GuiHandler.GUI.GRINDER.getGuiID();
    }

    @Override
    public ITextComponent getName() {
        return this.hasCustomName() ? this.customName : new TextComponentTranslation("container.grinder");
    }

    @Override
    public ITextComponent getCustomName() {
        return this.getName();
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
        return new GrinderContainer(playerInventory, this);
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null;
    }

    public void setCustomName(ITextComponent customName) {
        this.customName = customName;
    }

    @Override
    public void read(NBTTagCompound compound) {
        super.read(compound);
        this.inventory.deserializeNBT(compound.getCompound("inventory"));
        this.burnTime = compound.getInt("BurnTime");
        this.cookTime = compound.getInt("CookTime");
        this.totalCookTime = compound.getInt("TotalCookTime");
        this.currentBurnTime = compound.getInt("CurrentBurnTime");

        if(compound.contains("CustomName", Constants.NBT.TAG_STRING)){
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    @Override
    public NBTTagCompound write(NBTTagCompound compound) {
        super.write(compound);
		compound.setTag("inventory", this.inventory.serializeNBT());
		compound.setInt("BurnTime", this.burnTime);
		compound.setInt("CookTime", this.cookTime);
		compound.setInt("TotalCookTime", this.totalCookTime);
		compound.setInt("CurrentBurnTime", this.currentBurnTime);
		if(this.hasCustomName()) {
			compound.setString("CustomName", ITextComponent.Serializer.toJson(customName));
		}
		
		return compound;
    }

    public boolean isGrinding(){
        boolean grind = this.burnTime > 0;
        return grind;
    }

    private void log(String message){
        SimpleElectrics.logger.info(message);
    }

    private boolean canGrind(){
        if(this.inventory.getStackInSlot(0).isEmpty()){
            return false;
        } else {
            ItemStack stack = GrinderRecipes.instance().getCookingResult(this.inventory.getStackInSlot(0));
            if(stack.isEmpty()){
                return false;
            } else {
                ItemStack stack1 = this.inventory.getStackInSlot(2);

                if(stack1.isEmpty()){
                    return true;
                } else if(!stack1.isItemEqual(stack)){
                    return false;
                } else if(stack1.getCount() + stack.getCount() <= 64 && stack1.getCount()+stack.getCount() <= stack1.getMaxStackSize()){
                    return true;
                } else {
                    return stack1.getCount() + stack.getCount() <= stack.getMaxStackSize();
                }
            }
        }
    }

    @Override
    public void tick() {
        boolean flag = this.isGrinding(), flag1 = false;

        if(this.isGrinding()){
            this.burnTime--;
        }

        if(!this.world.isRemote){
            ItemStack stack = this.inventory.getStackInSlot(1);

            if(this.isGrinding() || !stack.isEmpty() && !this.inventory.getStackInSlot(0).isEmpty()){
                if(!this.isGrinding() && this.canGrind()){
                    this.burnTime = getBurnTime(stack);
                    this.currentBurnTime = this.burnTime;

                    if(this.isGrinding()){
                        flag1 = true;

                        if(!stack.isEmpty()){
                            Item item = stack.getItem();
                            stack.shrink(1);
                            if(stack.isEmpty()){
                                ItemStack item1 = item.getContainerItem(stack);
                                this.inventory.setStackInSlot(1, item1);
                            }
                        }
                    }
                }

                if(this.isGrinding() && this.canGrind()){
                    this.cookTime++;
                    if(this.cookTime == this.totalCookTime){
                        this.cookTime = 0;
                        this.totalCookTime = this.getCookTime(this.inventory.getStackInSlot(0));
                        this.grindItem();
                        flag1 = true;
                    }
                } else {
                    this.cookTime = 0;
                }
            } else if(!this.isGrinding() && this.cookTime > 0){
                this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
            }

            if(flag != this.isGrinding()){
                flag1 = true;
                GrinderBlock.setState(this.isGrinding(), this.world, this.pos);
            }
        }
    }

    public void dropInventory(World worldIn, BlockPos pos){
        for(int i = 0; i < this.inventory.getSlots(); i++){
            ItemStack stack = this.inventory.getStackInSlot(i);

            if(!stack.isEmpty()){
                InventoryHelper.spawnItemStack(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), stack);
            }
        }
    }

    @SuppressWarnings("unlikely-arg-type")
	public static int getBurnTime(ItemStack stack) {
		if(stack.isEmpty()) {
			return 0;
		} else {
            if(TileEntityFurnace.isItemFuel(stack)){
			    int burnTime = TileEntityFurnace.getBurnTimes().get(stack.getItem());
                if(burnTime >= 0) return burnTime;
                else return 0;
            } else return 0;
		}
    }

    public int getCookTime(ItemStack stack) {
		return 200;
    }

    public void grindItem(){
        if(this.canGrind()){
            ItemStack stack = this.inventory.getStackInSlot(0), 
            stack1 = GrinderRecipes.instance().getCookingResult(stack),
            stack2 = this.inventory.getStackInSlot(2);

            if(stack2.isEmpty()){
                this.inventory.setStackInSlot(2, stack1.copy());
            } else if(stack2.getItem() == stack1.getItem()){
                if(stack2.getCount() + stack1.getCount() <= stack2.getMaxStackSize()){
                    stack2.grow(stack1.getCount());
                }
            }

            stack.shrink(1);
        }
    }

    public static boolean isItemFuel(ItemStack stack){
        return getBurnTime(stack) > 0;
    }

    public int getField(int id){
        switch(id){
        case 0:
            return this.burnTime;
        case 1:
            return this.currentBurnTime;
            case 2:
			return this.cookTime;
		case 3:
			return this.totalCookTime;
		default:
            return 0;
        }
    }

    public void setField(int id, int value) {
		switch(id) {
		case 0:
			this.burnTime = value;
			break;
		case 1:
			this.currentBurnTime = value;
			break;
		case 2:
			this.cookTime = value;
			break;
		case 3:
			this.totalCookTime = value;
			break;
		}
    }

    public int getFieldCount() {
		return 4;
    }

    public void clear() {
		for(int i = 0; i < this.inventory.getSlots(); i++) {
			this.inventory.setStackInSlot(i, ItemStack.EMPTY);
		}
    }

    public ItemStackHandler getInventory(){
        return this.inventory;
    }

    @SuppressWarnings("unchecked")
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap) {
		if(!this.removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return LazyOptional.of(() -> (T)inventory);
		} else {
			return LazyOptional.empty();
		}
    }
}