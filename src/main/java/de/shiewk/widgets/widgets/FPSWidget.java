package de.shiewk.widgets.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class FPSWidget extends BasicTextWidget {
    public FPSWidget(Identifier id) {
        super(id, List.of());
    }

    @Override
    public void tickWidget() {
        this.renderText = Text.literal(MinecraftClient.getInstance().getCurrentFps() + " FPS");
    }

    @Override
    public Text getName() {
        return Text.translatable("widgets.widgets.fps");
    }

    @Override
    public Text getDescription() {
        return Text.translatable("widgets.widgets.fps.description");
    }
}
