package com.unrealdinnerbone.carts.entity;

import com.unrealdinnerbone.carts.CartContainer;
import com.unrealdinnerbone.carts.CartsRegistry;
import com.unrealdinnerbone.carts.lib.WhistleManger;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.minecart.AbstractMinecartEntity;
import net.minecraft.entity.item.minecart.ContainerMinecartEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.network.NetworkHooks;

public class TrainEntity extends ContainerMinecartEntity {

    private boolean whistle = false;
    private double burnTime;
    public double xPush;
    public double zPush;
    private Speed speed = Speed.NORMAL;
    private String name = "Train";

    public TrainEntity(EntityType<? extends TrainEntity> entityType, World world) {
        super(entityType, world);
    }

    private TrainEntity(double x, double y, double z, World world) {
        super(CartsRegistry.TRAIN.get(), x, y, z, world);
    }

    public static TrainEntity create(double x, double y, double z, World world) {
        return new TrainEntity(x, y, z, world);
    }

    public Type getMinecartType() {
        return Type.RIDEABLE;
    }

    public void setFlipped(boolean flipped) {
        this.flipped = flipped;
    }

    public void tick() {
        super.tick();
        if (!this.level.isClientSide()) {
            if(speed != Speed.STOP) {
                if (this.burnTime > 0) {
                    this.burnTime -= this.getSpeed().getFuel();
                }
                if (this.burnTime <= 0) {
                    setSpeed(Speed.STOP);
                    for(int i = 0; i < getContainerSize(); i++) {
                        ItemStack stack = getItem(i);
                        if(!stack.isEmpty()) {
                            int time = ForgeHooks.getBurnTime(stack);
                            if(time > 0) {
                                stack.shrink(1);
                                this.burnTime = time;
//                            this.cartDirection = Direction.FORWARDS;
                                setPushed(1, 1);
                                setSpeed(Speed.NORMAL);
                                break;
                            }
                        }
                    }
                }
            }
            if(whistle) {
                if (this.level.getGameTime() % 60 == 0L) {
                    this.level.playSound(null, this.blockPosition(), WhistleManger.getSound(null).get(), SoundCategory.BLOCKS, 0.2f, 1.0F);
                }
            }
            ItemStack stack = getItem(7);
            if(!stack.isEmpty()) {
                ITextComponent newName = stack.getHoverName();
                String formattedName = newName.getString();
                if(!formattedName.equals(this.name)) {
                    setCustomName(newName);
                    this.name = formattedName;
                }
            }
        }
        if (this.burnTime > 0 && this.random.nextInt(4) == 0) {
            this.level.addParticle(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.8D, this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }



    public void setPushed(double x, double z) {
        this.xPush = x;
        this.zPush = z;
    }

    @Override
    public IPacket<?> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    protected double getMaxSpeed() {
//        return super.getMaxSpeed();
        return 0.5f;
    }

    @Override
    public float getMaxCartSpeedOnRail() {
//        return super.getMaxCartSpeedOnRail();
        return 0.5f;
    }


    @Override
    public float getCurrentCartSpeedCapOnRail() {
//        return super.getCurrentCartSpeedCapOnRail();
        return 0.5f;
    }

    @Override
    public double getDragAir() {
//        return super.getDragAir();
        return 0.5f;
    }

    @Override
    public float getMaxSpeedAirLateral() {
//        return super.getMaxSpeedAirLateral();
        return 0.5f;
    }

    @Override
    public float getMaxSpeedAirVertical() {
//        return super.getMaxSpeedAirVertical();
        return 0.5f;
    }

    @Override
    public net.minecraft.util.Direction getMotionDirection() {
        return super.getMotionDirection();
    }


    @Override
    public ActionResultType interact(PlayerEntity playerEntity, Hand hand) {
        if(playerEntity.isShiftKeyDown()) {
            return super.interact(playerEntity, hand);
        }else {
            if (!this.level.isClientSide) {
                return playerEntity.startRiding(this) ? ActionResultType.CONSUME : ActionResultType.PASS;
            } else {
                return ActionResultType.SUCCESS;
            }
        }
    }

    protected void moveAlongTrack(BlockPos pPos, BlockState pState) {
        super.moveAlongTrack(pPos, pState);
        Vector3d vector3d = this.getDeltaMovement();
        double d2 = getHorizontalDistanceSqr(vector3d);
        double d3 = this.xPush * this.xPush + this.zPush * this.zPush;
        if (d3 > 1.0E-4D && d2 > 0.001D) {
            double d4 = MathHelper.sqrt(d2);
            double d5 = MathHelper.sqrt(d3);
            this.xPush = vector3d.x / d4 * d5;
            this.zPush = vector3d.z / d4 * d5;
        }

    }

    protected void applyNaturalSlowdown() {
        double d0 = this.xPush * this.xPush + this.zPush * this.zPush;
        if (d0 > 1.0E-7D) {
            d0 = (double)MathHelper.sqrt(d0);
            this.xPush /= d0;
            this.zPush /= d0;
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.8D, 0.0D, 0.8D).add(this.xPush, 0.0D, this.zPush));
        } else {
            this.setDeltaMovement(this.getDeltaMovement().multiply(0.98D, 0.0D, 0.98D));
        }

        super.applyNaturalSlowdown();
    }

