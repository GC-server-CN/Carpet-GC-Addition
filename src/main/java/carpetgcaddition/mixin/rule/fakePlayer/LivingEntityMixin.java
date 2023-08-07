package carpetgcaddition.mixin.rule.fakePlayer;

import carpetgcaddition.CarpetGCAdditionMod;
import carpetgcaddition.CarpetGCAdditionSettings;
import carpetgcaddition.fakeplayeraddition.FakePlayerAdditionProperties;
import carpetgcaddition.fakeplayeraddition.FakePlayerCollisionStatus;
import carpetgcaddition.fakeplayeraddition.FakePlayerPropertiesManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Inject(method = "pushAway", at = @At("HEAD"), cancellable = true)
    private void pushAway(Entity entity, CallbackInfo ci) {
        Entity self = (Entity)(Object)this;

        if (!self.isPlayer() || !entity.isPlayer()) {
            return;
        }

        FakePlayerPropertiesManager mgr = entity.getWorld().isClient
            ? CarpetGCAdditionMod.getClientFakePlayerPropsManager()
            : CarpetGCAdditionMod.getCarpetServer().getFakePlayerPropsManager();

        if (isDisableCollision(mgr, (PlayerEntity)self, (PlayerEntity)entity)) {
            ci.cancel();
        }
    }

    private static boolean isDisableCollision(FakePlayerPropertiesManager mgr, PlayerEntity player1, PlayerEntity player2) {
        if (player1 == player2) {
            return false;
        }

        var prop1 = mgr.getProperties(player1.getEntityName());
        var prop2 = mgr.getProperties(player2.getEntityName());

        boolean dc1 = prop1.isPresent() && isDisableCollision(prop1.get());
        boolean dc2 = prop2.isPresent() && isDisableCollision(prop2.get());

        return dc1 || dc2;
    }

    private static boolean isDisableCollision(FakePlayerAdditionProperties prop) {
        return prop.collisionWithPlayer == FakePlayerCollisionStatus.Auto
            ? CarpetGCAdditionSettings.fakePlayerCollisionWithPlayerDisabled
            : prop.collisionWithPlayer == FakePlayerCollisionStatus.Disable;
    }
}
