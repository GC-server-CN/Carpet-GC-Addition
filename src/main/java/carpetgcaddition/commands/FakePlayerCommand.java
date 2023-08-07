package carpetgcaddition.commands;

import carpet.CarpetSettings;
import carpet.utils.CommandHelper;
import carpet.utils.Messenger;
import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.fakeplayeraddition.FakePlayerCollisionStatus;
import carpetgcaddition.network.utils.FakePlayerServerNetworkUtils;
import carpetgcaddition.translation.MessageTranslationKeys;
import carpetgcaddition.translation.Translator;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.ServerCommandSource;

import java.util.Collection;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;
import static net.minecraft.command.CommandSource.suggestMatching;

public class FakePlayerCommand {
    private static final String FAKE_PLAYER_COMMAND = "fakePlayer";
    private static final String PLAYER_ARGUMENT_NAME = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal(FAKE_PLAYER_COMMAND)
                .requires(player -> CommandHelper.canUseCommand(player, CarpetSettings.commandPlayer))
                .then(argument(PLAYER_ARGUMENT_NAME, StringArgumentType.word())
                    .suggests((ctx, builder) -> suggestMatching(getFakePlayerSuggestions(), builder))
                    .then(literal("collision")
                        .then(literal("auto").executes(ctx -> setFakePlayerCollision(ctx, FakePlayerCollisionStatus.Auto)))
                        .then(literal("enable").executes(ctx -> setFakePlayerCollision(ctx, FakePlayerCollisionStatus.Enable)))
                        .then(literal("disable").executes(ctx -> setFakePlayerCollision(ctx, FakePlayerCollisionStatus.Disable)))
                        )
                    );

        dispatcher.register(command);
    }

    private static Collection<String> getFakePlayerSuggestions() {
        return CarpetGCAdditionMod.getCarpetServer().getFakePlayerPropsManager().getFakePlayerNames();
    }

    private static int setFakePlayerCollision(CommandContext<ServerCommandSource> ctx, FakePlayerCollisionStatus status) {
        var playerName = StringArgumentType.getString(ctx, PLAYER_ARGUMENT_NAME);
        var prop = CarpetGCAdditionMod.getCarpetServer().getFakePlayerPropsManager().getProperties(playerName);

        if (prop.isEmpty()) {
            var message = Translator.translation(MessageTranslationKeys.FakePlayerNotExisting);
            Messenger.m(ctx.getSource(), Messenger.s(message, "r"));
            return 0;
        }

        prop.get().collisionWithPlayer = status;
        var server = ctx.getSource().getServer();
        FakePlayerServerNetworkUtils.onFakePlayerAdditionPropUpdate(playerName, server);

        return 1;
    }
}
