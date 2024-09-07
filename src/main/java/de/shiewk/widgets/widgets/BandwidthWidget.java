package de.shiewk.widgets.widgets;

import de.shiewk.widgets.WidgetSettings;
import de.shiewk.widgets.widgets.settings.ToggleWidgetSetting;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.MultiValueDebugSampleLogImpl;

import java.util.List;

public class BandwidthWidget extends BasicTextWidget {
    public BandwidthWidget(Identifier id) {
        super(id, List.of(
                new ToggleWidgetSetting("dynamic_color", Text.translatable("widgets.widgets.bandwidth.dynamicColor"), true)
        ));
    }

    private int t = 0;
    private boolean dynamicColor = false;

    @Override
    public void tick() {
        t++;
        if (t >= 20){
            t = 0;
            final MultiValueDebugSampleLogImpl packetSizeLog = MinecraftClient.getInstance().getDebugHud().getPacketSizeLog();
            final int logLength = packetSizeLog.getLength();
            final int avgCompileLength = 60;
            long size = 0;
            for (int i = logLength-1; i > logLength-avgCompileLength; i--) {
                if (i < 0) break;
                size += packetSizeLog.get(i);
            }
            long avgBytesPerSecond = size / avgCompileLength * 20;
            this.renderText = Text.of(formatByteSize(avgBytesPerSecond));
            if (this.dynamicColor){
                if (avgBytesPerSecond < 100000){
                    this.textColor = 0x00ff00;
                } else if (avgBytesPerSecond < 750000) {
                    this.textColor = 0xffff00;
                } else {
                    this.textColor = 0xff3030;
                }
            }
        }
    }

    private String formatByteSize(long bytes) {
        if (bytes > 1000) {
            double mb = bytes / 100 / 10d;
            return mb + " KB/s";
        } else {
            return bytes + " B/s";
        }
    }

    @Override
    public void onSettingsChanged(WidgetSettings settings) {
        super.onSettingsChanged(settings);
        this.dynamicColor = ((ToggleWidgetSetting) settings.optionById("dynamic_color")).getValue();
    }

    @Override
    public Text getName() {
        return Text.translatable("widgets.widgets.bandwidth");
    }

    @Override
    public Text getDescription() {
        return Text.translatable("widgets.widgets.bandwidth.description");
    }
}
