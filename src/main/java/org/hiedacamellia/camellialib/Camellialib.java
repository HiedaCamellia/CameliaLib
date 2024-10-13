package org.hiedacamellia.camellialib;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import org.hiedacamellia.camellialib.core.config.CommonConfig;


@Mod(Camellialib.MODID)
public class Camellialib {

    public static final String MODID = "camellialib";

    public Camellialib(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC);

    }

}
