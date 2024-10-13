package org.hiedacamellia.camellialib.core.debug;


import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import org.hiedacamellia.camellialib.Camellialib;
import org.hiedacamellia.camellialib.core.config.CommonConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CamelliaDebug {

    private static String prefix = "[%a7a\u5c71\u8336\u82b1\u6838\u5fc3%a7r]";
    private static Boolean debugConfig = CommonConfig.DEBUG.get();
    private static Logger logger = LoggerFactory.getLogger(Camellialib.MODID);

    public static Logger getLogger(){
        return logger;
    }

    //客户端调试信息
    public static void send(String string) {
        if (FMLEnvironment.dist.isClient()) {
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null && debugConfig) {
                mc.player.sendSystemMessage(Component.literal(prefix + string));
            }
        }
    }

    //服务端调试信息
    public static void send(String string, Player player) {
        Level level = player.level();
        if(!level.isClientSide && debugConfig) {
            player.sendSystemMessage(Component.literal(prefix  + string));
        }
    }
}

