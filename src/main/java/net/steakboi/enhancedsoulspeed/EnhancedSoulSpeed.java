package net.steakboi.enhancedsoulspeed;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EnhancedSoulSpeed.MOD_ID)
public class EnhancedSoulSpeed
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "enhancedsoulspeed";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public EnhancedSoulSpeed()
    {
    }
}
