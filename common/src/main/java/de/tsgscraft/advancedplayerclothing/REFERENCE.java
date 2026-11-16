package de.tsgscraft.advancedplayerclothing;

import de.tsgscraft.advancedplayerclothing.client.screen.ClothingSelectionScreen;
import de.tsgscraft.advancedplayerclothing.platform.Services;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class REFERENCE {


    // Main mod (advancedclothing)
    public static final String MOD_ID = "advancedplayerclothing";
    public static final String MOD_NAME = "AdvancedPlayerClothing";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);

    public static ResourceLocation debugSkin = ResourceLocation.parse("advancedplayerclothing:textures/entity/player_debug.png");
    public static ResourceLocation customSkin = ResourceLocation.parse("advancedplayerclothing:textures/entity/player.png");

    public static boolean isClientSlim = false;

    public static boolean compatibleServer = false;

    public static final ModelLayerLocation ANCHOR_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "anchor_layer"), "main");

    public static final ModelLayerLocation SLIM_ARMOR_LAYER =
            new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MOD_ID, "slim_armor_layer"), "main");





    // Female Gender Mod (fmg)
    public static final String fmg_MODID = "wildfire_gender";





    // First Person Mod (firstperson)
    public static final String fp_MODID = "firstperson";

    public static boolean skipHead = false;

    public static void updateFirstPerson() {
        skipHead = false;
        LocalPlayer player = Minecraft.getInstance().player;

        if (Services.PLATFORM.isModLoaded(fp_MODID)) {
            skipHead = Services.COMPAT.shouldSkinHead();
            if (ConfigValues.DEBUG_FIRST_PERSON)
                player.sendSystemMessage(Component.literal("First Person Mod detected. Skip head: " + skipHead));
        }else if (ConfigValues.DEBUG_FIRST_PERSON)
            player.sendSystemMessage(Component.literal("First Person Mod not found."));

    }





    // Ragdoll (sable_player_ragdoll)
    public static final String rd_MODID = "sable_player_ragdoll";
    public static final Map<UUID, Boolean> playerRagdollStates = new HashMap<>();

    public static boolean isPlayerRagdoll(ServerPlayer player) {
        if (Services.PLATFORM.isModLoaded(rd_MODID)) {
            return Services.COMPAT.isPlayerRagdoll(player);
        }
        return false;
    }

    public static boolean isPlayerRagdoll(Player player) {
        if (Services.PLATFORM.isModLoaded(rd_MODID)) {
            return playerRagdollStates.getOrDefault(player.getUUID(), false);
        }
        return false;
    }





    // Utility method to check if the given UUID is the current player's UUID
    public static boolean isCurrentPlayer(UUID uuid) {
        return uuid.equals(Minecraft.getInstance().player.getUUID());
    }

    public static void updateRagdollPlayers(MinecraftServer server) {
        if (Services.PLATFORM.isModLoaded(rd_MODID)) {
            for (ServerPlayer player : server.getPlayerList().getPlayers()) {
                boolean isRagdoll = Services.COMPAT.isPlayerRagdoll(player);
                playerRagdollStates.put(player.getUUID(), isRagdoll);
            }
        }
    }
}
