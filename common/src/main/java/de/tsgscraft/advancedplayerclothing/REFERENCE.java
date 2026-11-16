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





    // Female Gender Mod (fmg)
    public static final String fmg_MODID = "wildfire_gender";





    // First Person Mod (firstperson)
    public static final String fp_MODID = "firstperson";





    // Ragdoll (sable_player_ragdoll)
    public static final String rd_MODID = "sable_player_ragdoll";





}
