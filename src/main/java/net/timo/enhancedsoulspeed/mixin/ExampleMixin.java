package net.timo.enhancedsoulspeed.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class ExampleMixin extends Entity {

	@Shadow protected abstract boolean isOnSoulSpeedBlock();
	@Shadow public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);
	@Shadow public abstract Random getRandom();
	@Shadow public abstract ItemStack getEquippedStack(EquipmentSlot var1);

	private static final UUID SOUL_SPEED_SPRINT_BOOST_ID = UUID.fromString("87f46a96-686f-4796-b035-22e16ee9e048");

	protected ExampleMixin(EntityType<? extends LivingEntity> entityType, World world) {
		super(entityType, world);
	}

	@Inject(method = "addSoulSpeedBoostIfNeeded()V", at = @At("RETURN"), cancellable = true)
	private void injected(CallbackInfo ci) {
		int i;
		if (!this.getLandingBlockState().isAir() && (i = EnchantmentHelper.getEquipmentLevel(Enchantments.SOUL_SPEED, (LivingEntity)((Object)this) )) > 0 && this.isOnSoulSpeedBlock()) {
			EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			if (entityAttributeInstance == null) {
				return;
			}
			if (this.isSprinting()) {
				entityAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(SOUL_SPEED_SPRINT_BOOST_ID, "Soul speed boost", (double)(i*2), EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
			} else {
				entityAttributeInstance.addTemporaryModifier(new EntityAttributeModifier(SOUL_SPEED_SPRINT_BOOST_ID, "Soul speed boost", (double)(0.5), EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
			}
		}
	}

	@Inject(method = "removeSoulSpeedBoost()V", at = @At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILHARD)
	private void injected2(CallbackInfo ci, EntityAttributeInstance entityAttributeInstance) {
		if (entityAttributeInstance.getModifier(SOUL_SPEED_SPRINT_BOOST_ID) != null) {
			entityAttributeInstance.removeModifier(SOUL_SPEED_SPRINT_BOOST_ID);
		}
	}
}