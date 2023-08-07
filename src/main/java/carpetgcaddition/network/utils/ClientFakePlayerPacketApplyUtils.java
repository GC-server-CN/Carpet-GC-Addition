package carpetgcaddition.network.utils;

import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;

public class ClientFakePlayerPacketApplyUtils{
    public static void updateProperties(String playerName, FakePlayerAdditionProperties prop) {
        if (prop == null) {
            return;
        }

        var existing = CarpetGCAdditionMod.getClientFakePlayerPropsManager().getProperties(playerName);
        if (existing.isEmpty()) {
            addProperties(playerName, prop);
            return;
        }

        var t = existing.get();
        t.collisionWithPlayer = prop.collisionWithPlayer;
    }

    public static void addProperties(String playerName, FakePlayerAdditionProperties prop) {
        CarpetGCAdditionMod.getClientFakePlayerPropsManager().add(playerName, prop);
    }

    public static void removeProperties(String playerName) {
        CarpetGCAdditionMod.getClientFakePlayerPropsManager().remove(playerName);
    }

    public static void setAllProperties(String[] playerName, FakePlayerAdditionProperties[] props) {
        if (playerName.length != props.length) {
            CarpetGCAdditionMod.LOGGER.warn("try set all fake player properties, but data validate failed");
            return;
        }

        var mgr = CarpetGCAdditionMod.getClientFakePlayerPropsManager();
        mgr.clear();

        for (int i = 0; i < playerName.length; i++) {
            mgr.add(playerName[i], props[i]);
        }
    }
}
