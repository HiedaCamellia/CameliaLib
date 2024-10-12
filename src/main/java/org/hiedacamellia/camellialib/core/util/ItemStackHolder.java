package org.hiedacamellia.camellialib.core.util;

import net.minecraft.world.item.ItemStack;

public class ItemStackHolder {
    private ItemStack itemStack;

    public ItemStackHolder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackHolder() {
        this.itemStack = ItemStack.EMPTY;
    }

    public ItemStack get() {
        return itemStack;
    }

    public void set(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public boolean isEmpty() {
        return itemStack.isEmpty();
    }
}
