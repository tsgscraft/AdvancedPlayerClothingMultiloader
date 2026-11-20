package de.tsgscraft.advancedplayerclothing.events;

import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.CommonClass;
import de.tsgscraft.advancedplayerclothing.client.C_REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.ClothingCommand;
import de.tsgscraft.advancedplayerclothing.client.ClothingSave;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.modifiers.ClothingModifiers;
import de.tsgscraft.advancedplayerclothing.client.render.AnchorLayer;
import de.tsgscraft.advancedplayerclothing.client.render.AnchorLayerRender;
import de.tsgscraft.advancedplayerclothing.network.plugin.TestForServerPayload;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.player.Player;

public class ClientEvents {

    public static void registerClientEvents() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClothingCommand.Command);
        });

        ClientPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            try {
                Services.NETWORK.sendToServer(new TestForServerPayload((byte) 0));
            }catch (Exception ignored) {}
            if (CommonClass.isClientSide()) ClothingSave.loadActiveClothingData();
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, server) -> {
            C_REFERENCE.compatibleServer = false;
            ClothingRegistry.getInstance().clearActiveClothingData(false);
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!ConfigValues.ENABLED) return;

            Minecraft minecraft = Minecraft.getInstance();

            if (minecraft.level == null) return;

            for (Player player : minecraft.level.players()) {
                ClothingModifiers.applyModifiersToPlayer(player);
            }
        });

        ResourceManagerHelper.get(PackType.CLIENT_RESOURCES).registerReloadListener(new ReloadListener());

        KeyBindingHelper.registerKeyBinding(C_REFERENCE.toggleEditGUI);



        EntityModelLayerRegistry.registerModelLayer(C_REFERENCE.ANCHOR_LAYER, () -> AnchorLayer.createBaseLayer(false));
        EntityModelLayerRegistry.registerModelLayer(C_REFERENCE.SLIM_ARMOR_LAYER, () -> AnchorLayer.createBaseLayer(true));

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityRenderer instanceof PlayerRenderer playerRenderer) {
                        registrationHelper.register(
                                new AnchorLayerRender(playerRenderer, context.getModelSet())
                        );
                    }
                }
        );
    }
}
