package de.tsgscraft.advancedplayerclothing.network.clothing;

import de.tsgscraft.advancedplayerclothing.REFERENCE;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

public record SetClothingPayload(
        String playerUUID,
        String clothingType,
        String clothingId
) implements CustomPacketPayload {

    public static final Type<SetClothingPayload> TYPE =
            new Type<>(
                    ResourceLocation.fromNamespaceAndPath(
                            REFERENCE.MOD_ID,
                            "set_clothing"
                    )
            );

    public static final StreamCodec<ByteBuf, SetClothingPayload> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.STRING_UTF8,
                    SetClothingPayload::playerUUID,

                    ByteBufCodecs.STRING_UTF8,
                    SetClothingPayload::clothingType,

                    ByteBufCodecs.STRING_UTF8,
                    SetClothingPayload::clothingId,

                    SetClothingPayload::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}