package org.hiedacamellia.camellialib.core.data.model;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.stream.Collectors;

public abstract class AutoItemModelProvider extends ItemModelProvider {

    public AutoItemModelProvider(PackOutput output, String modid, ExistingFileHelper helper) {
        super(output, modid, helper);
    }

    @Override
    protected void registerModels() {
        getKnownItems().forEach(item -> {
            if (!(item instanceof BlockItem)) {
                String path = BuiltInRegistries.ITEM.getKey(item).getPath();
                this.singleTexture(path, this.mcLoc("item/generated"), "layer0", this.modLoc("item/" + path));
            }
        });
    }

    public abstract Iterable<Item> getKnownItems();
    //return EXAMPLE.ITEMS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());

}