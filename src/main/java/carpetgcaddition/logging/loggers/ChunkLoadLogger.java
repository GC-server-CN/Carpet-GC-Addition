package carpetgcaddition.logging.loggers;

import carpet.logging.Logger;
import carpet.utils.Messenger;
import carpetgcaddition.utils.ChunkLoaderUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.lang.reflect.Field;

public class ChunkLoadLogger extends Logger {
    private static final Text MESSAGE_ITEM_SPACE_TEXT = Messenger.s("  ");
    private String messagePrefix;

    public ChunkLoadLogger(Field acceleratorField, String logName, String messagePrefix, String def, String[] options, boolean strictOptions) {
        super(acceleratorField, logName, def, options, strictOptions);
        this.messagePrefix = messagePrefix;
    }

    public void log(ChunkLoaderUtils.LoadChunkResult loadedChunk, World blockWorld){
        log((option, player) -> getLogMessage(loadedChunk, blockWorld, option, player));
    }

    private Text[] getLogMessage(ChunkLoaderUtils.LoadChunkResult loadedChunk, World blockWorld, String option, PlayerEntity player) {
        RegistryKey<World> dim = switch (option) {
            case "overworld" -> World.OVERWORLD;
            case "nether" -> World.NETHER;
            case "end" -> World.END;
            default -> player.getWorld().getRegistryKey();
        };

        if (blockWorld.getRegistryKey() != dim) return null;

        var pos = loadedChunk.getBlockPos();

        return new Text[]{Messenger.c(
                messagePrefix,
                MESSAGE_ITEM_SPACE_TEXT,
                Messenger.s("(%d, %d, %d)".formatted(pos.getX(), pos.getY(), pos.getZ()), "c"),
                MESSAGE_ITEM_SPACE_TEXT,
                Messenger.s(loadedChunk.getChunkPos().toString(), "y"),
                MESSAGE_ITEM_SPACE_TEXT,
                Messenger.s(loadedChunk.getExpiryTicks() + " tick")
        )};
    }
}
