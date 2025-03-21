package org.hiedacamellia.camellialib;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;


@Mod(value = Camellialib.MODID,dist = Dist.CLIENT)
public class CamellialibClient {


    public CamellialibClient(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

    }

}
