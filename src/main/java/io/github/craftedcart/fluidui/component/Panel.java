package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        if (parentComponent != null) {
            setTheme(parentComponent.theme);
        }
    }

    @Override
    public void setParentComponent(@Nullable Component parentComponent) {
        super.setParentComponent(parentComponent);

        if (parentComponent != null) {
            setTheme(parentComponent.theme);
        }
    }

    @Override
    public void setTheme(@NotNull UITheme theme) {
        super.setTheme(theme);

        backgroundColor = theme.panelBackgroundColor;
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
