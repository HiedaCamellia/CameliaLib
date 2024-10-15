package org.hiedacamellia.camellialib.common.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.LockCode;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public abstract class TickableBlockEntity extends RandomizableContainerBlockEntity implements WorldlyContainer {

    private int tickCount;

    protected TickableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
        tickCount = 0;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        tickCount = tag.getInt("tickCount");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("tickCount", tickCount);
    }

    public void tick(BlockState state,Level level, BlockPos pos, RandomSource random){
        if(tickCount==0)
            return;
        if(tickCount==1){
            assemble(state, level, pos, random);
        }
        if(tickCount>=1) {
            tickCount--;
        }
    }

    public void setTickCount(int tickCount) {
        this.tickCount = tickCount;
    }

    public int getTickCount() {
        return tickCount;
    }

    public abstract void assemble(BlockState state,Level level, BlockPos pos, RandomSource random);

    public abstract void tryAssemble(BlockState state, Level level);
}
