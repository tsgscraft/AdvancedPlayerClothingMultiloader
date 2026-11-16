package de.tsgscraft.advancedplayerclothing.platform;

import de.tsgscraft.advancedplayerclothing.client.compat.FirstPersonMod;
import de.tsgscraft.advancedplayerclothing.client.compat.RagdollMod;
import de.tsgscraft.advancedplayerclothing.platform.services.ICompatHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.ClientHooks;

public class NeoForgeCompatHelper implements ICompatHelper {

    @Override
    public boolean shouldSkinHead() {
        return FirstPersonMod.shouldSkipHead();
    }

    @Override
    public boolean isPlayerRagdoll(ServerPlayer player) {
        return RagdollMod.isPlayerRagdoll(player);
    }
}
