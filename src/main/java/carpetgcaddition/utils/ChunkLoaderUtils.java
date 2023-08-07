package carpetgcaddition.utils;

import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;

import java.util.Comparator;

public abstract class ChunkLoaderUtils {
    private static final String NOTE_BLOCK_TICKET_TYPE_NAME = "note_block";
    private static final String PISTON_HEAD_TICKET_TYPE_NAME = "piston_head";

    private static final ChunkTicketType<ChunkPos> PISTON_HEAD_TICKET;
    private static ChunkTicketType<ChunkPos> noteBlockTicket;

    public static final Identifier OBSIDIAN_ID = Identifier.of("minecraft", "obsidian");

    static {
        PISTON_HEAD_TICKET = ChunkTicketType.create(PISTON_HEAD_TICKET_TYPE_NAME, Comparator.comparingLong(ChunkPos::toLong), 6);
        setNoteBlockTicketTime(300);
    }

    public static boolean isNoteBlockLoadExpiryTimeInRange(int expiryTicks) {
        return  expiryTicks > 0 && expiryTicks <= 20 * 60 * 60;
    }

    public static void setNoteBlockTicketTime(int expiryTicks) {
        if (isNoteBlockLoadExpiryTimeInRange(expiryTicks)) {
            noteBlockTicket = ChunkTicketType.create(NOTE_BLOCK_TICKET_TYPE_NAME, Comparator.comparingLong(ChunkPos::toLong), expiryTicks);
        }
    }

    public static LoadChunkResult noteBlockChunkTick(ServerWorld world, BlockPos pos, int radius) {
        ChunkPos cp = new ChunkPos(pos);
        long expiryTick = noteBlockTicket.getExpiryTicks();
        world.getChunkManager().addTicket(noteBlockTicket, cp, radius, cp);
        return new LoadChunkResult(pos, expiryTick, radius);
    }

    public static LoadChunkResult pistonHeadChunkTick(ServerWorld world, BlockPos pos) {
        ChunkPos cp = new ChunkPos(pos);
        long expiryTick = PISTON_HEAD_TICKET.getExpiryTicks();
        world.getChunkManager().addTicket(PISTON_HEAD_TICKET, cp, 1, cp);
        return new LoadChunkResult(pos, expiryTick, 1);
    }

    public static class LoadChunkResult
    {
        private BlockPos blockPos;
        private long expiryTicks;
        private int radius;

        public LoadChunkResult(BlockPos pos, long expiryTicks, int radius) {
            this.blockPos = pos;
            this.expiryTicks = expiryTicks;
            this.radius = radius;
        }

        public BlockPos getBlockPos() {
            return blockPos;
        }

        public ChunkPos getChunkPos() {
            return new ChunkPos(blockPos);
        }

        public long getExpiryTicks() {
            return expiryTicks;
        }

        public int getRadius() {
            return radius;
        }
    }
}
