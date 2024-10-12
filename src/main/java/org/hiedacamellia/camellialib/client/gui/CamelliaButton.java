package org.hiedacamellia.camellialib.client.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;

public class CamelliaButton extends Button {
    public final int x;
    public final int y;

    protected CamelliaButton(int x, int y,int w,int h, Component message, OnPress onPress, @Nullable Tooltip tooltip) {
        super(x, y, w, h, message, onPress, Button.DEFAULT_NARRATION);
        this.x = x;
        this.y = y;
        this.setTooltip(tooltip);
    }

    public void enableRender(){
        this.visible=true;
    }

    public void disableRender(){
        this.visible=false;
    }

}
