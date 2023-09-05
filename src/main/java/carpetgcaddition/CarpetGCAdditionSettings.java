package carpetgcaddition;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Rule;
import carpetgcaddition.utils.ChunkLoaderUtils;
import carpetgcaddition.validators.RangeValidator;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.Nullable;

import static carpet.api.settings.RuleCategory.EXPERIMENTAL;
import static carpet.api.settings.RuleCategory.FEATURE;
import static carpet.api.settings.RuleCategory.SURVIVAL;

public class CarpetGCAdditionSettings {
    public static final String GCServer = "GCServer";

    @Rule(categories = GCServer)
    public static boolean noteBlockChunkLoader = false;

    @Rule(categories = GCServer)
    public static boolean pistonHeadChunkLoader = false;

    @Rule(
        validators = ValidateNoteBlockChunkLoaderTime.class,
        options = {"300"},
        strict = false,
        categories = GCServer
    )
    public static int noteBlockChunkLoaderTime = 300;
    private static class ValidateNoteBlockChunkLoaderTime extends RangeValidator<Integer> {
        public ValidateNoteBlockChunkLoaderTime() {
            super(1, 72000);
        }

        @Override
        public Integer validate(@Nullable ServerCommandSource source, CarpetRule<Integer> changingRule, Integer newValue, String userInput) {
            var r = super.validate(source, changingRule, newValue, userInput);
            if (r != null) {
                ChunkLoaderUtils.setNoteBlockTicketTime(noteBlockChunkLoaderTime);
            }
            return r;
        }
    }

    @Rule(
        validators = ValidateNoteBlockChunkLoaderNote.class,
        options = {"-1"},
        strict = false,
        categories = GCServer
    )
    public static int noteBlockChunkLoaderNote = -1;
    private static class ValidateNoteBlockChunkLoaderNote extends RangeValidator<Integer> {
        public ValidateNoteBlockChunkLoaderNote() {
            super(-1, 24);
        }
    }

    @Rule(
        validators = ValidateNoteBlockChunkLoaderRadius.class,
        options = {"2"},
        strict = false,
        categories = GCServer
    )
    public static int noteBlockChunkLoaderRadius = 2;
    private static class ValidateNoteBlockChunkLoaderRadius extends RangeValidator<Integer> {
        public ValidateNoteBlockChunkLoaderRadius() {
            super(1, 31);
        }
    }

    @Rule(categories = {GCServer, EXPERIMENTAL})
    public static boolean playerMessageProperOrderDisabled = false;

    @Rule(categories = GCServer)
    public static boolean fakePlayerCollisionWithPlayerDisabled = false;

    @Rule(categories = {FEATURE, SURVIVAL})
    public static boolean softDeepslate = false;

    @Rule(categories = {GCServer, FEATURE})
    public static boolean keepTickEntities = false;
}
