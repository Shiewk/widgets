package de.shiewk.widgets.widgets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class PlayerCountWidget extends BasicTextWidget{
    public PlayerCountWidget(Identifier id) {
        super(id, List.of());
    }

    @Override
    public void tick() {
        final ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        String online = networkHandler == null ? "?" : String.valueOf(networkHandler.getPlayerUuids().size());
        this.renderText = Text.literal(Text.translatable("widgets.widgets.playerCount.online", online).getString());
    }

    @Override
    public Text getName() {
        return Text.translatable("widgets.widgets.playerCount");
    }

    @Override
    public Text getDescription() {
        return Text.translatable("widgets.widgets.playerCount.description");
    }
}
