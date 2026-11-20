package de.tsgscraft.advancedplayerclothing.client.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.config.CustomPlayerModelEnum;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {
    @Shadow
    public Camera camera;

    @ModifyExpressionValue(
            method = "getRenderer(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/client/renderer/entity/EntityRenderer;",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/client/resources/PlayerSkin;model()Lnet/minecraft/client/resources/PlayerSkin$Model;"
            )
    )
    private PlayerSkin.Model modifyPlayerModel(PlayerSkin.Model original, Entity entity) {
        if (entity instanceof AbstractClientPlayer player) {
            if (player.getUUID().equals(Minecraft.getInstance().player.getUUID())) {
                return switch (ConfigValues.CUSTOM_PLAYER_MODEL) {
                    case CustomPlayerModelEnum.WIDE -> PlayerSkin.Model.WIDE;
                    case CustomPlayerModelEnum.SLIM -> PlayerSkin.Model.SLIM;
                    default -> original;
                };
            }
        }
        return original;
    }
}
