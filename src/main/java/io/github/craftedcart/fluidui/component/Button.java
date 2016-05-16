package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.plugin.AbstractComponentPlugin;
import io.github.craftedcart.fluidui.theme.ThemeManager;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.input.Mouse;

import java.util.Map;

/**
 * @author CraftedCart
 * Created on 05/05/2016 (DD/MM/YYYY)
 */
public class Button extends Component {

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundIdleColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundActiveColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundHitColor;

    public Button() {
        init();
        postInit();
    }

    public void init() {
        backgroundIdleColor = ThemeManager.currentTheme.buttonBackgroundIdleColor;
        backgroundActiveColor = ThemeManager.currentTheme.buttonBackgroundActiveColor;
        backgroundHitColor = ThemeManager.currentTheme.buttonBackgroundHitColor;
    }

    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {
        UIColor buttonBackgroundColor; //The background color of the button
        if (mouseOver) { //If the mouse is over the button
            if (Mouse.isButtonDown(0)) { //If LMB down
                buttonBackgroundColor = backgroundHitColor;
            } else { //LMB not down
                buttonBackgroundColor = backgroundActiveColor;
            }
        } else { //Mouse not over
            buttonBackgroundColor = backgroundIdleColor;
        }

        UIUtils.drawQuad(topLeftPx, bottomRightPx, buttonBackgroundColor);
    }

    public void setBackgroundIdleColor(@NotNull UIColor backgroundIdleColor) {
        this.backgroundIdleColor = backgroundIdleColor;
    }

    public void setBackgroundActiveColor(@NotNull UIColor backgroundActiveColor) {
        this.backgroundActiveColor = backgroundActiveColor;
    }

    public void setBackgroundHitColor(@NotNull UIColor backgroundHitColor) {
        this.backgroundHitColor = backgroundHitColor;
    }

    @Override
    public void onClick(int button, PosXY mousePos) {
        onClickAnywhere(button, mousePos);

        for (Map.Entry<String, Component> entry : childComponents.entrySet()) {
            Component childComponent = entry.getValue();

            if (childComponent.mouseOver) {
                childComponent.onClick(button, mousePos);
            }
        }

        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onClick(button, mousePos);
        }

        if (onLMBAction != null) {
            onLMBAction.execute();
        }
    }

}
