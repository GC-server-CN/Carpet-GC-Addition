// Raw license MTI
// Can use the code under MIT license

package carpetgcaddition.mixin.rule.chunkLoader;

import carpet.logging.LoggerRegistry;
import carpetgcaddition.CarpetGCAdditionSettings;
import carpetgcaddition.logging.loggers.CarpetGCAdditionLoggerRegistry;
import carpetgcaddition.logging.loggers.ChunkLoadLogger;
import carpetgcaddition.utils.ChunkLoaderUtils;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PistonBlock.class)
public class PistonBlockMixin {
    @Inject(method = "onSyncedBlockEvent", at = @At("HEAD"))
    private void onSyncedBlockEvent(BlockState state, World world, BlockPos pos, int type, int data, CallbackInfoReturnable<Boolean> info)
    {
        if (!CarpetGCAdditionSettings.pistonHeadChunkLoader || world.isClient) {
            return;
        }

        Block block = world.getBlockState(pos.up()).getBlock();
        Direction direction = state.get(FacingBlock.FACING);

        if (Registries.BLOCK.getId(block).equals(ChunkLoaderUtils.OBSIDIAN_ID)) {
            loadChunk((ServerWorld)world, pos, direction);
        }
    }

    private void loadChunk(ServerWorld world, BlockPos pos, Direction direction) {
        BlockPos targetPos = pos.offset(direction);
        var res = ChunkLoaderUtils.pistonHeadChunkTick(world, targetPos);

        var logger = (ChunkLoadLogger)LoggerRegistry.getLogger(CarpetGCAdditionLoggerRegistry.PISTON_HEAD_CHUNK_LOADER_NAME);
        logger.log(res, world);
    }
}
