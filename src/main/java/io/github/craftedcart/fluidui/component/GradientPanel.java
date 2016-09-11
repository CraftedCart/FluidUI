package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.util.EnumXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author CraftedCart
 * Created on 26/07/2016 (DD/MM/YYYY)
 */
public class GradientPanel extends Component {

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundColorFrom;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundColorTo;
    @NotNull public EnumXY gradientDirection = EnumXY.Y;

    public GradientPanel() {
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

        backgroundColorFrom = theme.gradientPanelBackgroundColorFrom;
        backgroundColorTo = theme.gradientPanelBackgroundColorTo;
    }

    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {
        if (gradientDirection == EnumXY.X) {
            UIUtils.drawQuadGradientHorizontal(topLeftPx, bottomRightPx, backgroundColorFrom, backgroundColorTo);
        } else {
            UIUtils.drawQuadGradientVertical(topLeftPx, bottomRightPx, backgroundColorFrom, backgroundColorTo);
        }
    }

    public void setBackgroundColor(@NotNull UIColor backgroundColorFrom, @NotNull UIColor backgroundColorTo) {
        this.backgroundColorFrom = backgroundColorFrom;
        this.backgroundColorTo = backgroundColorTo;
    }

    public void setBackgroundColorFrom(@NotNull UIColor backgroundColorFrom) {
        this.backgroundColorFrom = backgroundColorFrom;
    }

    public void setBackgroundColorTo(@NotNull UIColor backgroundColorTo) {
        this.backgroundColorTo = backgroundColorTo;
    }

}
