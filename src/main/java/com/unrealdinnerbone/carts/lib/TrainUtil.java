package com.unrealdinnerbone.carts.lib;

import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;

public class TrainUtil
{
    public static boolean isRidingTrain() {
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        return playerEntity != null && playerEntity.isPassenger() && playerEntity.getVehicle() instanceof TrainEntity;
    }
}
