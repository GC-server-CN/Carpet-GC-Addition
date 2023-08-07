package carpetgcaddition;

import carpet.CarpetExtension;
import carpet.CarpetServer;
import carpet.patches.EntityPlayerMPFake;
import carpetgcaddition.commands.FakePlayerCommand;
import carpetgcaddition.fakeplayeraddition.FakePlayerPropertiesManager;
import carpetgcaddition.logging.loggers.CarpetGCAdditionLoggerRegistry;
import carpetgcaddition.translation.Translator;
import carpetgcaddition.network.utils.FakePlayerServerNetworkUtils;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;

public class CarpetGCAdditionServer implements CarpetExtension {
    private FakePlayerPropertiesManager fakePlayerPropsManager;

    public static CarpetGCAdditionServer create(){
        var server = new CarpetGCAdditionServer();
        CarpetServer.manageExtension(server);
        return server;
    }

    @Override
    public String version() {
        return "carpet-gc-addition";
    }

    @Override
    public void onGameStarted() {
        fakePlayerPropsManager = new FakePlayerPropertiesManager();
        CarpetServer.settingsManager.parseSettingsClass(CarpetGCAdditionSettings.class);
    }

    @Override
    public void registerLoggers() {
        CarpetGCAdditionLoggerRegistry.initLoggers();
    }

    @Override
    public Map<String, String> canHasTranslations(String lang) {
        var translator = Translator.getTranslator(lang);
        return translator == null ? new HashMap<>() : translator.getTranslations();
    }

    @Override
    public void onPlayerLoggedIn(ServerPlayerEntity player) {
        if (player instanceof EntityPlayerMPFake fp) {
            fakePlayerPropsManager.add(player.getGameProfile().getName());
            FakePlayerServerNetworkUtils.onFakePlayerLoggedIn(fp);
        } else {
            FakePlayerServerNetworkUtils.onPlayerLoggedIn(player);
        }
    }

    @Override
    public void onPlayerLoggedOut(ServerPlayerEntity player) {
        if (player instanceof EntityPlayerMPFake fp) {
            fakePlayerPropsManager.remove(player.getGameProfile().getName());
            FakePlayerServerNetworkUtils.onFakePlayerLoggedOut(fp);
        }
    }

    @Override
    public void registerCommands(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        FakePlayerCommand.register(dispatcher, registryAccess);
    }

    public FakePlayerPropertiesManager getFakePlayerPropsManager() {
        return fakePlayerPropsManager;
    }
}
