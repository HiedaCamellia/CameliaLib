package org.hiedacamellia.camellialib.client.gui.tree;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.hiedacamellia.camellialib.client.debug.DebugEntry;
import org.hiedacamellia.immersiveui.client.graphic.gui.IUIGuiUtils;
import org.hiedacamellia.immersiveui.client.gui.component.widget.tree.TreeEntryWidget;

public class DebugTreeEntryWidget extends TreeEntryWidget<DebugEntry> {
    public DebugTreeEntryWidget(Component message, Font font) {
        super(message, font);
        this.foldWidth = 20;
        this.selfHeight = 20;
        this.selfWidth += 40;
    }

    public static DebugTreeEntryWidget create(DebugEntry data, Component component, Font font){
        DebugTreeEntryWidget widget = new DebugTreeEntryWidget(component, font);
        widget.setData(data);
        widget.width = font.width(component);
        widget.height = font.lineHeight;
        return widget;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float v) {
        if(hasChild()) {
            IUIGuiUtils.drawCenteredString(guiGraphics,font,fold?foldComponent:unfoldComponent,this.getX()+foldWidth/2,this.getY()+selfHeight/2,0xFFFFFF,false);
            IUIGuiUtils.drawCenteredString(guiGraphics,font, getMessage(), this.getX()+foldWidth+selfWidth/2, this.getY()+selfHeight/2, 0xFFFFFF,false);
        }
        else
            IUIGuiUtils.drawCenteredString(guiGraphics,font, getMessage(), this.getX()+selfWidth/2, this.getY()+selfHeight/2, 0xFFFFFF,false);

        if(fold) return;
        renderChildren(guiGraphics, mouseX, mouseY, v);
    }

    @Override
    public boolean shouldAccept(double mouseX, double mouseY){
        return false;
    }
}
