package org.hiedacamellia.camellialib.client.debug;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.camellialib.client.gui.tree.DebugTreeEntryWidget;
import org.hiedacamellia.camellialib.client.gui.tree.DebugTreeWidget;

import java.util.List;

public class DebugScreen extends Screen {

    private static DebugTreeWidget debugTreeWidget;

    protected DebugScreen() {
        super(Component.literal("Debug Screen"));
    }

    @Override
    protected void init() {
        List<DebugTreeEntryWidget> roots = DebugRegistries.getRoots();
        debugTreeWidget = DebugTreeWidget.create(roots, 0, 0, Component.literal("Debug"), font);
        addRenderableWidget(debugTreeWidget);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);

    }
}
