package com.unrealdinnerbone.carts.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.SegmentedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

import java.util.Arrays;

public class TrainModel extends EntityModel<TrainEntity> {
    private final ModelRenderer front;
    private final ModelRenderer front_grill_bumper_2_r1;
    private final ModelRenderer front_grill_bumper_1_r1;
    private final ModelRenderer front_grill_base_r1;
    private final ModelRenderer front_whistle_r1;
    private final ModelRenderer front_engine_brace_2_r1;
    private final ModelRenderer front_engine_brace_1_r1;
    private final ModelRenderer front_r1;
    private final ModelRenderer grill;
    private final ModelRenderer grill_r1;
    private final ModelRenderer back;
    private final ModelRenderer bottom;
    private final ModelRenderer bottom_r1;
    private final ModelRenderer right;
    private final ModelRenderer right_r1;
    private final ModelRenderer left;
    private final ModelRenderer left_r1;

    public TrainModel() {
        texWidth = 64;
        texHeight = 64;

        front = new ModelRenderer(this);
        front.setPos(0.0F, 23.0F, 9.0F);


        front_grill_bumper_2_r1 = new ModelRenderer(this);
        front_grill_bumper_2_r1.setPos(10.0F, 1.0F, -10.0F);
        front.addChild(front_grill_bumper_2_r1);
        setRotationAngle(front_grill_bumper_2_r1, 0.0F, -1.5708F, 1.5708F);
        front_grill_bumper_2_r1.texOffs(46, 55).addBox(-13.0F, 4.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        front_grill_bumper_1_r1 = new ModelRenderer(this);
        front_grill_bumper_1_r1.setPos(0.0F, 1.0F, -10.0F);
        front.addChild(front_grill_bumper_1_r1);
        setRotationAngle(front_grill_bumper_1_r1, 0.0F, -1.5708F, 1.5708F);
        front_grill_bumper_1_r1.texOffs(46, 55).addBox(-13.0F, 4.0F, 0.0F, 1.0F, 2.0F, 2.0F, 0.0F, false);

        front_grill_base_r1 = new ModelRenderer(this);
        front_grill_base_r1.setPos(0.0F, 1.0F, -9.0F);
        front.addChild(front_grill_base_r1);
        setRotationAngle(front_grill_base_r1, 0.0F, -1.5708F, 1.5708F);
        front_grill_base_r1.texOffs(44, 10).addBox(-13.0F, -8.0F, 0.0F, 3.0F, 16.0F, 2.0F, 0.0F, false);
        front_grill_base_r1.texOffs(36, 1).addBox(-12.0F, -2.0F, 16.0F, 4.0F, 4.0F, 5.0F, 0.0F, false);
        front_grill_base_r1.texOffs(0, 35).addBox(-13.0F, -6.0F, 4.0F, 6.0F, 12.0F, 12.0F, 0.0F, false);

        front_whistle_r1 = new ModelRenderer(this);
        front_whistle_r1.setPos(-4.0F, 1.0F, -9.0F);
        front.addChild(front_whistle_r1);
        setRotationAngle(front_whistle_r1, 0.0F, -1.5708F, 1.5708F);
        front_whistle_r1.texOffs(54, 6).addBox(-11.0F, -1.0F, 16.0F, 2.0F, 2.0F, 2.0F, 0.0F, false);

        front_engine_brace_2_r1 = new ModelRenderer(this);
        front_engine_brace_2_r1.setPos(14.0F, 1.0F, -7.0F);
        front.addChild(front_engine_brace_2_r1);
        setRotationAngle(front_engine_brace_2_r1, 0.0F, 1.5708F, -1.5708F);
        front_engine_brace_2_r1.texOffs(38, 29).addBox(9.0F, -8.0F, 10.0F, 3.0F, 2.0F, 4.0F, 0.0F, true);

        front_engine_brace_1_r1 = new ModelRenderer(this);
        front_engine_brace_1_r1.setPos(-14.0F, 1.0F, -7.0F);
        front.addChild(front_engine_brace_1_r1);
        setRotationAngle(front_engine_brace_1_r1, 0.0F, -1.5708F, 1.5708F);
        front_engine_brace_1_r1.texOffs(38, 29).addBox(-12.0F, -8.0F, 10.0F, 3.0F, 2.0F, 4.0F, 0.0F, false);

        front_r1 = new ModelRenderer(this);
        front_r1.setPos(0.0F, 1.0F, -9.0F);
        front.addChild(front_r1);
        setRotationAngle(front_r1, 0.0F, 3.1416F, 0.0F);
        front_r1.texOffs(0, 0).addBox(-8.0F, -10.0F, 8.0F, 16.0F, 8.0F, 2.0F, 0.0F, false);

        grill = new ModelRenderer(this);
        grill.setPos(7.0F, -1.0F, -22.0F);
        front.addChild(grill);
        setRotationAngle(grill, -0.7854F, 0.0F, 0.0F);


        grill_r1 = new ModelRenderer(this);
        grill_r1.setPos(0.0F, 0.0F, 0.0F);
        grill.addChild(grill_r1);
        setRotationAngle(grill_r1, 0.0F, -1.5708F, 1.5708F);
        grill_r1.texOffs(52, 29).addBox(0.0F, 6.0F, 0.0F, 1.0F, 2.0F, 4.0F, 0.0F, false);
        grill_r1.texOffs(26, 35).addBox(0.0F, 9.0F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        grill_r1.texOffs(0, 35).addBox(0.0F, 4.0F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        grill_r1.texOffs(0, 40).addBox(0.0F, 2.0F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        grill_r1.texOffs(26, 40).addBox(0.0F, 11.0F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        grill_r1.texOffs(52, 41).addBox(0.0F, 13.0F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);
        grill_r1.texOffs(52, 35).addBox(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 5.0F, 0.0F, false);

        back = new ModelRenderer(this);
        back.setPos(0.0F, 24.0F, 0.0F);
        back.texOffs(0, 0).addBox(-8.0F, -10.0F, 8.0F, 16.0F, 8.0F, 2.0F, 0.0F, false);
        back.texOffs(0, 28).addBox(-8.0F, -14.0F, 7.0F, 16.0F, 4.0F, 3.0F, 0.0F, false);

        bottom = new ModelRenderer(this);
        bottom.setPos(0.0F, 24.0F, 0.0F);


        bottom_r1 = new ModelRenderer(this);
        bottom_r1.setPos(0.0F, 0.0F, 0.0F);
        bottom.addChild(bottom_r1);
        setRotationAngle(bottom_r1, 0.0F, -1.5708F, 1.5708F);
        bottom_r1.texOffs(0, 10).addBox(-10.0F, -8.0F, 0.0F, 20.0F, 16.0F, 2.0F, 0.0F, false);

        right = new ModelRenderer(this);
        right.setPos(0.0F, 24.0F, 0.0F);


        right_r1 = new ModelRenderer(this);
        right_r1.setPos(0.0F, 0.0F, 0.0F);
        right.addChild(right_r1);
        setRotationAngle(right_r1, 0.0F, -1.5708F, 0.0F);
        right_r1.texOffs(0, 0).addBox(-8.0F, -10.0F, 6.0F, 16.0F, 8.0F, 2.0F, 0.0F, false);

        left = new ModelRenderer(this);
        left.setPos(0.0F, 24.0F, 0.0F);


        left_r1 = new ModelRenderer(this);
        left_r1.setPos(0.0F, 0.0F, 0.0F);
        left.addChild(left_r1);
        setRotationAngle(left_r1, 0.0F, 1.5708F, 0.0F);
        left_r1.texOffs(0, 0).addBox(-8.0F, -10.0F, 6.0F, 16.0F, 8.0F, 2.0F, 0.0F, false);




    }


    @Override
    public void setupAnim(TrainEntity p_225597_1_, float p_225597_2_, float p_225597_3_, float value, float p_225597_5_, float p_225597_6_) {
//        float it = (float) ((System.currentTimeMillis() % 3600L) / 10D * (float) Math.PI / 180D);
//
//        this.front.yRot = it;
//        this.bottom.yRot = it;
//        this.back.yRot = it;
//        this.right.yRot = it;
//        this.left.yRot = it;


        this.bottom.y = 4.0F - value;
        this.back.y = 4.0F - value;
        this.right.y = 4.0F - value;
        this.left.y = 4.0F - value;
        this.front.y = 4.0F - value;

    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        front.render(matrixStack, buffer, packedLight, packedOverlay);
        back.render(matrixStack, buffer, packedLight, packedOverlay);
        bottom.render(matrixStack, buffer, packedLight, packedOverlay);
        right.render(matrixStack, buffer, packedLight, packedOverlay);
        left.render(matrixStack, buffer, packedLight, packedOverlay);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}
