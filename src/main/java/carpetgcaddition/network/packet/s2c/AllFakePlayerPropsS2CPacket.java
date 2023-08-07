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

public class AllFakePlayerPropsS2CPacket implements FabricPacket, IFabricPacketApply {
    public static final PacketType<AllFakePlayerPropsS2CPacket> TYPE =
        PacketType.create(
            new Identifier(CarpetGCAdditionMod.PACKET_NAMESPACE,"all_fake_player_prop"),
            AllFakePlayerPropsS2CPacket::new
        );

    private int count;
    private String[] playerNames;
    private FakePlayerAdditionProperties[] props;

    public AllFakePlayerPropsS2CPacket(String[] playerNames, FakePlayerAdditionProperties[] props) {
        this.count = props.length;
        this.playerNames = playerNames;
        this.props = props;
    }

    public AllFakePlayerPropsS2CPacket(PacketByteBuf buf) {
        count = buf.readInt();
        playerNames = new String[count];
        props = new FakePlayerAdditionProperties[count];

        for (int i = 0; i < this.count; i++) {
            playerNames[i] = buf.readString();
            props[i] = readProp(buf);
        }
    }

    public static AllFakePlayerPropsS2CPacket empty() {
        return new AllFakePlayerPropsS2CPacket(new String[0], new FakePlayerAdditionProperties[0]);
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(count);
        for (int i = 0; i < count; i++) {
            buf.writeString(playerNames[i]);
            writeProp(buf, props[i]);
        }
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    @Override
    public void apply() {
        ClientFakePlayerPacketApplyUtils.setAllProperties(playerNames, props);
    }

    private void writeProp(PacketByteBuf buf, FakePlayerAdditionProperties prop) {
        buf.writeEnumConstant(prop.collisionWithPlayer);
    }

    private FakePlayerAdditionProperties readProp(PacketByteBuf buf) {
        var prop = new FakePlayerAdditionProperties();
        prop.collisionWithPlayer = buf.readEnumConstant(FakePlayerCollisionStatus.class);

        return prop;
    }
}
