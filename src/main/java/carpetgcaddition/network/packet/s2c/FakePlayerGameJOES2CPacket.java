package carpetgcaddition.network.packet.s2c;

import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;
import carpetgcaddition.network.packet.IFabricPacketApply;
import carpetgcaddition.network.utils.ClientFakePlayerPacketApplyUtils;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public record FakePlayerGameJOES2CPacket(String playerName, boolean isExit) implements FabricPacket, IFabricPacketApply {
    public static final PacketType<FakePlayerGameJOES2CPacket> TYPE =
        PacketType.create(
            new Identifier(CarpetGCAdditionMod.PACKET_NAMESPACE, "fake_player_joe"),
            FakePlayerGameJOES2CPacket::new
        );

    public FakePlayerGameJOES2CPacket(String playerName, boolean isExit) {
        this.playerName = playerName;
        this.isExit = isExit;
    }

    public FakePlayerGameJOES2CPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readBoolean());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(playerName);
        buf.writeBoolean(isExit);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Override
    public void apply() {
        if (this.isExit) {
            ClientFakePlayerPacketApplyUtils.removeProperties(this.playerName);
        } else {
            ClientFakePlayerPacketApplyUtils.addProperties(this.playerName, new FakePlayerAdditionProperties());
        }
    }
}
