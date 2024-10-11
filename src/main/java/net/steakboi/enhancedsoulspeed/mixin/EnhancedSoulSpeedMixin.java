package net.steakboi.enhancedsoulspeed.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class EnhancedSoulSpeedMixin {
    @Inject(method = "getJumpPower(F)F", at = @At("RETURN"), cancellable = true)
    protected void MultiplyJumpPower(CallbackInfoReturnable<Float> power) {
        if ((LivingEntity)(Object)this instanceof Player){
            power.setReturnValue(power.getReturnValueF()*3);
        }
    }
}