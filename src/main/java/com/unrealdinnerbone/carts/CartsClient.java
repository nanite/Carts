package com.unrealdinnerbone.carts;

import com.unrealdinnerbone.carts.lib.ClickKeyBinding;
import com.unrealdinnerbone.carts.packet.SetSpeedPacket;
import com.unrealdinnerbone.carts.packet.SetTrainDirectionPacket;
import com.unrealdinnerbone.carts.packet.ToggleTrainInventory;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class CartsClient
{

    private static final KeyBinding FASTER = new ClickKeyBinding("key.jamm.faster", GLFW.GLFW_KEY_W,
            trainEntity -> CartsPackets.CHANNEL.sendToServer(new SetSpeedPacket(trainEntity, true)));

    private static final KeyBinding SLOWER = new ClickKeyBinding("key.jamm.slower", GLFW.GLFW_KEY_S,
            trainEntity -> CartsPackets.CHANNEL.sendToServer(new SetSpeedPacket(trainEntity, false)));

    private static final KeyBinding DIRECTION = new ClickKeyBinding("key.jamm.direction",  GLFW.GLFW_KEY_P,
            trainEntity ->  CartsPackets.CHANNEL.sendToServer(new SetTrainDirectionPacket(trainEntity, true)),
            trainEntity -> CartsPackets.CHANNEL.sendToServer(new SetTrainDirectionPacket(trainEntity, false)));

    private static final KeyBinding INVENTORY = new ClickKeyBinding("key.jamm.inventory", GLFW.GLFW_KEY_E,
            trainEntity -> CartsPackets.CHANNEL.sendToServer(new ToggleTrainInventory(trainEntity)));


    public static void doClientStuff(final FMLClientSetupEvent event) {
        ClientRegistry.registerKeyBinding(FASTER);
        ClientRegistry.registerKeyBinding(SLOWER);
        ClientRegistry.registerKeyBinding(DIRECTION);
        ClientRegistry.registerKeyBinding(INVENTORY);
        RenderTypeLookup.setRenderLayer(CartsRegistry.SLOW_RAIL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CartsRegistry.NORMAL_RAIL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CartsRegistry.FAST_RAIL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CartsRegistry.FOUR_WAY.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CartsRegistry.TURNABLE.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CartsRegistry.STOP_RAIL.get(), RenderType.cutout());
        RenderTypeLookup.setRenderLayer(CartsRegistry.LEFT_TURNABLE.get(), RenderType.cutout());

    }

}
