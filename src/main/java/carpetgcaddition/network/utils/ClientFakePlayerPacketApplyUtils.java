package carpetgcaddition.network.utils;

import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;
import carpetgcaddition.fakeplayeraddition.FakePlayerPropertiesManager;

import java.util.Optional;

public class ClientFakePlayerPacketApplyUtils{
    public static void updateProperties(String playerName, FakePlayerAdditionProperties prop) {
        if (prop == null) {
            return;
        }

        getClientFakePlayerPropsManager().ifPresent(mgr -> {
            var props = mgr.getProperties(playerName);
            if (props.isEmpty()) {
                addProperties(playerName, prop);
                return;
            }

            props.get().collisionWithPlayer = prop.collisionWithPlayer;
        });
    }

    public static void addProperties(String playerName, FakePlayerAdditionProperties prop) {
            getClientFakePlayerPropsManager().ifPresent(mgr -> mgr.add(playerName, prop));
    }

    public static void removeProperties(String playerName) {
        getClientFakePlayerPropsManager().ifPresent(mgr -> mgr.remove(playerName));
    }

    public static void setAllProperties(String[] playerName, FakePlayerAdditionProperties[] props) {
        if (playerName.length != props.length) {
            CarpetGCAdditionMod.LOGGER.warn("try set all fake player properties, but data validate failed");
            return;
        }

        getClientFakePlayerPropsManager().ifPresent(mgr -> {
            mgr.clear();
            for (int i = 0; i < playerName.length; i++) {
                mgr.add(playerName[i], props[i]);
            }
        });
    }

    private static Optional<FakePlayerPropertiesManager> getClientFakePlayerPropsManager() {
        var client = CarpetGCAdditionMod.getClient();
        return client == null ? Optional.empty() : Optional.of(client.getFakePlayerPropsManager());
    }
}
