package com.unrealdinnerbone.carts.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.unrealdinnerbone.carts.Carts;
import com.unrealdinnerbone.carts.CartContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class TrainScreen extends ContainerScreen<CartContainer> {

    private static final ResourceLocation TRAIN_INVENTORY = new ResourceLocation(Carts.MOD_ID, "textures/gui/container/train.png");

    public TrainScreen(CartContainer trainContainer, PlayerInventory playerInventory, ITextComponent textComponent) {
        super(trainContainer, playerInventory, textComponent);
        this.passEvents = true;
    }

    protected void renderBg(MatrixStack matrixStack, float p_230450_2_, int p_230450_3_, int p_230450_4_) {
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.minecraft.getTextureManager().bind(TRAIN_INVENTORY);
        int i = (this.width - this.imageWidth) / 2;
        int j = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, i, j, 0, 0, this.imageWidth, this.imageHeight);
    }

    public void render(MatrixStack p_230430_1_, int p_230430_2_, int p_230430_3_, float p_230430_4_) {
        this.renderBackground(p_230430_1_);
        super.render(p_230430_1_, p_230430_2_, p_230430_3_, p_230430_4_);
        this.renderTooltip(p_230430_1_, p_230430_2_, p_230430_3_);
    }
}
