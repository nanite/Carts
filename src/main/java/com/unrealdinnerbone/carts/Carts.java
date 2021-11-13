package com.unrealdinnerbone.carts;

import com.unrealdinnerbone.carts.lib.ClickKeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Carts.MOD_ID)
public class Carts
{
    public static final String MOD_ID = "carts";

    private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

    public Carts() {
        CartsRegistry.init();
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, builder.build());
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> FMLJavaModLoadingContext.get().getModEventBus().addListener(CartsClient::doClientStuff));
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.addListener(ClickKeyBinding.EventHandler::onClientTick));
        CartsPackets.init();
    }

}
