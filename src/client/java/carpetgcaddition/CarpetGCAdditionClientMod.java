package carpetgcaddition;

import carpetgcaddition.network.packet.ICustomPayloadApply;
import carpetgcaddition.network.packet.s2c.AllFakePlayerPropsS2CPayload;
import carpetgcaddition.network.packet.s2c.FakePlayerGameJOES2CPayload;
import carpetgcaddition.network.packet.s2c.FakePlayerPropsS2CPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.packet.CustomPayload;

public class CarpetGCAdditionClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CarpetGCAdditionMod.onInitializeClient();
        //CarpetGCAdditionMod.registerPayloads();

        ClientPlayNetworking.registerGlobalReceiver(AllFakePlayerPropsS2CPayload.ID, CarpetGCAdditionClientMod::applyPayload);
        ClientPlayNetworking.registerGlobalReceiver(FakePlayerGameJOES2CPayload.ID, CarpetGCAdditionClientMod::applyPayload);
        ClientPlayNetworking.registerGlobalReceiver(FakePlayerPropsS2CPayload.ID, CarpetGCAdditionClientMod::applyPayload);
    }

    private static <T extends CustomPayload & ICustomPayloadApply> void applyPayload(T payload, ClientPlayNetworking.Context context) {
        payload.apply();
    }
}
