package carpetgcaddition;

import carpetgcaddition.fakeplayeraddition.FakePlayerPropertiesManager;
import carpetgcaddition.translation.Translator;
import net.fabricmc.api.ModInitializer;
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
        server = CarpetGCAdditionServer.create();
    }

    public static void onInitializeClient() {
        client = CarpetGCAdditionClient.create();
    }
}