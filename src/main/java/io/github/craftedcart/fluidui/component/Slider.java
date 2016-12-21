package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.uiaction.UIAction1;
import io.github.craftedcart.fluidui.util.MathUtils;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;

/**
 * @author CraftedCart
 * Created on 18/05/2016 (DD/MM/YYYY)
 */
public class Slider extends Component {

    public double minValue = 0;
    public double maxValue = 1;
    public double value = 0;

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor handleColor;
    public double sliderThickness;
    public double handleThickness;
    public double handleHeight;

    public boolean isGrabbed = false;

    public UIAction1<Double> onValueChangedAction;

    public Slider() {
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

        backgroundColor = theme.sliderBackgroundColor;
        handleColor = theme.sliderHandleColor;
        sliderThickness = theme.sliderThickness;
        handleHeight = theme.sliderHandleHeight;
        handleThickness = theme.handleThickness;
    }

    @Override
    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {

        if (isGrabbed) { //If is grabbed
            if (Mouse.isButtonDown(0)) { //If LMB down
                assert mousePos != null;
                double percentage = (mousePos.x - topLeftPx.x - handleThickness / 2) / (bottomRightPx.x - topLeftPx.x - handleThickness);
                double newValue = (maxValue - minValue) * percentage + minValue;

                if (newValue != value) {
                    value = MathUtils.clamp(newValue, minValue, maxValue);

                    if (onValueChangedAction != null) {
                        onValueChangedAction.execute(value);
                    }
                }
            } else {
                isGrabbed = false;
            }
        }

        double middleYPx = topLeftPx.y + height / 2;

        UIUtils.drawQuad( //Draw the background
                new PosXY(topLeftPx.x, middleYPx - sliderThickness / 2),
                new PosXY(bottomRightPx.x, middleYPx + sliderThickness / 2),
                backgroundColor
        );

        double percentage = (value - minValue) / (maxValue - minValue);
        double handleXPx = topLeftPx.x + (width - handleThickness) * percentage;

        UIUtils.drawQuad( //Draw the handle
                new PosXY(handleXPx, middleYPx - handleHeight / 2),
                new PosXY(handleXPx + handleThickness, middleYPx + handleHeight / 2),
                handleColor
        );

    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setHandleColor(@NotNull UIColor handleColor) {
        this.handleColor = handleColor;
    }

    public void setSliderThickness(double sliderThickness) {
        this.sliderThickness = sliderThickness;
    }

    public void setHandleThickness(double handleThickness) {
        this.handleThickness = handleThickness;
    }

    public void setHandleHeight(double handleHeight) {
        this.handleHeight = handleHeight;
    }

    @Override
    public void onClick(int button, PosXY mousePos) {
        super.onClick(button, mousePos);

        if (enableClicking) {
            isGrabbed = true;

            double percentage = (mousePos.x - topLeftPx.x - handleThickness / 2) / (bottomRightPx.x - topLeftPx.x - handleThickness);
            double newValue = (maxValue - minValue) * percentage + minValue;

            if (newValue != value) {
                value = MathUtils.clamp(newValue, minValue, maxValue);

                if (onValueChangedAction != null) {
                    onValueChangedAction.execute(value);
                }
            }
        }
    }

    public void setOnValueChangedAction(UIAction1<Double> onValueChangedAction) {
        this.onValueChangedAction = onValueChangedAction;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setValue(double value) {
        if (value != this.value) {
            this.value = value;

            if (onValueChangedAction != null) {
                onValueChangedAction.execute(value);
            }
        }
    }

}
