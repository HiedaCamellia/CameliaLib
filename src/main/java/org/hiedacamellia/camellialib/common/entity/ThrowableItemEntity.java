package org.hiedacamellia.camellialib.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.hiedacamellia.camellialib.core.debug.CamelliaDebug;

public class ThrowableItemEntity extends ThrowableItemProjectile {


    public ThrowableItemEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level) { super(entityType, level); }

    public ThrowableItemEntity(EntityType<? extends ThrowableItemProjectile> entityType, double x, double y, double z, Level level) {
        super(entityType, x, y, z, level);
    }

    public ThrowableItemEntity(EntityType<? extends ThrowableItemProjectile> entityType, LivingEntity shooter, Level level) {
        super(entityType, shooter, level);
    }

    @Override protected Item getDefaultItem() { return Items.SNOWBALL; }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        Entity entity = result.getEntity();
        CamelliaDebug.send(entity.toString());
        if (this.level().isClientSide()) return;
        if (this.getOwner() instanceof Player player) {
            entity.hurt(damageSources().playerAttack(player), (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
    }
}
