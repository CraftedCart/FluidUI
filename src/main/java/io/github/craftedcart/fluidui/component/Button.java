package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.plugin.AbstractComponentPlugin;
import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.Slice9PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.opengl.Texture;

import java.util.Map;

/**
 * @author CraftedCart
 * Created on 05/05/2016 (DD/MM/YYYY)
 */
public class Button extends Component {

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundIdleColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundActiveColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundHitColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundDisabledColor;

    @Nullable public Texture texture;
    @Nullable public Slice9PosXY textureSlice9;

    public boolean isEnabled = true;

    public Button() {
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

        backgroundIdleColor = theme.buttonBackgroundIdleColor;
        backgroundActiveColor = theme.buttonBackgroundActiveColor;
        backgroundHitColor = theme.buttonBackgroundHitColor;
        backgroundDisabledColor = theme.buttonBackgroundDisabledColor;
    }

    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {
        UIColor buttonBackgroundColor; //The background color of the button
        if (mouseOver && isEnabled) { //If the mouse is over the button
            if (Mouse.isButtonDown(0)) { //If LMB down
                buttonBackgroundColor = backgroundHitColor;
            } else { //LMB not down
                buttonBackgroundColor = backgroundActiveColor;
            }
        } else { //Mouse not over
            buttonBackgroundColor = backgroundIdleColor;
        }

        if (texture == null) {
            UIUtils.drawQuad(topLeftPx, bottomRightPx, buttonBackgroundColor);
        } else {
                buttonBackgroundColor.bindColor();
            if (textureSlice9 == null) {
                UIUtils.drawTexturedQuad(topLeftPx, bottomRightPx, texture);
            } else {
                UIUtils.drawTexturedQuad(topLeftPx, bottomRightPx, texture, textureSlice9);
            }
        }
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

        if (enableClicking) {
            for (Map.Entry<String, Component> entry : childComponents.entrySet()) {
                Component childComponent = entry.getValue();

                if (childComponent.mouseOver) {
                    childComponent.onClick(button, mousePos);
                }
            }

            for (AbstractComponentPlugin plugin : plugins) {
                plugin.onClick(button, mousePos);
            }

            if (onLMBAction != null && isEnabled) {
                onLMBAction.execute();
            }
        }
    }

    public void setTexture(@Nullable Texture texture) {
        this.texture = texture;
    }

    public void setTextureSlice9(@Nullable Slice9PosXY textureSlice9) {
        this.textureSlice9 = textureSlice9;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

}
