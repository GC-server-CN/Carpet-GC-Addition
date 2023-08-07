package carpetgcaddition.fakeplayeraddition;

import java.util.*;

public class FakePlayerPropertiesManager {
    private Map<String, FakePlayerAdditionProperties> fakePlayers;
    private List<String> fakePlayerNames;

    public FakePlayerPropertiesManager() {
        fakePlayers = new HashMap<>();
        fakePlayerNames = new ArrayList<>();
    }

    public Optional<FakePlayerAdditionProperties> getProperties(String playerName) {
        var properties = fakePlayers.get(playerName);
        return properties == null ? Optional.empty() : Optional.of(properties);
    }

    public List<String> getFakePlayerNames() {
        return fakePlayerNames;
    }

    public void add(String playerName) {
        add(playerName, null);
    }

    public void add(String playerName, FakePlayerAdditionProperties properties) {
        if (playerName == null || playerName.isEmpty() || fakePlayerNames.contains(playerName)) {
            return;
        }

        if (properties == null) {
            properties = new FakePlayerAdditionProperties();
        }

        fakePlayers.put(playerName, properties);
        fakePlayerNames.add(playerName);
    }

    public boolean remove(String playerName) {
        fakePlayerNames.remove(playerName);
        return fakePlayers.remove(playerName) != null;
    }

    public boolean contains(String playerName) {
        return fakePlayerNames.contains(playerName);
    }

    public void clear() {
        fakePlayers.clear();
        fakePlayerNames.clear();
    }
}
