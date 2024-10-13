package org.hiedacamellia.camellialib.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SwordItemWithTooltip extends SwordItem {

    public SwordItemWithTooltip(Tier tier, Properties properties) {
        super(tier, properties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, Item.@NotNull TooltipContext context, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemstack, context, list, flag);
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(itemstack.getItem());
        if (!Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.camellialib.press_shift").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        } else {
            String[] description = Component.translatable(key.toLanguageKey("item")+".desc").getString().split("§n");
            for (String line : description) {
                list.add(Component.literal(line));
            }
        }
    }
}
