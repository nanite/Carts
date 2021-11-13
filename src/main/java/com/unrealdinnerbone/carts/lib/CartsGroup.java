package com.unrealdinnerbone.carts.lib;

import com.unrealdinnerbone.carts.Carts;
import com.unrealdinnerbone.carts.CartsRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class CartsGroup extends ItemGroup {

    public CartsGroup() {
        super(Carts.MOD_ID);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(CartsRegistry.TRAIN_ITEM.get());
    }
}
