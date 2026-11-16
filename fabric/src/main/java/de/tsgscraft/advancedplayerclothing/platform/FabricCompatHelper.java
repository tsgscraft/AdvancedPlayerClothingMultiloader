package de.tsgscraft.advancedplayerclothing.platform;

import de.tsgscraft.advancedplayerclothing.client.compat.FirstPersonMod;
import de.tsgscraft.advancedplayerclothing.platform.services.ICompatHelper;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;

public class FabricCompatHelper implements ICompatHelper {
    @Override
    public boolean shouldSkinHead() {
        return FirstPersonMod.shouldSkipHead();
    }

    @Override
    public boolean isPlayerRagdoll(ServerPlayer player) {
        return false;
    }
}
