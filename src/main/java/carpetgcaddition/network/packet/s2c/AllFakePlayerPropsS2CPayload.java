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

public class AllFakePlayerPropsS2CPayload implements CustomPayload, ICustomPayloadApply {

    public static final Id<AllFakePlayerPropsS2CPayload> ID = new Id<>(Identifier.of(CarpetGCAdditionMod.PACKET_NAMESPACE,"all_fake_player_prop"));

    public static final PacketCodec<PacketByteBuf, AllFakePlayerPropsS2CPayload> CODEC =
        PacketCodec.of(AllFakePlayerPropsS2CPayload::encode, AllFakePlayerPropsS2CPayload::decode);

    private int count;
    private String[] playerNames;
    private FakePlayerAdditionProperties[] props;

    public AllFakePlayerPropsS2CPayload(String[] playerNames, FakePlayerAdditionProperties[] props) {
        this.count = props.length;
        this.playerNames = playerNames;
        this.props = props;
    }

    @Override
    public Id<? extends CustomPayload> getId() { return ID; }

    @Override
    public void apply() {
        ClientFakePlayerPacketApplyUtils.setAllProperties(playerNames, props);
    }

    public static AllFakePlayerPropsS2CPayload empty() {
        return new AllFakePlayerPropsS2CPayload(new String[0], new FakePlayerAdditionProperties[0]);
    }

    private static void encode(AllFakePlayerPropsS2CPayload value, PacketByteBuf buf) {
        buf.writeInt(value.count);
        for (int i = 0; i < value.count; i++) {
            buf.writeString(value.playerNames[i]);
            encodeProp(buf, value.props[i]);
        }
    }

    private static AllFakePlayerPropsS2CPayload  decode(PacketByteBuf buf) {
        var count = buf.readInt();
        var playerNames = new String[count];
        var props = new FakePlayerAdditionProperties[count];

        for (int i = 0; i < count; i++) {
            playerNames[i] = buf.readString();
            props[i] = decodeProp(buf);
        }

        return new AllFakePlayerPropsS2CPayload(playerNames, props);
    }

    private static void encodeProp(PacketByteBuf buf, FakePlayerAdditionProperties prop) {
        buf.writeEnumConstant(prop.collisionWithPlayer);
    }

    private static FakePlayerAdditionProperties decodeProp(PacketByteBuf buf) {
        var prop = new FakePlayerAdditionProperties();
        prop.collisionWithPlayer = buf.readEnumConstant(FakePlayerCollisionStatus.class);

        return prop;
    }
}