    @Override
    public void moveMinecartOnRail(BlockPos pos) { //Non-default because getMaximumSpeed is protected
        AbstractMinecartEntity mc = getMinecart();
        double speed = this.speed.getSpeed();
        Vector3d vec3d1 = mc.getDeltaMovement();
        double x = MathHelper.clamp(speed * vec3d1.x, -speed, speed);
        double z = MathHelper.clamp(speed * vec3d1.z, -speed, speed);
        mc.move(MoverType.SELF, new Vector3d(x, 0.0D, z));
    }


    @Override
    protected void comeOffTrack() {
    }

    @Override
    public void push(Entity p_70108_1_) {
        super.push(p_70108_1_);
    }

    @Override
    public ITextComponent getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    public ItemStack getCartItem() {
        return new ItemStack(CartsRegistry.TRAIN_ITEM.get());
    }

    @Override
    protected Container createMenu(int id, PlayerInventory playerInventory) {
        return new CartContainer(id, playerInventory, this);
    }

    public Speed getSpeed() {
        return speed;
    }


    @Override
    public Vector3d getDeltaMovement() {
        return super.getDeltaMovement();
    }

    public void setWhistle(boolean whistle)
    {
        this.whistle = whistle;
    }

    @Override
    public void destroy(DamageSource p_94095_1_) {
        this.remove();
        if (this.level.getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            ItemStack itemstack = getCartItem();
            if (this.hasCustomName()) {
                itemstack.setHoverName(this.getCustomName());
            }
            this.spawnAtLocation(itemstack);
        }

    }


    @Override
    protected void addAdditionalSaveData(CompoundNBT compoundNBT) {
        super.addAdditionalSaveData(compoundNBT);
        compoundNBT.putDouble("PushX", this.xPush);
        compoundNBT.putDouble("PushZ", this.zPush);
        compoundNBT.putDouble("BurnTime", this.burnTime);
    }


    @Override
    protected void readAdditionalSaveData(CompoundNBT compoundNBT) {
        super.readAdditionalSaveData(compoundNBT);
        this.xPush = compoundNBT.getDouble("PushX");
        this.zPush = compoundNBT.getDouble("PushZ");
        this.burnTime = compoundNBT.getDouble("BurnTime");
//        this.cartDirection = Direction.values()[compoundNBT.getInt("Direction")];
    }

    @Override
    public int getContainerSize() {
        return 8;
    }

    public void setSpeed(Speed speed) {
        if(speed == Speed.STOP) {
            setPushed(0f, 0f);
        }
        for(Entity passenger : getPassengers()) {
            if(passenger instanceof PlayerEntity) {
                ((PlayerEntity) passenger).displayClientMessage(new StringTextComponent("Speed set to: " + speed.name()), true);
            }
        }
        this.speed = speed;
    }


    public enum Speed {
        STOP(0.0f, 0),
        SLOW(0.2f, 1),
        NORMAL(0.4f, 1.5),
        FAST(0.6f, 2);

        private final float speed;
        private final double fuel;

        Speed(float speed, double fuel) {
            this.speed = speed;
            this.fuel = fuel;
        }

        public float getSpeed() {
            return speed;
        }


        public Speed next() {
            return this == FAST ? this : values()[ordinal() + 1];
        }

        public Speed previous() {
            return this == STOP ? this : values()[ordinal() - 1];
        }

        public double getFuel() {
            return fuel;
        }
    }


    public enum Direction {
        BACKWARDS,
        FORWARDS;

    }
}
