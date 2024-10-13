package org.hiedacamellia.camellialib.common.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.hiedacamellia.camellialib.common.item.ThrowableItemWithTooltip;

public class ThrowableItemEntity extends ThrowableItemProjectile {

    private static final int MAX_RETURN_TICKS = 100;
    private boolean hitEntity, isReturning;
    private int returnTicks;

    public ThrowableItemEntity(EntityType<? extends ThrowableItemEntity> entityType, Level level) { super(entityType, level); }


    @Override protected Item getDefaultItem() { return Items.SNOWBALL; }

    @Override
    public void tick() {
        super.tick();
        if (isReturning && (++returnTicks > MAX_RETURN_TICKS || this.getOwner() == null || this.distanceToSqr(this.getOwner()) < 1.0)) {
            if (this.getOwner() instanceof Player player) player.getInventory().add(this.getItem());
            this.discard();
        } else if (isReturning) {
            this.setDeltaMovement(this.getOwner().position().subtract(this.position()).normalize().scale(0.5));
        }
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        if (this.level().isClientSide()) return;

        ItemStack itemStack = this.getItem();
        Item item = itemStack.getItem();

        if (item instanceof SwordItem && hitEntity) {
            isReturning = true;
            spawnItemDrop(itemStack);
        }

    }


    private void spawnItemDrop(ItemStack itemStack) {
        this.level().addFreshEntity(new ItemEntity(this.level(), this.getX(), this.getY(), this.getZ(), itemStack));
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (this.level().isClientSide()) return;

        Entity entity = result.getEntity();
        ItemStack itemStack = this.getItem();
        Item item = itemStack.getItem();

        if (item instanceof ThrowableItemWithTooltip && this.getOwner() instanceof Player player) {
            hitEntity = true;
            entity.hurt(damageSources().playerAttack(player), (float) player.getAttributeValue(Attributes.ATTACK_DAMAGE));
        }

    }

}
