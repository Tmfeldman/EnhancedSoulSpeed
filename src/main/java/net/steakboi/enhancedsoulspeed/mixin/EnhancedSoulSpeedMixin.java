package net.steakboi.enhancedsoulspeed.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Set;

import static net.steakboi.enhancedsoulspeed.EnhancedSoulSpeed.SOUL_SPEED_SPRINTING_MODIFIER_ID;

@Mixin(LivingEntity.class)
public abstract class EnhancedSoulSpeedMixin{
    @Inject(method = "onChangedBlock(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V", at = @At(value = "TAIL"))
    private void AddAndRemoveEnhancedSoulSpeedBoostIfNeeded(ServerLevel pLevel, BlockPos pPos, CallbackInfo ci) {
        try {
            LivingEntity this_cast = (LivingEntity) (Object) this;
            AttributeInstance attributeinstance = this_cast.getAttribute(Attributes.MOVEMENT_SPEED);
            attributeinstance.removeModifier(SOUL_SPEED_SPRINTING_MODIFIER_ID);
            if (this_cast.isSprinting() && this_cast instanceof Player && this_cast.level() instanceof ServerLevel serverlevel1 && this_cast.level().getBlockState(this_cast.getOnPos()).is(BlockTags.SOUL_SPEED_BLOCKS)) {
                Player Player_this = (Player) (Object) this;
                ItemStack boots = Player_this.getInventory().getArmor(0);
                Set<Object2IntMap.Entry<Holder<Enchantment>>> Enchantment_set = boots.getEnchantments().entrySet();
                for (Object2IntMap.Entry<Holder<Enchantment>> s : Enchantment_set) {
                    if (s.getKey().is(Enchantments.SOUL_SPEED)) {
                        int soul_speed_level = s.getIntValue();
                        AttributeModifier SOUL_SPEED_SPEED_MODIFIER_SPRINTING = new AttributeModifier(SOUL_SPEED_SPRINTING_MODIFIER_ID, 2.0f * soul_speed_level, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
                        attributeinstance.addTransientModifier(SOUL_SPEED_SPEED_MODIFIER_SPRINTING);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("EnhancedSoulSpeed: Failed to decide whether or not to apply enhanced soul speed boost. Continuing");
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
    }
}