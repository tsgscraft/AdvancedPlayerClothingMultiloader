package de.tsgscraft.advancedplayerclothing.platform.services;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public interface ICompatHelper {

    // First Person Mod
    boolean shouldSkinHead();

    // Sable Ragdoll
    boolean isPlayerRagdoll(ServerPlayer player);
}
