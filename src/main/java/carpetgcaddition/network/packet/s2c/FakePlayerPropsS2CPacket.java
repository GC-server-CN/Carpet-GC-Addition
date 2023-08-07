package carpetgcaddition.network.packet.s2c;

import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;
import carpetgcaddition.fakeplayeraddition.FakePlayerCollisionStatus;
import carpetgcaddition.network.packet.IFabricPacketApply;
import carpetgcaddition.network.utils.ClientFakePlayerPacketApplyUtils;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class FakePlayerPropsS2CPacket implements FabricPacket, IFabricPacketApply {
    public static final PacketType<FakePlayerPropsS2CPacket> TYPE =
        PacketType.create(
            new Identifier(CarpetGCAdditionMod.PACKET_NAMESPACE,"fake_player_prop"),
            FakePlayerPropsS2CPacket::new
        );

    private String playerName;
    private  FakePlayerAdditionProperties prop;

    public FakePlayerPropsS2CPacket(String playerName, FakePlayerAdditionProperties prop) {
        this.playerName = playerName;
        this.prop = prop;
    }

    public FakePlayerPropsS2CPacket(PacketByteBuf buf) {
        this.prop = new FakePlayerAdditionProperties();

        this.playerName = buf.readString();
        this.prop.collisionWithPlayer = buf.readEnumConstant(FakePlayerCollisionStatus.class);
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(playerName);
        buf.writeEnumConstant(this.prop.collisionWithPlayer);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Override
    public void apply() {
        ClientFakePlayerPacketApplyUtils.updateProperties(playerName, prop);
    }
}
