package de.tsgscraft.advancedplayerclothing.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.ClothingElement;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.mixin.PlayerModelAccessor;
import de.tsgscraft.advancedplayerclothing.client.modifiers.ClothingModifiers;
import de.tsgscraft.advancedplayerclothing.client.modifiers.ModelPartModifiers;
import de.tsgscraft.advancedplayerclothing.platform.RagdollPart;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.network.chat.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AnchorLayerRender extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    private final AnchorLayer layer;
    private final AnchorLayer slimLayer;

    public static final Map<UUID, Map<String, ModelPartModifiers>> playerModifiers = new HashMap<>();

    public AnchorLayerRender(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> renderer, EntityModelSet modelSet) {
        super(renderer);

        layer = new AnchorLayer(
                modelSet.bakeLayer(C_REFERENCE.ANCHOR_LAYER),
                false
        );

        slimLayer = new AnchorLayer(
                modelSet.bakeLayer(C_REFERENCE.SLIM_ARMOR_LAYER),
                true
        );
    }

    public static boolean notRagdoll = true;
    public static RagdollPart currentRagdollPart = null;

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int light, AbstractClientPlayer entity, float limbSwing, float limbSwingAmount, float partialTicks, float age, float headYaw, float headPitch) {
        if (!ConfigValues.ENABLED) return;

        PlayerModel<AbstractClientPlayer> parent = this.getParentModel();
        layer.setAllVisible(false);
        if (notRagdoll) {
            parent.copyPropertiesTo(layer);
            layer.copyPlayerProperties(parent);
            layer.swimAmount = parent.swimAmount;

            ClothingModifiers.configureSecondLayer(layer, entity.getUUID());
        }else {
            stopAnim(layer);
            ClothingModifiers.configureSecondLayer(layer, entity.getUUID());
            if (currentRagdollPart != null) {
                if (layer.hat.visible) layer.hat.visible = currentRagdollPart == RagdollPart.HEAD;
                if (layer.jacket.visible) layer.jacket.visible = currentRagdollPart == RagdollPart.TORSO;
                if (layer.leftSleeve.visible) layer.leftSleeve.visible = currentRagdollPart == RagdollPart.LEFT_ARM;
                if (layer.rightSleeve.visible) layer.rightSleeve.visible = currentRagdollPart == RagdollPart.RIGHT_ARM;
                if (layer.leftPants.visible) layer.leftPants.visible = currentRagdollPart == RagdollPart.LEFT_LEG;
                if (layer.rightPants.visible) layer.rightPants.visible = currentRagdollPart == RagdollPart.RIGHT_LEG;
            }
        }

        int overlay = LivingEntityRenderer.getOverlayCoords(entity, 0.0F);

        try {
            ClothingRegistry.getInstance().getActiveClothingElements(entity.getUUID()).values().forEach(clothingElementId -> {
                ClothingElement clothingElement = ClothingRegistry.getInstance().getClothingElements().get(clothingElementId);
                if (clothingElement != null && clothingElement.renderInfo() != null) {
                    clothingElement.renderInfo().render(
                            poseStack,
                            buffer,
                            light,
                            overlay,
                            -1,
                            parent,
                            layer,
                            entity,
                            "generic"
                    );
                }
            });
        }catch (Exception e) {
            REFERENCE.LOG.error("Error while rendering clothing for player with UUID {}: {}", entity.getUUID(), e.getMessage());
            e.printStackTrace();
        }

        if (ConfigValues.ONLY_BREASTS) {
            return;
        }

        // Render the anchor layer itself (as a smaller secondLayer)
        VertexConsumer vertexConsumer = buffer.getBuffer(
                RenderType.entityCutoutNoCull(C_REFERENCE.getSkinTexture(entity))
        );

        if (((PlayerModelAccessor) parent).isSlim()) {
            copyVisibility(layer, slimLayer);
            if (notRagdoll) {
                layer.copyPropertiesTo(slimLayer);
                slimLayer.copyPlayerProperties(layer);
                slimLayer.swimAmount = layer.swimAmount;
            }else {
                stopAnim(slimLayer);
            }
            checkFirstPerson(entity, slimLayer);
            slimLayer.renderToBuffer(
                    poseStack,
                    vertexConsumer,
                    light,
                    overlay
            );
        } else {
            checkFirstPerson(entity, layer);
            layer.renderToBuffer(
                    poseStack,
                    vertexConsumer,
                    light,
                    overlay
            );
        }
    }

    public static void checkFirstPerson(AbstractClientPlayer player, AnchorLayer model) {
        C_REFERENCE.updateFirstPerson();
        if (ConfigValues.DEBUG_FIRST_PERSON) {
            player.sendSystemMessage(
                    Component.literal("player: " + player.getName().getString() + " | skipHead: " + C_REFERENCE.skipHead)
            );
        }
        if (Minecraft.getInstance().player == player) {
            if (C_REFERENCE.skipHead) {
                model.head.visible = false;
                model.hat.visible = false;
            }
        }
    }

    private void copyVisibility(AnchorLayer source, AnchorLayer target) {
        target.head.visible = source.head.visible;
        target.hat.visible = source.hat.visible;
        target.body.visible = source.body.visible;
        target.jacket.visible = source.jacket.visible;
        target.leftArm.visible = source.leftArm.visible;
        target.leftSleeve.visible = source.leftSleeve.visible;
        target.rightArm.visible = source.rightArm.visible;
        target.rightSleeve.visible = source.rightSleeve.visible;
        target.leftLeg.visible = source.leftLeg.visible;
        target.leftPants.visible = source.leftPants.visible;
        target.rightLeg.visible = source.rightLeg.visible;
        target.rightPants.visible = source.rightPants.visible;
    }

    private void stopAnim(AnchorLayer model) {
        stopAnim(model.head, 0);
        stopAnim(model.hat, 1);
        stopAnim(model.body, 2);
        stopAnim(model.jacket, 3);
        stopAnim(model.leftArm, 4);
        stopAnim(model.leftSleeve, 5);
        stopAnim(model.rightArm, 6);
        stopAnim(model.rightSleeve, 7);
        stopAnim(model.leftLeg, 8);
        stopAnim(model.leftPants, 9);
        stopAnim(model.rightLeg, 10);
        stopAnim(model.rightPants, 11);
    }

    private void stopAnim(ModelPart part, int partId) {
        part.xRot = 0.0F;
        part.yRot = 0.0F;
        part.zRot = 0.0F;
        part.x = 0.0F;
        part.y = 0.0F;
        part.z = 0.0F;
        switch (partId) {
            case 0, 1, 2, 3 -> part.setPos(0.0F, 0.0F, 0.0F);
            case 4, 5 -> part.setPos(5.0F, 2.5F, 0.0F);
            case 6, 7 -> part.setPos(-5.0F, 2.5F, 0.0F);
            case 8, 9 -> part.setPos(1.9F, 12.0F, 0);
            case 10, 11 -> part.setPos(-1.9F, 12.0F, 0);
        }
    }
}
