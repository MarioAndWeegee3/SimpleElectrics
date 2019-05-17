package com.marioandweegee3.simpleelectrics.common.blocks;

import com.jcraft.jogg.StreamState;
import com.marioandweegee3.simpleelectrics.SimpleElectrics;
import com.marioandweegee3.simpleelectrics.common.tile_entities.grinder.GrinderTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkHooks;

@SuppressWarnings("deprecation")
public class GrinderBlock extends Block{
    public static final DirectionProperty FACING = BlockHorizontal.HORIZONTAL_FACING;
    private final boolean isGrinding;
    private static boolean keepInventory;
    
    public GrinderBlock(Block.Properties properties, boolean isGrinding){
        super(properties);
        this.isGrinding = isGrinding;
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, EnumFacing.NORTH));
    }

    @Override
    public boolean onBlockActivated(IBlockState state, World worldIn, BlockPos pos, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if(!worldIn.isRemote){
            GrinderTile te = (GrinderTile)worldIn.getTileEntity(pos);

            if(te != null){
                if(player instanceof EntityPlayerMP && !(player instanceof FakePlayer)){
                    EntityPlayerMP playerMP = (EntityPlayerMP)player;

                    NetworkHooks.openGui(playerMP, te, buf -> buf.writeBlockPos(pos));
                }
            }

            return true;
        }

        return false;
    }

    @Override
    public void onReplaced(IBlockState state, World worldIn, BlockPos pos, IBlockState newState, boolean isMoving) {
        TileEntity te = worldIn.getTileEntity(pos);

        if(te != null && te instanceof GrinderTile && !keepInventory){
            GrinderTile gt = (GrinderTile)te;
            gt.dropInventory(worldIn, gt.getPos());
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public IBlockState getStateForPlacement(BlockItemUseContext context) {
        EnumFacing facing = context.getPlacementHorizontalFacing().getOpposite();

        return this.getDefaultState().with(FACING, facing);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity te = worldIn.getTileEntity(pos);

        if(te != null && te instanceof GrinderTile){
            GrinderTile gt = (GrinderTile)te;
            if(stack.hasDisplayName()){
                gt.setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public boolean hasTileEntity() {
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(IBlockState state, IBlockReader world) {
        return new GrinderTile();
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @Override
    public IBlockState rotate(IBlockState state, Rotation rot) {
        return state.with(FACING, rot.rotate(state.get(FACING)));
    }

    @Override
    public IBlockState mirror(IBlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.toRotation(state.get(FACING)));
    }

    @Override
    protected void fillStateContainer(Builder<Block, IBlockState> builder) {
        builder.add(FACING);
    }

    public static void setState(boolean active, World world, BlockPos pos){
        IBlockState state = world.getBlockState(pos);
        TileEntity te = world.getTileEntity(pos);
        keepInventory = true;

        if(active){
            world.setBlockState(pos, grinder_on.getDefaultState().with(FACING, state.get(FACING)), 3);
        } else {
            world.setBlockState(pos, grinder_off.getDefaultState().with(FACING, state.get(FACING)), 3);
        }

        keepInventory = false;

        if(te != null){
            te.validate();
            world.setTileEntity(pos, te);
        }
    }

    public static final Block grinder_on = new GrinderBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5f).sound(SoundType.STONE).lightValue(10),true);
    public static final Block grinder_off = new GrinderBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5f).sound(SoundType.STONE),false);
}