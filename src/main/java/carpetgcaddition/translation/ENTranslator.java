package carpetgcaddition.translation;

import java.util.HashMap;

class ENTranslator {
    public static Translator create() {
        var t = new HashMap<String, String>();
        var builder = new TranslationBuilder(t);

        addRule(builder);
        addRuleValidErrorMessage(builder);

        return new Translator(t);
    }

    private static void addRule(TranslationBuilder builder) {
        builder.rule("noteBlockChunkLoader")
            .desc("Loads nearby chunks when noteblock is triggered.");
        builder.rule("pistonHeadChunkLoader")
            .desc("When piston pushes or retracts with obsidian above it, it will load a chunk where the corresponding piston head was located.")
            .extra("It is recommended to use with noteBlockChunkLoader for better experience.");
        builder.rule("noteBlockChunkLoaderTime")
            .desc("How long does a chunk loads for each noteblock trigger.");
        builder.rule("noteBlockChunkLoaderNote")
            .desc("Specify the required pitch for a triggered noteblock to load chunks.")
            .extra("Use -1 for loading chunks regardless the pitch of the noteblock.")
            .extra("Use 0 - 24 to specify the required pitch for noteblocks to load chunks.");
        builder.rule("noteBlockChunkLoaderRadius")
            .desc("The entity processing radius of the noteblock chunk loader.");

        builder.rule("playerMessageProperOrderDisabled")
            .desc("Disable player message send out order validate in server.")
            .extra("A brute force way to prevent player being kicked when trying to paste large litematics");
        builder.rule("fakePlayerCollisionWithPlayerDisabled")
            .desc("Disable collision between fake player and any real or fake players.")
            .extra("Can be override with collision option in /fakePlayer command for each fake player.");

        builder.rule("softDeepslate")
            .desc("can break deepslate in one click by netherite pickaxe (haste II + efficiency V required)");
    }

    private static void addRuleValidErrorMessage(TranslationBuilder builder) {
        builder.message(MessageTranslationKeys.RangeValidatorFail, "Value has to be between %s to %s");
        builder.message(MessageTranslationKeys.FakePlayerNotExisting, "Fake player not existing");
    }
}
