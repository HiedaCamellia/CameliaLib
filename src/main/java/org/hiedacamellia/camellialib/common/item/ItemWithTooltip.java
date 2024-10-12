package org.hiedacamellia.camellialib.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemWithTooltip extends Item {
    public ItemWithTooltip(Properties properties) {
        super(properties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Item.@NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(itemstack.getItem());
        if (!Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.camellialib.press_shift").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        } else {
            String[] description = Component.translatable(key.toLanguageKey()+".desc").getString().split("§n");
            for (String line : description) {
                list.add(Component.literal(line));
            }
        }
    }
}
