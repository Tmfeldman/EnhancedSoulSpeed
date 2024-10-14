package net.steakboi.enhancedsoulspeed;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;


// The value here should match an entry in the META-INF/mods.toml file
@Mod(EnhancedSoulSpeed.MOD_ID)
public class EnhancedSoulSpeed
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "enhancedsoulspeed";
    public static final ResourceLocation SOUL_SPEED_SPRINTING_MODIFIER_ID = ResourceLocation.withDefaultNamespace("soul_speed_sprinting");

    public EnhancedSoulSpeed()
    {
    }
}
