package org.hiedacamellia.camellialib.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.hiedacamellia.camellialib.core.util.ItemStackHolder;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public abstract class ItemButton extends CamelliaButton {

    private final ItemStackHolder itemStack;

    protected ItemButton(int x, int y, Component message, Button.OnPress onPress, ItemStack itemStack, @Nullable Tooltip tooltip) {
        super(x, y,16,16,  message, onPress, tooltip);
        this.itemStack = new ItemStackHolder(itemStack);
    }

    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        renderBg(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.renderItem(this.itemStack.get(),this.x, this.y);

    }

    public abstract void renderBg(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick);










}
