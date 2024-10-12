package org.hiedacamellia.camellialib.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.hiedacamellia.camellialib.common.blockentity.TickableBlockEntity;

public abstract class TickableBlock extends Block implements EntityBlock {
    public TickableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if(blockEntity instanceof TickableBlockEntity tickableBlockEntity){
            tickableBlockEntity.tick(state, level, pos, random);
            if(tickableBlockEntity.getTickCount()>0)
                level.scheduleTick(pos, state.getBlock(), 1);
        }
    }

}
