package carpetgcaddition.logging.loggers;

import carpet.logging.Logger;
import carpet.logging.LoggerRegistry;

import java.lang.reflect.Field;

public class CarpetGCAdditionLoggerRegistry {
    public static final String NOTE_BLOCK_CHUNK_LOADER_NAME = "noteBlockChunkLoader";
    public static final String PISTON_HEAD_CHUNK_LOADER_NAME = "pistonHeadChunkLoader";

    public static boolean __noteBlockChunkLoader;
    public static boolean __pistonHeadChunkLoader;

    public static void initLoggers() {
        registerLoggers();
    }

    private static void registerLoggers() {
        String[] worldOptions = new String[] {"dynamic", "overworld", "nether", "end"};

        registerLogger(new ChunkLoadLogger(getField("__noteBlockChunkLoader"), NOTE_BLOCK_CHUNK_LOADER_NAME, "w NoteBlockLoader", "dynamic", worldOptions, true));
        registerLogger(new ChunkLoadLogger(getField("__pistonHeadChunkLoader"), PISTON_HEAD_CHUNK_LOADER_NAME, "w PistonHeadLoader", "dynamic", worldOptions, true));
    }

    private static void registerLogger(Logger logger) {
        LoggerRegistry.registerLogger(logger.getLogName(), logger);
    }

    private static Field getField(String fieldName) {
        try {
            return CarpetGCAdditionLoggerRegistry.class.getField(fieldName);
        } catch (Exception ex) {
            return null;
        }
    }

}
