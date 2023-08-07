package carpetgcaddition.mixin.rule.playerMessageProperOrder;

import carpetgcaddition.CarpetGCAdditionSettings;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.time.Instant;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Inject(method = "isInProperOrder", at = @At("HEAD"), cancellable = true)
    public void isInProperOrder(Instant timestamp, CallbackInfoReturnable<Boolean> cir) {
        if (CarpetGCAdditionSettings.playerMessageProperOrderDisabled) {
            cir.setReturnValue(true);
        }
    }
}
