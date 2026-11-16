package de.tsgscraft.advancedplayerclothing.client.mixin.fmg;


import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.wildfire.render.BreastSide;
import com.wildfire.render.GenderLayer;
import de.tsgscraft.advancedplayerclothing.ConfigValues;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(GenderLayer.class)
public class GenderLayerMixin<T extends LivingEntity, M extends HumanoidModel<T>> {
    @Inject(
            method = "renderBreast",
            at = @At("HEAD"),
            cancellable = true
    )
    private void renderBreast(LivingEntity entity, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int light, int overlay, BreastSide side, CallbackInfo ci) {

        ci.cancel();
    }

    /*

    private @Nullable RenderType getRenderLayer(T entity) {
        RenderLayerParent var3 = this.context;
        if (var3 instanceof LivingEntityRenderer<T, M> renderer) {
            Minecraft client = Minecraft.getInstance();
            boolean bodyVisible = !entity.isInvisible();
            boolean translucent = !bodyVisible && client.player != null && !entity.isInvisibleTo(client.player);
            boolean glowing = client.shouldEntityAppearGlowing(entity);
            return renderer.getRenderType(entity, bodyVisible, translucent, glowing);
        } else {
            throw new IllegalStateException("context renderer is not a LivingEntityRenderer");
        }
    }
     */
    @WrapOperation(
            method = "getRenderLayer",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/client/renderer/entity/LivingEntityRenderer;getRenderType(Lnet/minecraft/world/entity/LivingEntity;ZZZ)Lnet/minecraft/client/renderer/RenderType;"),
            remap = false)
    private RenderType advancedplayerclothing$wrapRenderType(LivingEntityRenderer<?, ?> instance,
                                                             LivingEntity entity,
                                                             boolean bodyVisible, boolean translucent, boolean glowing,
                                                             Operation<RenderType> original) {
        ResourceLocation resourcelocation;
        if (entity instanceof Player player) {
            if (ConfigValues.DEBUG_SKIN_TEXTURE) {
                resourcelocation = C_REFERENCE.debugSkin;
            } else if (ConfigValues.CUSTOM_SKIN && C_REFERENCE.isCurrentPlayer(player.getUUID())) {
                resourcelocation = C_REFERENCE.customSkin;
            } else {
                return original.call(instance, entity, bodyVisible, translucent, glowing);
            }
        }else {
            return original.call(instance, entity, bodyVisible, translucent, glowing);
        }
        if (translucent) {
            return RenderType.itemEntityTranslucentCull(resourcelocation);
        } else if (bodyVisible) {
            return instance.getModel().renderType(resourcelocation);
        } else {
            return glowing ? RenderType.outline(resourcelocation) : null;
        }
    }
}
