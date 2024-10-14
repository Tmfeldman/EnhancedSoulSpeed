package net.steakboi.enhancedsoulspeed;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public class EnhancedSoulSpeed implements ModInitializer {
	public static final String MOD_ID = "enhancedsoulspeed";
	public static final Identifier SOUL_SPEED_SPRINTING_MODIFIER_ID = Identifier.ofVanilla("soul_speed_sprinting");
	public static Map<Float, EntityAttributeModifier> SOUL_SPEED_SPEED_MODIFIER_SPRINTING_CACHE = new HashMap<>();
	@Override
	public void onInitialize() {

	}

	public static EntityAttributeModifier get_SOUL_SPEED_SPEED_MODIFIER_SPRINTING(Float idx) {
		if (!SOUL_SPEED_SPEED_MODIFIER_SPRINTING_CACHE.containsKey(idx)) SOUL_SPEED_SPEED_MODIFIER_SPRINTING_CACHE.put(idx, new EntityAttributeModifier(SOUL_SPEED_SPRINTING_MODIFIER_ID, idx, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
		return SOUL_SPEED_SPEED_MODIFIER_SPRINTING_CACHE.get(idx);
	}
}