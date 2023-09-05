package carpetgcaddition.mixin.rule.keepTickEntities;

import carpetgcaddition.CarpetGCAdditionSettings;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {
    @Shadow public abstract void resetIdleTimeout();

    @Inject(method = "tick", at = @At("HEAD"))
    public void tick(BooleanSupplier shouldKeepTicking, CallbackInfo ci) {
        if (CarpetGCAdditionSettings.keepTickEntities) {
            resetIdleTimeout();
        }
    }
}
