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
    private static FakePlayerPropertiesManager clientFakePlayerPropsManager;

    public static CarpetGCAdditionServer getCarpetServer() {
        return server;
    }

    public static FakePlayerPropertiesManager getClientFakePlayerPropsManager() {
        return clientFakePlayerPropsManager;
    }

    @Override
    public void onInitialize() {
        Translator.init();
        clientFakePlayerPropsManager = new FakePlayerPropertiesManager();
        server = CarpetGCAdditionServer.create();
    }
}