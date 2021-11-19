package com.unrealdinnerbone.carts;

import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import com.tterrag.registrate.providers.RegistrateItemModelProvider;
import com.tterrag.registrate.util.entry.BlockEntry;
import com.tterrag.registrate.util.entry.ContainerEntry;
import com.tterrag.registrate.util.entry.TileEntityEntry;
import com.unrealdinnerbone.carts.block.FourWayRailBlock;
import com.unrealdinnerbone.carts.block.LeftTurnableRailBlock;
import com.unrealdinnerbone.carts.block.SpeedRailBlock;
import com.unrealdinnerbone.carts.block.TurnableRailBlock;
import com.unrealdinnerbone.carts.block.computercraft.RailDetectorBE;
import com.unrealdinnerbone.carts.block.computercraft.RailDetectorBlock;
import com.unrealdinnerbone.carts.client.TrainRender;
import com.unrealdinnerbone.carts.client.TrainScreen;
import com.unrealdinnerbone.carts.entity.TrainEntity;
import com.unrealdinnerbone.carts.item.TrainItem;
import com.unrealdinnerbone.carts.lib.CartsGroup;
import net.minecraft.block.AbstractRailBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.state.properties.RailShape;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.tterrag.registrate.providers.RegistrateRecipeProvider.hasItem;

public class CartsRegistry {

    public static void init() {}

    private static final Registrate REGISTRATE = Registrate.create(Carts.MOD_ID)
            .itemGroup(CartsGroup::new, "Carts");


    public static final Supplier<EntityType<TrainEntity>> TRAIN = REGISTRATE.entity("train", TrainEntity::new, EntityClassification.MISC)
            .properties(properties ->
                    properties
                            .sized(0.98F, 0.7F)
                            .setShouldReceiveVelocityUpdates(true)
                            .setUpdateInterval(3))
            .renderer(() -> TrainRender::new)
            .register();

    public static final ContainerEntry<CartContainer> TRAIN_CONTAINER = REGISTRATE.container("train", CartContainer::new, () -> TrainScreen::new)
            .register();

