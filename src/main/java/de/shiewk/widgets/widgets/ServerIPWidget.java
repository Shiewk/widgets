package de.shiewk.widgets.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class ServerIPWidget extends BasicTextWidget {
    public ServerIPWidget(Identifier id) {
        super(id, List.of());
    }

    private int width;
    private int t = 0;

    @Override
    public void tick() {
        final ServerInfo serverEntry = MinecraftClient.getInstance().getCurrentServerEntry();
        if (serverEntry != null){
            this.renderText = Text.of(serverEntry.address);
        } else {
            this.renderText = Text.translatable("menu.singleplayer");
        }
        t++;
        if (t >= 20){
            t = 0;
            this.width = MinecraftClient.getInstance().textRenderer.getWidth(this.renderText) + 20;
        }
    }

    @Override
    public int width() {
        return Math.max(super.width(), this.width);
    }

    @Override
    public Text getName() {
        return Text.translatable("widgets.widgets.serverIP");
    }

    @Override
    public Text getDescription() {
        return Text.translatable("widgets.widgets.serverIP.description");
    }
}
