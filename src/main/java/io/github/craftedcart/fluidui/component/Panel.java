package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.ThemeManager;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;

/**
 * @author CraftedCart
 * Created on 03/03/2016 (DD/MM/YYYY)
 */
public class Panel extends Component {

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundColor;

    public Panel() {
        init();
        postInit();
    }

    public void init() {
        backgroundColor = ThemeManager.currentTheme.panelBackgroundColor;
    }

    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {
        UIUtils.drawQuad(topLeftPx, bottomRightPx, backgroundColor);
    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

}
