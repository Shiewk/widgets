package de.shiewk.widgets.widgets;

import de.shiewk.widgets.ModWidget;
import de.shiewk.widgets.WidgetSettingOption;
import de.shiewk.widgets.WidgetSettings;
import de.shiewk.widgets.widgets.settings.RGBAColorWidgetSetting;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.util.List;

public abstract class BasicTextWidget extends ModWidget {
    protected Text renderText = Text.empty();
    private int textWidthH;
    private TextRenderer renderer = null;

    private static ObjectArrayList<WidgetSettingOption> getCustomSettings(List<WidgetSettingOption> otherCustomOptions) {
        final ObjectArrayList<WidgetSettingOption> list = new ObjectArrayList<>(otherCustomOptions);
        list.add(new RGBAColorWidgetSetting("backgroundcolor", Text.translatable("widgets.widgets.basictext.background"), 0, 0, 0, 80));
        list.add(new RGBAColorWidgetSetting("textcolor", Text.translatable("widgets.widgets.basictext.textcolor"), 255, 255, 255, 255));
        return list;
    }
    protected BasicTextWidget(Identifier id, List<WidgetSettingOption> otherCustomOptions) {
        super(id, getCustomSettings(otherCustomOptions));
    }

    protected static final int
            DEFAULT_WIDTH = 80,
            DEFAULT_HEIGHT = 9 + 12,
            DEFAULT_BACKGROUND_COLOR = new Color(0, 0, 0, 80).getRGB(),
            DEFAULT_TEXT_COLOR = new Color(255, 255 ,255, 255).getRGB();

    protected int backgroundColor = DEFAULT_BACKGROUND_COLOR, textColor = DEFAULT_TEXT_COLOR;

    @Override
    public int width() {
        return DEFAULT_WIDTH;
    }

    @Override
    public int height() {
        return DEFAULT_HEIGHT;
    }

    @Override
    public void render(DrawContext context, long n, TextRenderer textRenderer, int posX, int posY) {
        renderer = textRenderer;
        context.fill(posX, posY, posX + width(), posY + height(), this.backgroundColor);
        context.drawText(textRenderer, renderText, posX + (width() / 2) - textWidthH, posY + 6, this.textColor, true);
    }

    @Override
    public final void tick() {
        tickWidget();
        if (renderer != null){
            this.textWidthH = renderer.getWidth(renderText) / 2;
        }
    }

    public abstract void tickWidget();

    @Override
    public void onSettingsChanged(WidgetSettings settings) {
        this.backgroundColor = ((RGBAColorWidgetSetting) settings.optionById("backgroundcolor")).getColor();
        this.textColor = ((RGBAColorWidgetSetting) settings.optionById("textcolor")).getColor();
    }
}
