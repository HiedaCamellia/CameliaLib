package org.hiedacamellia.camellialib.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.hiedacamellia.camellialib.common.entity.ThrowableItemEntity;
import org.joml.Quaternionf;

public class OnReturningItemRender extends ThrowableItemRender {

    public OnReturningItemRender(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public void render(ThrowableItemEntity entity, float entityYaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        if (entity.isReturning()) {
            matrixStack.mulPose(new Quaternionf().rotationY((float) Math.toRadians(entity.tickCount * 20)));
        }
        super.render(entity, entityYaw, partialTicks, matrixStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ThrowableItemEntity entity) {
        return BuiltInRegistries.ITEM.getKey(entity.getItem().getItem());
    }
}
