package de.tsgscraft.advancedplayerclothing.network.clothing;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record FullClothingSyncPayload(
        String playerUUID,
        Map<String, String> clothingData
) implements CustomPacketPayload {

    public static final Type<FullClothingSyncPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            REFERENCE.MOD_ID,
                            "set_full_clothing_sync"
                    )
            );

    public static final StreamCodec<ByteBuf, FullClothingSyncPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    FullClothingSyncPayload::playerUUID,
                    ByteBufCodecs.map(
                            HashMap::new,
                            ByteBufCodecs.STRING_UTF8,
                            ByteBufCodecs.STRING_UTF8
                    ),
                    FullClothingSyncPayload::clothingData,

                    FullClothingSyncPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
