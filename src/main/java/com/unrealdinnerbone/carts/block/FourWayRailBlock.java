package com.unrealdinnerbone.carts.block;

import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class FourWayRailBlock extends AbstractRailBlock {

    public static final EnumProperty<RailShape> SHAPE = EnumProperty.create("shape", RailShape.class, shape -> !shape.isAscending());

    public FourWayRailBlock(Properties properties) {
        super(true, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(SHAPE, RailShape.NORTH_SOUTH));
    }


    @Override
    public Property<RailShape> getShapeProperty() {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(SHAPE);
    }

    @Override
    public boolean canMakeSlopes(BlockState state, IBlockReader world, BlockPos pos) {
        return false;
    }

    @Override
    public RailShape getRailDirection(BlockState state, IBlockReader world, BlockPos pos, @Nullable AbstractMinecartEntity cart) {
        if (cart != null) {
            Vector3d delta = cart.getDeltaMovement();
            if (Math.abs(delta.x) > Math.abs(delta.z)) {
                return RailShape.EAST_WEST;
            }
        }
        return RailShape.NORTH_SOUTH;
    }
}
