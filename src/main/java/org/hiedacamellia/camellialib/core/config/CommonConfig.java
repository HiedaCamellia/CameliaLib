package org.hiedacamellia.camellialib.core.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class CommonConfig {


    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue DEBUG = BUILDER
            .comment("Set to true to enable debug info")
            .comment("\u8bbe\u7f6e\u4e3atrue\u4ee5\u542f\u7528\u8c03\u8bd5\u4fe1\u606f")
            .define("debug", false);

    public static final ForgeConfigSpec SPEC = BUILDER.build();
}
