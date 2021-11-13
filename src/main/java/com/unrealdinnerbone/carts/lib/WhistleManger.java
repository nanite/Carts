package com.unrealdinnerbone.carts.lib;

import com.unrealdinnerbone.carts.CartsRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundEvent;

import java.util.function.Supplier;

public class WhistleManger {

    public static Supplier<SoundEvent> getSound(PlayerEntity player) {
        return CartsRegistry.WHISTLE;
    }
}
