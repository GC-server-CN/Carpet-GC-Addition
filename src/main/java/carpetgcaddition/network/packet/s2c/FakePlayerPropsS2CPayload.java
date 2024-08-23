package carpetgcaddition.network.packet.s2c;

import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;
import carpetgcaddition.fakeplayeraddition.FakePlayerCollisionStatus;
import carpetgcaddition.network.packet.ICustomPayloadApply;
import carpetgcaddition.network.utils.ClientFakePlayerPacketApplyUtils;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FakePlayerPropsS2CPayload(String playerName, FakePlayerAdditionProperties prop) implements CustomPayload, ICustomPayloadApply {
    public static final Id<FakePlayerPropsS2CPayload> ID =
        new Id<>(Identifier.of(CarpetGCAdditionMod.PACKET_NAMESPACE,"fake_player_prop"));

    public static final PacketCodec<PacketByteBuf, FakePlayerPropsS2CPayload> CODEC =
        PacketCodec.of(FakePlayerPropsS2CPayload::encode, FakePlayerPropsS2CPayload::decode);

    private static void encode(FakePlayerPropsS2CPayload value, PacketByteBuf buf) {
        buf.writeString(value.playerName);
        buf.writeEnumConstant(value.prop.collisionWithPlayer);
    }

    private static FakePlayerPropsS2CPayload  decode(PacketByteBuf buf) {
        var playerName = buf.readString();
        var prop = new FakePlayerAdditionProperties();
        prop.collisionWithPlayer = buf.readEnumConstant(FakePlayerCollisionStatus.class);
        return new FakePlayerPropsS2CPayload(playerName, prop);
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }

    @Override
    public void apply() {
        ClientFakePlayerPacketApplyUtils.updateProperties(playerName, prop);
    }
}
