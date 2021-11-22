package com.unrealdinnerbone.carts.packet;

import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class SetTrainDirectionPacket {

    private final int entityId;
    private final boolean whistle;

    public SetTrainDirectionPacket(TrainEntity trainCart, boolean active) {
        this.entityId = trainCart.getId();
        this.whistle = active;
    }

    public SetTrainDirectionPacket(PacketBuffer buf) {
        this.entityId = buf.readInt();
        this.whistle = buf.readBoolean();
    }

    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.whistle);
    }

    public void handle(Supplier<Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide() == LogicalSide.SERVER) {
                if(ctx.getSender() != null) {
                    MinecraftServer server = ctx.getSender().getServer();
                    if(server != null) {
                        TrainEntity trainCart = (TrainEntity) ctx.getSender().level.getEntity(this.entityId);
                        if(trainCart != null) {
                            trainCart.setDirection(whistle ? TrainEntity.Direction.FORWARDS : TrainEntity.Direction.BACKWARDS);
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
