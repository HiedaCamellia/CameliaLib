package org.hiedacamellia.camellialib.core.config;

import net.neoforged.neoforge.common.ModConfigSpec;

public class CommonConfig {


    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue DEBUG = BUILDER
            .comment("Set to true to enable debug info")
            .comment("设置为true以启用调试信息")
            .define("debug", true);

    public static final ModConfigSpec SPEC = BUILDER.build();
}
