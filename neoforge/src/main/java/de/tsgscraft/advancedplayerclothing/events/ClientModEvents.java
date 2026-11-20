package de.tsgscraft.advancedplayerclothing.events;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.render.AnchorLayer;
import de.tsgscraft.advancedplayerclothing.client.render.AnchorLayerRender;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = REFERENCE.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(C_REFERENCE.toggleEditGUI);
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(C_REFERENCE.ANCHOR_LAYER, () -> AnchorLayer.createBaseLayer(false));
        event.registerLayerDefinition(C_REFERENCE.SLIM_ARMOR_LAYER, () -> AnchorLayer.createBaseLayer(true));
    }

    @SubscribeEvent
    public static void onAddLayers(EntityRenderersEvent.AddLayers event) {
        event.getSkins().forEach(
                skin -> {
                    PlayerRenderer renderer =
                            event.getSkin(skin);

                    renderer.addLayer(
                            new AnchorLayerRender(
                                    renderer,
                                    event.getEntityModels()
                            )
                    );
                }
        );
    }
}
