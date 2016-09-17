package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import org.jetbrains.annotations.NotNull;

/**
 * @author CraftedCart
 * Created on 04/07/2016 (DD/MM/YYYY)
 */
public class CheckBox extends Button {

    @SuppressWarnings("NullableProblems") @NotNull public UIColor uncheckedColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor checkedColor;
    public boolean value = false;

    @Override
    public void init() {
        super.init();
    }

    @Override
    public void onClick(int button, PosXY mousePos) {
        value = !value;
        updateColor();

        super.onClick(button, mousePos);
    }

    @Override
    public void setTheme(@NotNull UITheme theme) {
        super.setTheme(theme);

        uncheckedColor = theme.checkBoxUncheckedColor;
        checkedColor = theme.checkBoxCheckedColor;
        updateColor();
    }

    private void updateColor() {
        backgroundIdleColor = value ? checkedColor : uncheckedColor;
    }

    public void setValue(boolean value) {
        this.value = value;
        updateColor();
    }

    public boolean getValue() {
        return value;
    }

    public void setUncheckedColor(@NotNull UIColor uncheckedColor) {
        this.uncheckedColor = uncheckedColor;
    }

    public void setCheckedColor(@NotNull UIColor checkedColor) {
        this.checkedColor = checkedColor;
    }

}
