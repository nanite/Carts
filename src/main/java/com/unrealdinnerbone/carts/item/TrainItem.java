package com.unrealdinnerbone.carts.item;

import com.unrealdinnerbone.carts.CartsRegistry;
import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TrainItem extends Item {

    public TrainItem(Properties properties) {
        super(properties);
    }

    public ActionResultType useOn(ItemUseContext context) {
        World world = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = world.getBlockState(blockpos);
        if(blockstate.is(BlockTags.RAILS)) {
            ItemStack itemstack = context.getItemInHand();
            if (!world.isClientSide) {
                RailShape railshape = blockstate.getBlock() instanceof AbstractRailBlock ? ((AbstractRailBlock)blockstate.getBlock()).getRailDirection(blockstate, world, blockpos, null) : RailShape.NORTH_SOUTH;
                double offset = railshape.isAscending() ? 0.5D : 0.0D;
                TrainEntity trainEntity = TrainEntity.create(blockpos.getX() + 0.5D, blockpos.getY() + 0.0625D + offset, blockpos.getZ() + 0.5D, world);
                world.addFreshEntity(trainEntity);
            }

            itemstack.shrink(1);
            return ActionResultType.sidedSuccess(world.isClientSide);
        }else {
            return ActionResultType.FAIL;
        }
    }
}
