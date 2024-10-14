package net.steakboi.enhancedsoulspeed.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Set;

import static net.steakboi.enhancedsoulspeed.EnhancedSoulSpeed.*;

@Mixin(LivingEntity.class)
public abstract class EnhancedSoulSpeedMixin{

	@Inject(method = "applyMovementEffects(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/util/math/BlockPos;)V", at = @At(value = "TAIL"))
	private void AddAndRemoveEnhancedSoulSpeedBoostIfNeeded(ServerWorld pLevel, BlockPos pPos, CallbackInfo ci) {
		LivingEntity this_cast = (LivingEntity) (Object) this;
		if (this_cast.getWorld() instanceof ServerWorld && this_cast instanceof PlayerEntity) {
			try {
				EntityAttributeInstance attributeinstance = this_cast.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
				attributeinstance.removeModifier(SOUL_SPEED_SPRINTING_MODIFIER_ID);
				if (this_cast.isSprinting() && this_cast.getWorld().getBlockState(this_cast.getVelocityAffectingPos()).isIn(BlockTags.SOUL_SPEED_BLOCKS)) {
					PlayerEntity Player_this = (PlayerEntity) (Object) this;
					ItemStack boots = Player_this.getInventory().getArmorStack(0);
					Set<Object2IntMap.Entry<RegistryEntry<Enchantment>>> Enchantment_set = boots.getEnchantments().getEnchantmentEntries();
					for (Object2IntMap.Entry<RegistryEntry<Enchantment>> s : Enchantment_set) {
						if (s.getKey().matchesKey(Enchantments.SOUL_SPEED)) {
							int soul_speed_level = s.getIntValue();
							EntityAttributeModifier SOUL_SPEED_SPEED_MODIFIER_SPRINTING = get_SOUL_SPEED_SPEED_MODIFIER_SPRINTING(soul_speed_level*2.0F);
							attributeinstance.addTemporaryModifier(SOUL_SPEED_SPEED_MODIFIER_SPRINTING);
							break;
						}
					}
				}
			} catch (Exception e) {
				System.out.println("EnhancedSoulSpeed: Failed to decide whether or not to apply enhanced soul speed boost. Continuing");
				System.out.println(e.toString());
				System.out.println(Arrays.toString(e.getStackTrace()));
			}
		}
	}
}