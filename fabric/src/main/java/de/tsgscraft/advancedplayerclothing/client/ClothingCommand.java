package de.tsgscraft.advancedplayerclothing.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.tsgscraft.advancedplayerclothing.ClothingRegistry;
import de.tsgscraft.advancedplayerclothing.client.screen.clothing.ClothingSelectionScreen;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class ClothingCommand {
    public static final LiteralArgumentBuilder<FabricClientCommandSource> Command = ClientCommandManager.literal("advancedplayerclothing")
            .then(ClientCommandManager.literal("setmap")
                    .then(ClientCommandManager.argument("key", StringArgumentType.string())
                            .suggests((context, builder) -> {
                                for (String key : ClothingRegistry.getInstance().getClothingTypes()) {
                                    builder.suggest("\"" + key + "\"");
                                }
                                return builder.buildFuture();
                            })
                            .then(ClientCommandManager.argument("value", StringArgumentType.string())
                                    .suggests((context, builder) -> {
                                        String key = StringArgumentType.getString(context, "key");
                                        for (String value : ClothingRegistry.getInstance().getClothingTypeToIdMap().get(key)) {
                                            builder.suggest("\"" + value + "\"");
                                        }
                                        return builder.buildFuture();
                                    })
                                    .executes(context -> {
                                        if (context.getSource().getEntity() instanceof Player) {
                                            String key = StringArgumentType.getString(context, "key");
                                            String value = StringArgumentType.getString(context, "value");
                                            ClothingRegistry.getInstance().setClothing(key, value);
                                        }
                                        return 1;
                                    })
                            )
                    )
            ).then(ClientCommandManager.literal("removemap")
                    .then(ClientCommandManager.argument("key", StringArgumentType.string())
                            .suggests((context, builder) -> {
                                for (String key : ClothingRegistry.getInstance().getClothingTypes()) {
                                    builder.suggest("\"" + key + "\"");
                                }
                                return builder.buildFuture();
                            })
                            .executes(context -> {
                                if (context.getSource().getEntity() instanceof Player) {
                                    String key = StringArgumentType.getString(context, "key");
                                    ClothingRegistry.getInstance().setClothing(key, "");
                                }
                                return 1;
                            })
                    )
            ).then(ClientCommandManager.literal("screen")
                    .executes(context -> {
                        Minecraft.getInstance().setScreen(new ClothingSelectionScreen());
                        return 1;
                    })
            ).then(ClientCommandManager.literal("getData")
                    .then(ClientCommandManager.argument("player", StringArgumentType.string())
                            .executes(context -> {
                                return 1;
                            }))
                    .executes(context -> {
                        if (context.getSource().getEntity() instanceof Player player) {
                            player.sendSystemMessage(Component.literal(ClothingRegistry.getInstance().getActiveClothingElements().toString()));
                        }
                        return 1;
                    })
            );
}
