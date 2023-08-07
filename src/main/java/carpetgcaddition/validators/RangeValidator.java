package carpetgcaddition.validators;

import carpet.api.settings.CarpetRule;
import carpet.api.settings.Validator;
import carpet.utils.Messenger;
import carpetgcaddition.translation.MessageTranslationKeys;
import carpetgcaddition.translation.Translator;
import net.minecraft.server.command.ServerCommandSource;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RangeValidator<T extends Comparable<T>> extends Validator<T> {

    @NotNull
    protected T min;
    @NotNull
    protected T max;

    public RangeValidator(@NotNull T min, @NotNull T max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public T validate(@Nullable ServerCommandSource source, CarpetRule<T> changingRule, T newValue, String userInput) {
        return newValue == null || newValue.compareTo(min) < 0 || newValue.compareTo(max) > 0
            ? null
            : newValue;
    }

    @Override
    public void notifyFailure(ServerCommandSource source, CarpetRule<T> currentRule, String providedValue) {
        var s = Translator.getTranslator().getTranslation(MessageTranslationKeys.RangeValidatorFail);
        if (s.isEmpty()) {
            super.notifyFailure(source, currentRule, providedValue);
            return;
        }

        Messenger.m(source, Messenger.s(s.get().formatted(min.toString(), max.toString()), "r"));
        if (description() != null) {
            Messenger.m(source, "r " + description());
        }
    }
}
