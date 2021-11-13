package com.unrealdinnerbone.carts.compact.computercraft;

import com.unrealdinnerbone.carts.block.computercraft.RailDetectorBE;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class RailDetectorPeripheral implements IPeripheral {

    private static final String TYPE = "rail_detector";
    private final RailDetectorBE railTile;
    private final List<IComputerAccess> computers = new ArrayList<>();


    public RailDetectorPeripheral(RailDetectorBE railTile) {
        this.railTile = railTile;
    }


    @Override
    public void attach(@Nonnull IComputerAccess computer) {
        computers.add(computer);
    }

    @Override
    public void detach(@Nonnull IComputerAccess computer) {
        computers.remove(computer);
    }

    @LuaFunction
    public final synchronized String getLast() {
        return railTile.getLastPassedCartName();
    }

    @Nonnull
    @Override
    public String getType() {
        return TYPE;
    }

    public void triggerEvent(String... data) {
        for(IComputerAccess computer : computers) {
            computer.queueEvent("minecart", data);
        }
    }

    @Override
    public boolean equals(@Nullable IPeripheral other) {
        return this == other || other instanceof RailDetectorPeripheral && ((RailDetectorPeripheral) other).railTile == railTile;

    }
}
