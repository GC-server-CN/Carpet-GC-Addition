// Raw license MTI
// Can use the code under MIT license

package carpetgcaddition.mixin.rule.chunkLoader;

import carpet.logging.LoggerRegistry;
import carpetgcaddition.CarpetGCAdditionSettings;
import carpetgcaddition.logging.loggers.CarpetGCAdditionLoggerRegistry;
import carpetgcaddition.logging.loggers.ChunkLoadLogger;
import carpetgcaddition.utils.ChunkLoaderUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NoteBlock.class)
public abstract class NoteBlockMixin {
    @Inject(method = "playNote", at = @At("HEAD"))
    private void loadChunk(@Nullable Entity entity, BlockState state, World world, BlockPos pos, CallbackInfo info)
    {
        if (!CarpetGCAdditionSettings.noteBlockChunkLoader || world.isClient || !checkNote(state)) {
            return;
        }

        var realRadius = CarpetGCAdditionSettings.noteBlockChunkLoaderRadius + 1;
        var res = ChunkLoaderUtils.noteBlockChunkTick((ServerWorld)world, pos, realRadius);

        var logger = (ChunkLoadLogger)LoggerRegistry.getLogger(CarpetGCAdditionLoggerRegistry.NOTE_BLOCK_CHUNK_LOADER_NAME);
        logger.log(res, world);
    }

    private boolean checkNote(BlockState state) {
        int target = CarpetGCAdditionSettings.noteBlockChunkLoaderNote;
        return target == -1 || target == state.get(NoteBlock.NOTE);
    }
}