    public static final Supplier<TrainItem> TRAIN_ITEM = REGISTRATE.item("train", TrainItem::new)
            .model((ctx, prov) -> prov.generated(ctx::getEntry, new ResourceLocation("item/minecart")))
            .recipe((ctx, prov) -> ShapedRecipeBuilder
                    .shaped(ctx.get())
                    .pattern("IRI")
                    .pattern("III")
                    .define('I', Items.IRON_INGOT)
                    .define('R', Items.REDSTONE)
                    .unlockedBy("has", hasItem(Items.REDSTONE))
                    .save(prov))
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(Items.MINECART)
                    .requires(Items.REDSTONE, 1)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov, "train_upgrade"))
            .register();

    public static final Supplier<SoundEvent> WHISTLE = REGISTRATE.simple("whistle", SoundEvent.class, () -> new SoundEvent(new ResourceLocation(Carts.MOD_ID, "whistle")));

    public static final Supplier<RailDetectorBlock> RAIL_DETECTOR = REGISTRATE.block("rail_detector", RailDetectorBlock::new)
            .simpleItem()
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.get())
                    .pattern("III")
                    .pattern("IMI")
                    .pattern("III")
                    .define('I', Items.IRON_INGOT)
                    .define('M', Items.MINECART)
                    .unlockedBy("has", hasItem(Items.IRON_INGOT))
                    .save(prov))
            .register();

    public static final TileEntityEntry<RailDetectorBE> RAIL_DETECTOR_BE = REGISTRATE
            .tileEntity("rail_detector", RailDetectorBE::new)
            .validBlock(RAIL_DETECTOR::get)
            .register();

    private static final ITag.INamedTag<Block> RAILS = BlockTags.createOptional(new ResourceLocation("minecraft", "rails"));

    public static final BlockEntry<SpeedRailBlock> SLOW_RAIL = REGISTRATE.block("slow_rail", properties -> new SpeedRailBlock(properties, TrainEntity.Speed.SLOW))
            .properties(properties -> properties.noCollission().strength(0.7F).sound(SoundType.METAL))
            .blockstate(CartsRegistry::railBlockstate)
            .tag(RAILS)
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(Items.RAIL)
                    .requires(Items.REDSTONE)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov))
            .item().model(CartsRegistry::itemGenerated).build()
            .register();

    public static final BlockEntry<SpeedRailBlock> NORMAL_RAIL = REGISTRATE.block("normal_rail", properties -> new SpeedRailBlock(properties, TrainEntity.Speed.NORMAL))
            .properties(properties -> properties.noCollission().strength(0.7F).sound(SoundType.METAL))
            .blockstate(CartsRegistry::railBlockstate)
            .tag(RAILS)
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(Items.RAIL)
                    .requires(Items.REDSTONE, 2)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov))
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(SLOW_RAIL.get())
                    .requires(Items.REDSTONE, 1)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov, "slow_to_normal_rail"))
            .item().model(CartsRegistry::itemGenerated).build()
            .register();

    public static final BlockEntry<SpeedRailBlock> FAST_RAIL = REGISTRATE.block("fast_rail", properties -> new SpeedRailBlock(properties, TrainEntity.Speed.FAST))
            .properties(properties -> properties.noCollission().strength(0.7F).sound(SoundType.METAL))
            .blockstate(CartsRegistry::railBlockstate)
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(Items.RAIL)
                    .requires(Items.REDSTONE, 3)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov))
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(NORMAL_RAIL.get())
                    .requires(Items.REDSTONE, 1)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov, "normal_to_fast_rail"))
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(SLOW_RAIL.get())
                    .requires(Items.REDSTONE, 2)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov, "slow_to_fast_rail"))
            .tag(RAILS)
            .item().model(CartsRegistry::itemGenerated).build()
            .register();

    public static final BlockEntry<SpeedRailBlock> STOP_RAIL = REGISTRATE.block("stop_rail", properties -> new SpeedRailBlock(properties, TrainEntity.Speed.STOP))
            .properties(properties -> properties.noCollission().strength(0.7F).sound(SoundType.METAL))
            .blockstate(CartsRegistry::railBlockstate)
            .recipe((ctx, prov) -> ShapelessRecipeBuilder.shapeless(ctx.get())
                    .requires(Items.RAIL)
                    .requires(Items.REDSTONE, 4)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov))
            .tag(RAILS)
            .item().model(CartsRegistry::itemGenerated).build()
            .register();

    public static final BlockEntry<FourWayRailBlock> FOUR_WAY = REGISTRATE.block("four_way", FourWayRailBlock::new)
            .properties(properties -> properties.noCollission().strength(0.7F).sound(SoundType.METAL))
            .blockstate(CartsRegistry::railBlockstate)
            .tag(RAILS)
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.get(), 5)
                    .pattern(" R ")
                    .pattern("RRR")
                    .pattern(" R ")
                    .define('R', Items.RAIL)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov))
            .item().model(CartsRegistry::itemGenerated).build()
            .register();


    public static final BlockEntry<TurnableRailBlock> TURNABLE = REGISTRATE.block("turnable", TurnableRailBlock::new)
            .properties(properties -> properties.noCollission().strength(0.7F).sound(SoundType.METAL))
            .blockstate(CartsRegistry::railPowerdBlockstate)
            .tag(RAILS)
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.get(), 3)
                    .pattern("   ")
                    .pattern(" RR")
                    .pattern(" R ")
                    .define('R', Items.RAIL)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov))
            .item().model(CartsRegistry::itemGenerated).build()
            .register();


    public static final BlockEntry<LeftTurnableRailBlock> LEFT_TURNABLE = REGISTRATE.block("left_turnable", LeftTurnableRailBlock::new)
            .properties(properties -> properties.noCollission().strength(0.7F).sound(SoundType.METAL))
            .blockstate(CartsRegistry::railPowerdBlockstate)
            .tag(RAILS)
            .recipe((ctx, prov) -> ShapedRecipeBuilder.shaped(ctx.get(), 3)
                    .pattern("   ")
                    .pattern(" R ")
                    .pattern("RR ")
                    .define('R', Items.RAIL)
                    .unlockedBy("has", hasItem(Items.RAIL))
                    .save(prov))
            .item().model(CartsRegistry::itemGenerated).build()
            .register();








    private static void railPowerdBlockstate(DataGenContext<Block, ? extends AbstractRailBlock> c, RegistrateBlockstateProvider p) {
        p.getVariantBuilder(c.get())
                .forAllStates(state -> {
                    RailShape shape = state.getValue(c.get().getShapeProperty());
                    String powerId = state.getValue(BlockStateProperties.POWERED) ? "on" : "off";

                    Direction direction = state.getValue(TurnableRailBlock.FACING);

                    int yRotation = 0;

                    switch(direction) {
                        case SOUTH:
                            yRotation = 180;
                            break;
                        case WEST:
                            yRotation = -90;
                            break;
                        case EAST:
                            yRotation = 90;
                            break;
                    }


                    String name = shape == RailShape.ASCENDING_NORTH || shape == RailShape.ASCENDING_EAST ? "ne" : "sw";
                    String blockName = c.get().getRegistryName().getPath();

                    ResourceLocation texture = new ResourceLocation(c.get().getRegistryName().getNamespace(), "block/" + c.get().getRegistryName().getPath());
                    String fileLocation = shape.isAscending() ? blockName + "_raised_" + name : blockName;
                    String parentId = shape.isAscending() ? "template_rail_raised_" + name : "rail_flat";

                    return ConfiguredModel.builder()
                            .modelFile(p.models()
                                    .withExistingParent(fileLocation + "_" + powerId, new ResourceLocation("minecraft", "block/" + parentId))
                                    .texture("rail", texture + "_" + powerId))
                            .rotationY(yRotation)
                            .build();
                });
    }


    private static final List<RailShape> SHAPES = Arrays.asList(RailShape.ASCENDING_EAST, RailShape.ASCENDING_WEST, RailShape.EAST_WEST);

    private static void railBlockstate(DataGenContext<Block, ? extends AbstractRailBlock> c, RegistrateBlockstateProvider p) {
        p.getVariantBuilder(c.get())
                .forAllStates(state -> {
                    RailShape shape = state.getValue(c.get().getShapeProperty());

                    int yRotation = SHAPES.contains(shape) ? 90 : 0;
                    int xRotation = 0;

                    String name = shape == RailShape.ASCENDING_NORTH || shape == RailShape.ASCENDING_EAST ? "ne" : "sw";
                    String blockName = c.get().getRegistryName().getPath();

//
                    if(state.hasProperty(SpeedRailBlock.FACING)) {
                        Direction facing = state.getValue(SpeedRailBlock.FACING);
                        switch(facing) {
                            case SOUTH:
                                yRotation = 180;
                                break;
                            case WEST:
                                yRotation = -90;
                                break;
                            case EAST:
                                yRotation = 90;
                        }
                    }

                    if(shape == RailShape.ASCENDING_WEST || shape == RailShape.ASCENDING_SOUTH) {
                        yRotation = 0;
                        if(shape == RailShape.ASCENDING_WEST) {
                            xRotation = 180;
                            yRotation = 90;
                        }else {
                            xRotation = 180;
                        }
                    }

                    ResourceLocation texture = new ResourceLocation(c.get().getRegistryName().getNamespace(), "block/" + c.get().getRegistryName().getPath());
                    String fileLocation = shape.isAscending() ? blockName + "_raised_" + name : blockName;
                    String parentId = shape.isAscending() ? "template_rail_raised_" + name : "rail_flat";

                    return ConfiguredModel.builder()
                            .modelFile(p.models()
                                    .withExistingParent(fileLocation, new ResourceLocation("minecraft", "block/" + parentId))
                                    .texture("rail", texture))
                            .rotationY(yRotation)
                            .rotationX(xRotation)
                            .build();
                });
    }

    private static void itemGenerated(DataGenContext<Item, BlockItem> ctx, RegistrateItemModelProvider provider) {
        provider.generated(ctx, new ResourceLocation(ctx.get().getRegistryName().getNamespace(), "block/" + ctx.get().getRegistryName().getPath()));
    }
}
