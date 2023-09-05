package carpetgcaddition.network.utils;

import carpet.patches.EntityPlayerMPFake;
import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.delegate.Func;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;
import carpetgcaddition.network.packet.s2c.AllFakePlayerPropsS2CPacket;
import carpetgcaddition.network.packet.s2c.FakePlayerGameJOES2CPacket;
import carpetgcaddition.network.packet.s2c.FakePlayerPropsS2CPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.List;

public class FakePlayerServerNetworkUtils {
    public static void onPlayerLoggedIn(ServerPlayerEntity player) {
        var mgr = CarpetGCAdditionMod.getCarpetServer().getFakePlayerPropsManager();
        var fakePlayersNames = mgr.getFakePlayerNames();

        if (fakePlayersNames == null || fakePlayersNames.isEmpty()) {
            ServerPlayNetworking.send(player, AllFakePlayerPropsS2CPacket.empty());
            return;
        }

        var fakePlayers = new String[fakePlayersNames.size()];
        fakePlayersNames.toArray(fakePlayers);

        var props = new FakePlayerAdditionProperties[fakePlayers.length];
        for (int i = 0; i < fakePlayers.length; i++) {
            props[i] = mgr.getProperties(fakePlayers[i]).get();
        }

        ServerPlayNetworking.send(player, new AllFakePlayerPropsS2CPacket(fakePlayers, props));
    }

    public static void onFakePlayerLoggedIn(EntityPlayerMPFake player) {
        var realPlayers = getRealPlayers(player.server.getPlayerManager());
        if (realPlayers.isEmpty()) {
            return;
        }

        if (CarpetGCAdditionMod.getCarpetServer().getFakePlayerPropsManager().getFakePlayerNames().isEmpty()) {
            return;
        }

        var packet = new FakePlayerGameJOES2CPacket(player.getGameProfile().getName(), false);
        for (var p : realPlayers) {
            ServerPlayNetworking.send(p, packet);
        }
    }

    public static void onFakePlayerLoggedOut(EntityPlayerMPFake player) {
        var realPlayers = getRealPlayers(player.server.getPlayerManager());
        if (realPlayers.isEmpty()) {
            return;
        }

        var packet = new FakePlayerGameJOES2CPacket(player.getGameProfile().getName(), true);
        for (var p : realPlayers) {
            ServerPlayNetworking.send(p, packet);
        }
    }

    public static void onFakePlayerAdditionPropUpdate(String playerName, MinecraftServer server) {
        var prop = CarpetGCAdditionMod.getCarpetServer().getFakePlayerPropsManager().getProperties(playerName);
        if (prop.isEmpty() || server.getPlayerManager().getPlayer(playerName) == null) {
            return;
        }

        var realPlayers = getRealPlayers(server.getPlayerManager());
        if (realPlayers.isEmpty()) {
            return;
        }

        var packet = new FakePlayerPropsS2CPacket(playerName, prop.get());
        for (var p : realPlayers) {
            ServerPlayNetworking.send(p, packet);
        }
    }

    private static List<ServerPlayerEntity> getRealPlayers(PlayerManager mgr) {
        var fakePlayers = CarpetGCAdditionMod.getCarpetServer().getFakePlayerPropsManager().getFakePlayerNames();
        return mgr.getPlayerList()
            .stream()
            .filter(e -> !fakePlayers.contains(e.getGameProfile().getName()))
            .toList();
    }
}
