package com.unrealdinnerbone.carts;

import com.unrealdinnerbone.carts.packet.ToggleTrainInventory;
import com.unrealdinnerbone.carts.packet.SetSpeedPacket;
import com.unrealdinnerbone.carts.packet.SetTrainDirectionPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.Optional;

public class CartsPackets
{
    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Carts.MOD_ID, Carts.MOD_ID),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    private static int id = 0;

    public static void init() {
        CHANNEL.registerMessage(id++, SetTrainDirectionPacket.class, SetTrainDirectionPacket::encode, SetTrainDirectionPacket::new, SetTrainDirectionPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(id++, SetSpeedPacket.class, SetSpeedPacket::encode, SetSpeedPacket::new, SetSpeedPacket::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
        CHANNEL.registerMessage(id++, ToggleTrainInventory.class, ToggleTrainInventory::encode, ToggleTrainInventory::new, ToggleTrainInventory::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }
}
