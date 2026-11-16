package de.tsgscraft.advancedplayerclothing.client.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import de.tsgscraft.advancedplayerclothing.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.modifiers.ClothingModifiers;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PlayerRenderer.class, priority = 1500)
public abstract class PlayerRendererPrePostMixin {

    @Unique
    private static final String RENDER =
            "render(Lnet/minecraft/client/player/AbstractClientPlayer;FFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V";

    @Inject(method = RENDER, at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/player/PlayerRenderer;setModelProperties(Lnet/minecraft/client/player/AbstractClientPlayer;)V",
            shift = At.Shift.AFTER))
    private void advancedplayerclothing$renderPre(AbstractClientPlayer player, float entityYaw, float partialTicks,
                                                  PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                                  CallbackInfo ci) {
        if (!ConfigValues.ENABLED) return;
        PlayerModel<AbstractClientPlayer> model = ((PlayerRenderer) (Object) this).getModel();
        if (ConfigValues.DEBUG_SKIN)
            model.setAllVisible(false);
        ClothingModifiers.configureDefaultSecondLayer(model, player.getUUID());
    }

    @Inject(method = RENDER, at = @At("RETURN"))
    private void advancedplayerclothing$renderPost(AbstractClientPlayer player, float entityYaw, float partialTicks,
                                                   PoseStack poseStack, MultiBufferSource buffer, int packedLight,
                                                   CallbackInfo ci) {
        ((PlayerRenderer) (Object) this).getModel().setAllVisible(true);
    }

}
