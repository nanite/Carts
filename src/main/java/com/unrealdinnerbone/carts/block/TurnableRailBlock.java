package com.unrealdinnerbone.carts.block;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.*;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class TurnableRailBlock extends AbstractRailBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final EnumProperty<RailShape> SHAPE = EnumProperty.create("shape", RailShape.class, shape -> !shape.isAscending());
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;


    public TurnableRailBlock(Properties properties) {
        super(true, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH).setValue(POWERED, false).setValue(FACING, Direction.NORTH));
    }


    @Override
    protected void updateState(BlockState pState, World pLevel, BlockPos pPos, Block pBlock) {
        boolean flag = pState.getValue(POWERED);
        boolean flag1 = pLevel.hasNeighborSignal(pPos);
        if (flag1 != flag) {
            pLevel.setBlock(pPos, pState.setValue(POWERED, flag1), 3);
            pLevel.updateNeighborsAt(pPos.below(), this);
        }

    }

    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SHAPE, POWERED, FACING);
    }

    @Override
    public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }

    @Override
    public RailShape getRailDirection(BlockState state, IBlockReader world, BlockPos pos, @Nullable AbstractMinecartEntity cart) {

        boolean is_powered = state.getValue(POWERED);
        Direction facing = state.getValue(FACING);
        boolean is_x_axis = facing == Direction.NORTH || facing == Direction.SOUTH;
        boolean backwards = facing == Direction.NORTH || facing == Direction.EAST;
        if (cart != null) {
            boolean turn_approach = is_x_axis? Math.abs(cart.getDeltaMovement().x) > 0.05 : Math.abs(cart.getDeltaMovement().z) > 0.05;

            if (is_powered || turn_approach) {

                boolean backwards_approach = (!is_x_axis? cart.getDeltaMovement().x : -cart.getDeltaMovement().z) * (backwards? 1 : -1) <= 0 && !turn_approach;

                if (backwards_approach) {
                } else {
                    if (is_x_axis) {
                        return backwards ? RailShape.SOUTH_EAST : RailShape.NORTH_WEST;
                    } else {
                        return backwards ? RailShape.SOUTH_WEST : RailShape.NORTH_EAST;
                    }
                }
            }
        }

        return is_x_axis? RailShape.NORTH_SOUTH : RailShape.EAST_WEST;
    }

    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        Direction direction = context.getHorizontalDirection();
        return super.getStateForPlacement(context).setValue(FACING, direction);
    }

    public BlockState rotate(BlockState pState, Rotation pRotation) {
        switch(pRotation) {
            case CLOCKWISE_180:
                switch(pState.getValue(SHAPE)) {
                    case SOUTH_EAST:
                        return pState.setValue(SHAPE, RailShape.NORTH_WEST);
                    case SOUTH_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_WEST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_EAST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_SOUTH: //Forge fix: MC-196102
                    case EAST_WEST:
                        return pState;
                }
            case COUNTERCLOCKWISE_90:
                switch(pState.getValue(SHAPE)) {
                    case NORTH_SOUTH:
                        return pState.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                    case SOUTH_EAST:
                        return pState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return pState.setValue(SHAPE, RailShape.NORTH_WEST);
                }
            case CLOCKWISE_90:
                switch(pState.getValue(SHAPE)) {
                    case NORTH_SOUTH:
                        return pState.setValue(SHAPE, RailShape.EAST_WEST);
                    case EAST_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_SOUTH);
                    case SOUTH_EAST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                }
            default:
                return pState;
        }
    }


    public BlockState mirror(BlockState pState, Mirror pMirror) {
        RailShape railshape = pState.getValue(SHAPE);
        switch(pMirror) {
            case LEFT_RIGHT:
                switch(railshape) {
                    case ASCENDING_NORTH:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_SOUTH);
                    case ASCENDING_SOUTH:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_NORTH);
                    case SOUTH_EAST:
                        return pState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case SOUTH_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_WEST);
                    case NORTH_WEST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case NORTH_EAST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    default:
                        return super.mirror(pState, pMirror);
                }
            case FRONT_BACK:
                switch(railshape) {
                    case ASCENDING_EAST:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_WEST);
                    case ASCENDING_WEST:
                        return pState.setValue(SHAPE, RailShape.ASCENDING_EAST);
                    case ASCENDING_NORTH:
                    case ASCENDING_SOUTH:
                    default:
                        break;
                    case SOUTH_EAST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_WEST);
                    case SOUTH_WEST:
                        return pState.setValue(SHAPE, RailShape.SOUTH_EAST);
                    case NORTH_WEST:
                        return pState.setValue(SHAPE, RailShape.NORTH_EAST);
                    case NORTH_EAST:
                        return pState.setValue(SHAPE, RailShape.NORTH_WEST);
                }
        }

        return super.mirror(pState, pMirror);
    }
}
