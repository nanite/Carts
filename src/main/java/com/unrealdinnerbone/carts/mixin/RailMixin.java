package com.unrealdinnerbone.carts.mixin;

import com.unrealdinnerbone.carts.block.computercraft.RailDetectorBE;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecartEntity.class)
public abstract class RailMixin extends Entity {


    public RailMixin(EntityType<?> pType, World pLevel) {
        super(pType, pLevel);
        throw new IllegalAccessError("You shouldn't be able to create a RailMixin");
    }

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/block/AbstractRailBlock;onMinecartPass(Lnet/minecraft/block/BlockState;Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/item/minecart/AbstractMinecartEntity;)V"), method = "moveAlongTrack")
    public void mixinPass(BlockPos pos, BlockState state, CallbackInfo info) {
        TileEntity tileEntity = level.getBlockEntity(pos.below());
        if(tileEntity instanceof RailDetectorBE) {
            ((RailDetectorBE) tileEntity).onCartPassed((AbstractMinecartEntity) (Object) this);
        }
    }
}
