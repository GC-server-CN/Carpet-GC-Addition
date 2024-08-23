package carpetgcaddition;

import carpetgcaddition.fakeplayeraddition.FakePlayerPropertiesManager;
import carpetgcaddition.network.packet.s2c.AllFakePlayerPropsS2CPayload;
import carpetgcaddition.network.packet.s2c.FakePlayerGameJOES2CPayload;
import carpetgcaddition.network.packet.s2c.FakePlayerPropsS2CPayload;
import carpetgcaddition.translation.Translator;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarpetGCAdditionMod implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Carpet GC Addition");
    public static final String PACKET_NAMESPACE = "carpet_gc_addition";

    private static CarpetGCAdditionServer server;
    private static  CarpetGCAdditionClient client;

    public static CarpetGCAdditionServer getCarpetServer() {
        return server;
    }
    public static CarpetGCAdditionClient getClient() {
        return client;
    }

    @Override
    public void onInitialize() {
        Translator.init();
        registerPayloads();
        server = CarpetGCAdditionServer.create();
    }

    public static void onInitializeClient() {
        client = CarpetGCAdditionClient.create();
    }

    public static void registerPayloads() {
        PayloadTypeRegistry.playS2C().register(AllFakePlayerPropsS2CPayload.ID, AllFakePlayerPropsS2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(FakePlayerGameJOES2CPayload.ID, FakePlayerGameJOES2CPayload.CODEC);
        PayloadTypeRegistry.playS2C().register(FakePlayerPropsS2CPayload.ID, FakePlayerPropsS2CPayload.CODEC);
    }
}