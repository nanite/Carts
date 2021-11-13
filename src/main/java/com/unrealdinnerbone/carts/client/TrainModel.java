package com.unrealdinnerbone.carts.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class TrainModel extends EntityModel<TrainEntity> {
    private final ModelRenderer buffersf;
    private final ModelRenderer buffersback;
    private final ModelRenderer body;
    private final ModelRenderer roof;
    private final ModelRenderer trim;

    public TrainModel() {
        texWidth = 128;
        texHeight = 64;

        buffersf = new ModelRenderer(this);
        buffersf.setPos(0.0F, 24.0F, 0.0F);
        buffersf.texOffs(0, 0).addBox(-5.0F, -4.5F, -13.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        buffersf.texOffs(0, 0).addBox(3.0F, -4.5F, -13.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        buffersf.texOffs(0, 0).addBox(-4.5F, -4.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        buffersf.texOffs(0, 0).addBox(3.5F, -4.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        buffersback = new ModelRenderer(this);
        buffersback.setPos(0.0F, 24.0F, 23.0F);
        buffersback.texOffs(0, 0).addBox(-5.0F, -4.5F, -11.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        buffersback.texOffs(0, 0).addBox(3.0F, -4.5F, -11.0F, 2.0F, 2.0F, 1.0F, 0.0F, false);
        buffersback.texOffs(0, 0).addBox(-4.5F, -4.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);
        buffersback.texOffs(0, 0).addBox(3.5F, -4.0F, -12.0F, 1.0F, 1.0F, 1.0F, 0.0F, false);

        body = new ModelRenderer(this);
        body.setPos(0.0F, 24.0F, 0.0F);
        body.texOffs(0, 0).addBox(7.0F, -9.0F, -1.0F, 1.0F, 6.0F, 10.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-8.0F, -3.0F, -10.0F, 16.0F, 2.0F, 20.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-8.0F, -9.0F, 9.0F, 16.0F, 6.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-8.0F, -7.0F, -10.0F, 16.0F, 4.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(7.0F, -13.0F, -9.0F, 1.0F, 10.0F, 8.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-8.0F, -13.0F, -9.0F, 1.0F, 10.0F, 8.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-8.0F, -9.0F, -1.0F, 1.0F, 6.0F, 10.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-7.0F, -13.0F, -2.0F, 14.0F, 4.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-7.0F, -12.0F, -9.0F, 14.0F, 4.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-7.0F, -9.0F, -5.0F, 14.0F, 6.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-7.0F, -10.0F, -5.0F, 14.0F, 1.0F, 3.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-6.0F, -3.05F, -3.0F, 12.0F, 0.0F, 11.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(7.0F, -13.0F, -10.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-3.0F, -13.0F, -10.0F, 6.0F, 6.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-8.0F, -13.0F, -10.0F, 1.0F, 6.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-7.0F, -13.0F, -10.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(-7.0F, -8.0F, -10.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(3.0F, -13.0F, -10.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);
        body.texOffs(0, 0).addBox(3.0F, -8.0F, -10.0F, 4.0F, 1.0F, 1.0F, 0.0F, false);

        roof = new ModelRenderer(this);
        roof.setPos(0.0F, 0.0F, 0.0F);
        body.addChild(roof);
        roof.texOffs(0, 0).addBox(-7.0F, -13.0F, -9.0F, 14.0F, 1.0F, 7.0F, 0.0F, false);
        roof.texOffs(0, 0).addBox(-1.5F, -18.0F, -7.0F, 3.0F, 5.0F, 3.0F, 0.0F, false);

        trim = new ModelRenderer(this);
        trim.setPos(0.0F, 24.0F, 0.0F);
        trim.texOffs(0, 0).addBox(-9.0F, -4.0F, 10.0F, 18.0F, 1.0F, 1.0F, 0.0F, false);
        trim.texOffs(0, 0).addBox(8.0F, -4.0F, -10.0F, 1.0F, 1.0F, 20.0F, 0.0F, false);
        trim.texOffs(0, 0).addBox(-9.0F, -4.0F, -10.0F, 1.0F, 1.0F, 20.0F, 0.0F, false);
        trim.texOffs(0, 0).addBox(-9.0F, -4.0F, -11.0F, 18.0F, 1.0F, 1.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(TrainEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.roof.y = 4.0F - ageInTicks;
        this.body.y = 4.0F - ageInTicks;
        this.trim.y = 4.0F - ageInTicks;
        this.buffersback.y = 4.0F - ageInTicks;
        this.buffersf.y = 4.0F - ageInTicks;
        //previously the render function, render code was moved to a method below
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        buffersf.render(matrixStack, buffer, packedLight, packedOverlay);
        buffersback.render(matrixStack, buffer, packedLight, packedOverlay);
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        trim.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
