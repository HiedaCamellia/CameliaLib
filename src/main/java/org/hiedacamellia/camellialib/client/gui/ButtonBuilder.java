package org.hiedacamellia.camellialib.client.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;

public abstract class ButtonBuilder<T> {
    private int x;
    private int y;
    private int w;
    private int h;
    private final Component message;
    private final Button.OnPress onPress;
    private Tooltip tooltip;

    public ButtonBuilder(Component message, Button.OnPress onPress) {
        this.message = message;
        this.onPress = onPress;
    }

    public ButtonBuilder<T> pos(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public ButtonBuilder<T> size(int w, int h) {
        this.w = w;
        this.h = h;
        return this;
    }

    public ButtonBuilder<T> tooltip(@Nullable Tooltip tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public abstract T build();

}
