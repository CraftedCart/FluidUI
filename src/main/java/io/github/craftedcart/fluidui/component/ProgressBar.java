package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author CraftedCart
 * Created on 18/05/2016 (DD/MM/YYYY)
 */
public class ProgressBar extends Component {

    public double value = 0;

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor foregroundColor;

    public ProgressBar() {
        init();
        postInit();
    }

    public void init() {
        if (parentComponent != null) {
            setTheme(parentComponent.theme);
        }
    }

    @Override
    public void setTheme(@NotNull UITheme theme) {
        super.setTheme(theme);

        backgroundColor = theme.progressBarBackgroundColor;
        foregroundColor = theme.progressBarForegroundColor;
    }

    @Override
    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {
        UIUtils.drawQuad(topLeftPx, bottomRightPx, backgroundColor); //Draw the background
        UIUtils.drawQuad( //Draw the bar
                topLeftPx,
                new PosXY(topLeftPx.x + (width * value), bottomRightPx.y),
                foregroundColor
        );

    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setForegroundColor(@NotNull UIColor foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
