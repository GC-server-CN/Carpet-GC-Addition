package carpetgcaddition.network.packet.s2c;

import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;
import carpetgcaddition.network.packet.ICustomPayloadApply;
import carpetgcaddition.network.utils.ClientFakePlayerPacketApplyUtils;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record FakePlayerGameJOES2CPayload(String playerName, boolean isExit) implements CustomPayload, ICustomPayloadApply {
    public static final Id<FakePlayerGameJOES2CPayload> ID =
            new Id<>(Identifier.of(CarpetGCAdditionMod.PACKET_NAMESPACE, "fake_player_joe"));

    public static final PacketCodec<PacketByteBuf, FakePlayerGameJOES2CPayload> CODEC =
            PacketCodec.of(FakePlayerGameJOES2CPayload::encode, FakePlayerGameJOES2CPayload::decode);

    public FakePlayerGameJOES2CPayload(PacketByteBuf buf) {
        this(buf.readString(), buf.readBoolean());
    }

    private static void encode(FakePlayerGameJOES2CPayload value, PacketByteBuf buf) {
        buf.writeString(value.playerName);
        buf.writeBoolean(value.isExit);
    }

    private static FakePlayerGameJOES2CPayload  decode(PacketByteBuf buf) {
        return new FakePlayerGameJOES2CPayload(buf.readString(), buf.readBoolean());
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }

    @Override
    public void apply() {
        if (this.isExit) {
            ClientFakePlayerPacketApplyUtils.removeProperties(this.playerName);
        } else {
            ClientFakePlayerPacketApplyUtils.addProperties(this.playerName, new FakePlayerAdditionProperties());
        }
    }
}
