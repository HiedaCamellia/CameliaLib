package org.hiedacamellia.camellialib.core.data.lang;

import net.minecraft.data.PackOutput;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.LanguageProvider;

public abstract class CamelliaLanguageProvider extends LanguageProvider {

    public CamelliaLanguageProvider(PackOutput output, String modid, String locale) {
        super(output, modid, locale);
    }

    public void addWithTooltip(Item key, String value, String tooltip) {
        add(key.getDescriptionId(), value);
        add(key.getDescriptionId()+".desc", tooltip);
    }

    public void addWithTooltip(Block key, String value, String tooltip) {
        add(key.getDescriptionId(), value);
        add(key.getDescriptionId()+".desc", tooltip);
    }

    public void addWithTooltip(ItemStack key, String value, String tooltip) {
        add(key.getDescriptionId(), value);
        add(key.getDescriptionId()+".desc", tooltip);
    }

    public void addWithTooltip(MobEffect key, String value, String tooltip) {
        add(key.getDescriptionId(), value);
        add(key.getDescriptionId()+".desc", tooltip);
    }

    public void addWithTooltip(TagKey<?> key, String value, String tooltip) {
        add(Tags.getTagTranslationKey(key), value);
        add(Tags.getTagTranslationKey(key)+".desc", tooltip);
    }

    public void addWithTooltip(String key, String value, String tooltip) {
        add(key, value);
        add(key+".desc", tooltip);
    }
}
