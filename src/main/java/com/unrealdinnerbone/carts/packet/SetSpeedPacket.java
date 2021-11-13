package com.unrealdinnerbone.carts.packet;

import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class SetSpeedPacket {

    private int entityId;
    private boolean forward;

    public SetSpeedPacket(TrainEntity trainCart, boolean forward) {
        this.entityId = trainCart.getId();
        this.forward = forward;
    }

    public SetSpeedPacket(PacketBuffer buf) {
        this.entityId = buf.readInt();
        this.forward = buf.readBoolean();
    }


    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityId);
        buf.writeBoolean(this.forward);
    }

    public void handle(Supplier<Context> context) {
        Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide() == LogicalSide.SERVER) {
                if(ctx.getSender() != null) {
                    MinecraftServer server = ctx.getSender().getServer();
                    if(server != null) {
                        TrainEntity trainCart = (TrainEntity) ctx.getSender().level.getEntity(this.entityId);
                        if(trainCart != null) {
                            trainCart.setSpeed(forward ? trainCart.getSpeed().next() : trainCart.getSpeed().previous());
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
