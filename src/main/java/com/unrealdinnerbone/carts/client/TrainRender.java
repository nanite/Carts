package com.unrealdinnerbone.carts.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.unrealdinnerbone.carts.Carts;
import com.unrealdinnerbone.carts.entity.TrainEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

public class TrainRender extends EntityRenderer<TrainEntity> {

    private static final ResourceLocation MINECART_LOCATION = new ResourceLocation(Carts.MOD_ID, "textures/entity/train.png");
    protected final EntityModel<TrainEntity> model = new TrainModel();

    public TrainRender(EntityRendererManager p_i46155_1_) {
        super(p_i46155_1_);
        this.shadowRadius = 0.7F;
    }

    public void render(TrainEntity trainEntity, float p_225623_2_, float p_225623_3_, MatrixStack matrixStack, IRenderTypeBuffer typeBuffer, int p_225623_6_) {
        super.render(trainEntity, p_225623_2_, p_225623_3_, matrixStack, typeBuffer, p_225623_6_);
        matrixStack.pushPose();
        long i = (long)trainEntity.getId() * 493286711L;
        i = i * i * 4392167121L + i * 98761L;
        float f = (((float)(i >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f1 = (((float)(i >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float f2 = (((float)(i >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        matrixStack.translate((double)f, (double)f1, (double)f2);
        double d0 = MathHelper.lerp((double)p_225623_3_, trainEntity.xOld, trainEntity.getX());
        double d1 = MathHelper.lerp((double)p_225623_3_, trainEntity.yOld, trainEntity.getY());
        double d2 = MathHelper.lerp((double)p_225623_3_, trainEntity.zOld, trainEntity.getZ());
        Vector3d vector3d = trainEntity.getPos(d0, d1, d2);
        float f3 = MathHelper.lerp(p_225623_3_, trainEntity.xRotO, trainEntity.xRot);
        if (vector3d != null) {
            Vector3d vector3d1 = trainEntity.getPosOffs(d0, d1, d2, (double)0.3F);
            Vector3d vector3d2 = trainEntity.getPosOffs(d0, d1, d2, (double)-0.3F);
            if (vector3d1 == null) {
                vector3d1 = vector3d;
            }

            if (vector3d2 == null) {
                vector3d2 = vector3d;
            }

            matrixStack.translate(vector3d.x - d0, (vector3d1.y + vector3d2.y) / 2.0D - d1, vector3d.z - d2);
            Vector3d vector3d3 = vector3d2.add(-vector3d1.x, -vector3d1.y, -vector3d1.z);
            if (vector3d3.length() != 0.0D) {
                vector3d3 = vector3d3.normalize();
                p_225623_2_ = (float)(Math.atan2(vector3d3.z, vector3d3.x) * 180.0D / Math.PI);
                f3 = (float)(Math.atan(vector3d3.y) * 73.0D);
            }
        }

        matrixStack.translate(0.0D, 0.375D, 0.0D);

        Direction direction = trainEntity.getMotionDirection();
        float rotation = direction == Direction.NORTH || direction == Direction.WEST ? 90 : 270;
        if(trainEntity.getCurrentDirection().isForwards()) {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation - p_225623_2_));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(f3));
        }else {
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(rotation - p_225623_2_));
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(f3));
        }



//        if(trainEntity.getCurrentDirection() == TrainEntity.Direction.BACKWARDS) {
//            matrixStack.mulPose(Vector3f.XP.rotationDegrees(-f3));
//        }else {
//        }
        float f5 = (float)trainEntity.getHurtTime() - p_225623_3_;
        float f6 = trainEntity.getDamage() - p_225623_3_;
        if (f6 < 0.0F) {
            f6 = 0.0F;
        }

        if (f5 > 0.0F) {
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(MathHelper.sin(f5) * f5 * f6 / 10.0F * (float)trainEntity.getHurtDir()));
        }

        matrixStack.scale(-1.0F, -1.0F, 1.0F);
        this.model.setupAnim(trainEntity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
        IVertexBuilder ivertexbuilder = typeBuffer.getBuffer(this.model.renderType(this.getTextureLocation(trainEntity)));
        this.model.renderToBuffer(matrixStack, ivertexbuilder, p_225623_6_, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStack.popPose();
    }

    public ResourceLocation getTextureLocation(TrainEntity p_110775_1_) {
        return MINECART_LOCATION;
    }
}
