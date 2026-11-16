package de.tsgscraft.advancedplayerclothing.events;

import de.tsgscraft.advancedplayerclothing.CommonClass;
import de.tsgscraft.advancedplayerclothing.REFERENCE;
import de.tsgscraft.advancedplayerclothing.client.ClothingElement;
import de.tsgscraft.advancedplayerclothing.client.simpleClothing.ClothingResourceLoader;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class ReloadListener implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return ResourceLocation.fromNamespaceAndPath(REFERENCE.MOD_ID, "clothing_loader");
    }

    @Override
    public CompletableFuture<Void> reload(PreparationBarrier preparationBarrier, ResourceManager resourceManager, ProfilerFiller preparationsProfiler, ProfilerFiller reloadProfiler, Executor backgroundExecutor, Executor gameExecutor) {
        return CompletableFuture
                .supplyAsync(
                        () -> ClothingResourceLoader.loadClothing(resourceManager),
                        backgroundExecutor
                )
                .thenCompose(preparationBarrier::wait)
                .thenAcceptAsync(
                        loadedClothing -> {
                            for (ClothingElement clothing : loadedClothing.values()) {
                                clothing.renderInfo().bake();
                            }
                            CommonClass.getClothingRegistry().setClothingElements(loadedClothing);
                        },
                        gameExecutor
                );
    }
}
