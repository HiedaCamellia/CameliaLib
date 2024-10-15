package org.hiedacamellia.camellialib;


import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.hiedacamellia.camellialib.core.config.CommonConfig;

@Mod(Camellialib.MODID)
public class Camellialib {

    public static final String MODID = "camellialib";

    public Camellialib() {

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

    }

}
