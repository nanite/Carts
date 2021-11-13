package com.unrealdinnerbone.carts.packet;

import com.unrealdinnerbone.carts.CartContainer;
import com.unrealdinnerbone.carts.Carts;
import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ToggleTrainInventory
{
    private int entityId;

    public ToggleTrainInventory(TrainEntity trainCart) {
        this.entityId = trainCart.getId();
    }

    public ToggleTrainInventory(PacketBuffer buf) {
        this.entityId = buf.readInt();
    }


    public void encode(PacketBuffer buf) {
        buf.writeInt(this.entityId);
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            if(ctx.getDirection().getReceptionSide() == LogicalSide.SERVER) {
                if(ctx.getSender() != null) {
                    MinecraftServer server = ctx.getSender().getServer();
                    if(server != null) {
                        TrainEntity trainCart = (TrainEntity) ctx.getSender().level.getEntity(this.entityId);
                        if(trainCart != null) {
                            if(ctx.getSender().containerMenu instanceof CartContainer) {
                                ctx.getSender().openMenu(null);
                            }else {
                                ctx.getSender().openMenu(trainCart);
                            }
                        }
                    }
                }
            }
        });
        ctx.setPacketHandled(true);
    }
}
