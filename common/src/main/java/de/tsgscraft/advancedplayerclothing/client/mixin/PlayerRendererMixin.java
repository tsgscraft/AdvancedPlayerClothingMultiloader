package de.tsgscraft.advancedplayerclothing.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import de.tsgscraft.advancedplayerclothing.ConfigValues;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerRendererMixin(EntityRendererProvider.Context context, PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(context, model, shadowRadius);
    }

    @Shadow
    private void setModelProperties(AbstractClientPlayer p_117775_) {}

    @ModifyVariable(
            method = "renderHand",
            at = @At("STORE"),
            ordinal = 0
    )
    private ResourceLocation modifyHandTexture(
            ResourceLocation original,
            @Local(argsOnly = true) AbstractClientPlayer player
    ) {
        if (ConfigValues.DEBUG_SKIN_TEXTURE) {
            return REFERENCE.debugSkin;
        }

        if (ConfigValues.CUSTOM_SKIN && REFERENCE.isCurrentPlayer(player.getUUID())) {
            return REFERENCE.customSkin;
        }

        return original;
    }

    @Inject(
            method = "getTextureLocation(Lnet/minecraft/client/player/AbstractClientPlayer;)Lnet/minecraft/resources/ResourceLocation;",
            at = @At("TAIL"),
            cancellable = true
    )
    public void getTextureLocation(AbstractClientPlayer player, CallbackInfoReturnable<ResourceLocation> cir) {
        if (ConfigValues.DEBUG_SKIN_TEXTURE) {
            cir.setReturnValue(REFERENCE.debugSkin);
            cir.cancel();
        }else if (ConfigValues.CUSTOM_SKIN && REFERENCE.isCurrentPlayer(player.getUUID())) {
            cir.setReturnValue(REFERENCE.customSkin);
            cir.cancel();
        }
    }
}
