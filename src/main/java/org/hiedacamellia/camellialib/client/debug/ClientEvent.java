package org.hiedacamellia.camellialib.client.debug;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.PauseScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.hiedacamellia.camellialib.core.config.CommonConfig;
import org.hiedacamellia.immersiveui.client.gui.component.w2s.World2ScreenScreen;
import org.hiedacamellia.immersiveui.client.gui.layer.World2ScreenWidgetLayer;
import org.lwjgl.glfw.GLFW;

import java.util.UUID;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT,bus = EventBusSubscriber.Bus.GAME)
public class ClientEvent {


    @SubscribeEvent
    public static void onKey(final InputEvent.Key event) {

        if(CommonConfig.DEBUG.isFalse()) return;

        if(event.getKey() == GLFW.GLFW_KEY_C){
            Minecraft.getInstance().setScreen(new DebugScreen());
        }

    }
}
