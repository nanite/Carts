package com.unrealdinnerbone.carts.block.computercraft;

import com.unrealdinnerbone.carts.CartsRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

public class RailDetectorBlock extends Block {

    public RailDetectorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new RailDetectorBE(CartsRegistry.RAIL_DETECTOR_BE.get());
    }
}
