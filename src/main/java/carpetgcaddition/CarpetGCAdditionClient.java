package carpetgcaddition;

import carpetgcaddition.fakeplayeraddition.FakePlayerPropertiesManager;

public class CarpetGCAdditionClient {
    public static CarpetGCAdditionClient create() {
        return new CarpetGCAdditionClient();
    }

    private FakePlayerPropertiesManager fakePlayerPropsManager;
    public FakePlayerPropertiesManager getFakePlayerPropsManager() {
        return fakePlayerPropsManager;
    }

    private CarpetGCAdditionClient() {
        fakePlayerPropsManager = new FakePlayerPropertiesManager();
    }
}
