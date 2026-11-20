package de.tsgscraft.advancedplayerclothing.events;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.ClothingCommand;
import de.tsgscraft.advancedplayerclothing.client.config.ConfigValues;
import de.tsgscraft.advancedplayerclothing.client.modifiers.ClothingModifiers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

@SuppressWarnings("removal")
@EventBusSubscriber(modid = REFERENCE.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeoForgeEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPlayer(RenderPlayerEvent.Pre event) {
        if (!ConfigValues.ENABLED) return;
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
        if (ConfigValues.DEBUG_SKIN)
            model.setAllVisible(false);
        ClothingModifiers.configureDefaultSecondLayer(model, event.getEntity().getUUID());
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRenderPlayer(RenderPlayerEvent.Post event) {
        PlayerModel<AbstractClientPlayer> model = event.getRenderer().getModel();
        model.setAllVisible(true);
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!ConfigValues.ENABLED) return;

        Minecraft minecraft = Minecraft.getInstance();

        if (minecraft.level == null) return;

        for (Player player : minecraft.level.players()) {
            ClothingModifiers.applyModifiersToPlayer(player);
        }
    }

    @SubscribeEvent
    public static void registerCommands(RegisterClientCommandsEvent event) {
        event.getDispatcher().register(ClothingCommand.Command);
    }
}