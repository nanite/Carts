package com.unrealdinnerbone.carts;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.common.ForgeHooks;

public class CartContainer extends Container {


    public CartContainer(ContainerType<CartContainer> type, int id, PlayerInventory playerInventory) {
        this(id, playerInventory, new Inventory(8));
    }

    public CartContainer(int id, PlayerInventory playerInventory, IInventory inventory) {
        super(CartsRegistry.TRAIN_CONTAINER.get(), id);
        final int playerInventoryStartX = 8;
        final int playerInventoryStartY = 84;
        final int slotSizePlus2 = 18;
        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, 9 + (row * 9) + column, playerInventoryStartX + (column * slotSizePlus2), playerInventoryStartY + (row * slotSizePlus2)));
            }
        }
        final int playerHotbarY = playerInventoryStartY + slotSizePlus2 * 3 + 4;
        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, playerInventoryStartX + (column * slotSizePlus2), playerHotbarY));
        }


        for (int i = 0; i < inventory.getContainerSize() - 1; i++) {
            this.addSlot(new FuelSlot(inventory, i, 26 + (i * 18), 19) {});
        }

        this.addSlot(new ItemSlot(inventory, inventory.getContainerSize() - 1, 80, 19 + 24));

    }

    @Override
    public boolean stillValid(PlayerEntity entity) {
        return true;
    }


    public ItemStack quickMoveStack(PlayerEntity p_82846_1_, int p_82846_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_82846_2_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_82846_2_ < 7) {
                if (!this.moveItemStackTo(itemstack1, 7, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, 7, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }



    public static class ItemSlot extends Slot {

        public ItemSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
            super(inventoryIn, index, xPosition, yPosition);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return itemStack.getItem() == Items.PAPER;
        }

        @Override
        public int getMaxStackSize() {
            return 1;
        }
    }

    public static class FuelSlot extends Slot{

        public FuelSlot(IInventory inventory, int slot, int x, int y) {
            super(inventory, slot, x, y);
        }

        @Override
        public boolean mayPlace(ItemStack itemStack) {
            return ForgeHooks.getBurnTime(itemStack) > 0;
        }
    }
}
