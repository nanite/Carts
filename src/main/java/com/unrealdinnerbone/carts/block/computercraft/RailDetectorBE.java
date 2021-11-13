package com.unrealdinnerbone.carts.block.computercraft;

import com.unrealdinnerbone.carts.CartsRegistry;
import com.unrealdinnerbone.carts.compact.computercraft.RailDetectorPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.util.CapabilityUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static dan200.computercraft.shared.Capabilities.CAPABILITY_PERIPHERAL;

public class RailDetectorBE extends TileEntity {

    private String lastPassedCartName;
    private final RailDetectorPeripheral railP;
    private LazyOptional<IPeripheral> peripheralCap;

    public RailDetectorBE(TileEntityType<?> type) {
        super(type);
        this.lastPassedCartName = "";
        this.railP = new RailDetectorPeripheral(this);
        this.peripheralCap = LazyOptional.of(() -> railP);

    }

    public void setLastPassedCartName(String name) {
        this.lastPassedCartName = name;
    }

    public String getLastPassedCartName() {
        return lastPassedCartName;
    }

    @Override
    public CompoundNBT save(CompoundNBT compound) {
        return super.save(writeData(compound));
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        readData(nbt);
        super.load(state, nbt);
    }


    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), 0, writeData(new CompoundNBT()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        readData(pkt.getTag());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return writeData(new CompoundNBT());
    }


    public void onCartPassed(AbstractMinecartEntity entity) {
       String name = entity.getDisplayName().getString();
       setLastPassedCartName(name);
       railP.triggerEvent(name, name);
    }

    public CompoundNBT writeData(CompoundNBT compound) {
        compound.putString("lastPassed", lastPassedCartName);
        return compound;
    }

    public void readData(CompoundNBT compound) {
        if(compound.contains("lastPassed")) {
            lastPassedCartName = compound.getString("lastPassed");
        }
    }


    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == CAPABILITY_PERIPHERAL ? peripheralCap.cast() : super.getCapability(cap, side);
    }

    @Override
    protected void invalidateCaps() {
        super.invalidateCaps();
        peripheralCap = CapabilityUtil.invalidate(peripheralCap);
    }
}
