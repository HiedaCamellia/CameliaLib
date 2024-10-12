package org.hiedacamellia.camellialib.core.data.state;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public abstract class AutoStateProvider  extends BlockStateProvider {
    public AutoStateProvider(PackOutput gen,String modid, ExistingFileHelper helper) {
        super(gen, modid, helper);
    }

    @Override
    protected void registerStatesAndModels() {

        getKnownBlocks().forEach(block -> simpleBlockWithItem(block,cubeAll(block)));
        getKnownLogs().forEach(this::logBlock);

    }


    public abstract Iterable<Block> getKnownBlocks();

    public abstract Iterable<RotatedPillarBlock> getKnownLogs();
    //return EXAMPLE.BLOCKS.getEntries().stream().map(DeferredHolder::get).collect(Collectors.toSet());
}