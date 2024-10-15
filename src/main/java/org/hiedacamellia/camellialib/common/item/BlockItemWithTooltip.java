package org.hiedacamellia.camellialib.common.item;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;

public class BlockItemWithTooltip extends BlockItem {
    public BlockItemWithTooltip(Block block, Properties properties) {
        super(block, properties);
    }
    @Override
    public void appendHoverText(@NotNull ItemStack itemstack, @Nullable Level Level, @NotNull List<Component> list, @NotNull TooltipFlag flag) {
        super.appendHoverText(itemstack, Level, list, flag);
        ResourceLocation key = BuiltInRegistries.ITEM.getKey(itemstack.getItem());
        if (!Screen.hasShiftDown()) {
            list.add(Component.translatable("tooltip.camellialib.press_shift").withStyle(ChatFormatting.GRAY, ChatFormatting.ITALIC));
        } else {
            String[] description = Component.translatable(key.toLanguageKey("block")+".desc").getString().split("§n");
            for (String line : description) {
                list.add(Component.literal(line));
            }
        }
    }
}
