package com.unrealdinnerbone.carts.lib;

import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.event.TickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ClickKeyBinding extends KeyBinding {


    private static final List<ClickKeyBinding> bindings = new ArrayList<>();

    private static boolean handled = false;
    private List<Consumer<TrainEntity>> onDown = new ArrayList<>();
    private List<Consumer<TrainEntity>> onUp = new ArrayList<>();

    public ClickKeyBinding(String description, int keyCode, Consumer<TrainEntity> down, Consumer<TrainEntity> up) {
        super(description, new RidingTrainKeyConflictContext(), InputMappings.Type.KEYSYM, keyCode, "key.categories.jamm");
        registerDownHandler(down);
        registerUpHandler(up);
        bindings.add(this);
    }

    public ClickKeyBinding(String description, int keyCode, Consumer<TrainEntity> down) {
        super(description, new RidingTrainKeyConflictContext(), InputMappings.Type.KEYSYM, keyCode, "key.categories.jamm");
        registerDownHandler(down);
        bindings.add(this);
    }

    public void registerDownHandler(Consumer<TrainEntity> eventConsumer) {
        this.onDown.add(eventConsumer);
    }

    public void registerUpHandler(Consumer<TrainEntity> eventConsumer) {
        this.onUp.add(eventConsumer);
    }

    public void tick(TickEvent.ClientTickEvent event) {

        PlayerEntity playerEntity = Minecraft.getInstance().player;
        if(playerEntity != null && playerEntity.isPassenger() && playerEntity.getVehicle() instanceof TrainEntity) {
            TrainEntity trainEntity =  (TrainEntity) playerEntity.getVehicle();
            if(this.consumeClick() && !handled) {
                handled = true;
                for(Consumer<TrainEntity> clientTickEventConsumer : onDown) {
                    clientTickEventConsumer.accept(trainEntity);
                }
            }else if(handled && !this.consumeClick()) {
                handled = false;
                for(Consumer<TrainEntity> clientTickEventConsumer : onUp) {
                    clientTickEventConsumer.accept(trainEntity);
                }
            }
        }



    }


    public static class EventHandler {
        public static void onClientTick(TickEvent.ClientTickEvent event) {
            for(int i = 0, bindingsSize = bindings.size(); i < bindingsSize; i++) {
                ClickKeyBinding binding = bindings.get(i);
                binding.tick(event);
            }
        }
    }

    public static class RidingTrainKeyConflictContext implements IKeyConflictContext {

        @Override
        public boolean isActive() {
            return TrainUtil.isRidingTrain();
        }

        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return false;
        }
    }
}
