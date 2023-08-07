package carpetgcaddition;

import carpetgcaddition.network.packet.IFabricPacketApply;
import carpetgcaddition.network.packet.s2c.AllFakePlayerPropsS2CPacket;
import carpetgcaddition.network.packet.s2c.FakePlayerGameJOES2CPacket;
import carpetgcaddition.network.packet.s2c.FakePlayerPropsS2CPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.network.ClientPlayerEntity;

public class CarpetGCAdditionClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(AllFakePlayerPropsS2CPacket.TYPE, CarpetGCAdditionClientMod::applyPacket);
        ClientPlayNetworking.registerGlobalReceiver(FakePlayerGameJOES2CPacket.TYPE, CarpetGCAdditionClientMod::applyPacket);
        ClientPlayNetworking.registerGlobalReceiver(FakePlayerPropsS2CPacket.TYPE, CarpetGCAdditionClientMod::applyPacket);
    }

    private static void applyPacket(IFabricPacketApply packet, ClientPlayerEntity player, PacketSender sender) {
        packet.apply();
    }
}
