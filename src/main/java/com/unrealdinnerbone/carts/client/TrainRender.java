package com.unrealdinnerbone.carts.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MinecartRenderer;
import net.minecraft.entity.player.PlayerEntity;

public class TrainRender extends MinecartRenderer<TrainEntity> {

    public TrainRender(EntityRendererManager rendererManager) {
        super(rendererManager);
    }

    @Override
    public void render(TrainEntity entity, float entityYaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderTypeBuffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, matrixStack, renderTypeBuffer, packedLight);
        PlayerEntity playerEntity = Minecraft.getInstance().player;
        if(playerEntity.getVehicle() == null || playerEntity.getVehicle().getId() != entity.getId()) {
            renderNameTag(entity, entity.getDisplayName(), matrixStack, renderTypeBuffer, packedLight);
        }
    }
}
