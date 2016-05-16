package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.ThemeManager;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.uiaction.UIAction;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

import java.util.regex.Pattern;

/**
 * @author CraftedCart
 * Created on 11/05/2016 (DD/MM/YYYY)
 */
public class TextField extends Label {

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor valueColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor placeholderColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor cursorColor;
    private boolean cursorFadingOut = true;

    @NotNull public String value = "";
    @Nullable public String placeholder;
    @Nullable public String inputRegexCheck;

    public boolean isSelected = false;
    public int cursorPos = 0;

    @Nullable public UIAction onTabAction;
    @Nullable public UIAction onReturnAction;

    public TextField() {
        backgroundColor = ThemeManager.currentTheme.textFieldBackgroundColor;
        valueColor = ThemeManager.currentTheme.textFieldValueColor;
        placeholderColor = ThemeManager.currentTheme.textFieldPlaceholderColor;
        cursorColor = ThemeManager.currentTheme.textFieldCursorColor;
    }

    @Override
    public void componentDraw() {
        UIUtils.drawQuad(topLeftPx, bottomRightPx, backgroundColor);

        //<editor-fold desc="Set the text to the value or the placeholder">
        if (!value.isEmpty()) {
            text = value;
            textColor = valueColor;
        } else if (placeholder != null) {
            text = placeholder;
            textColor = placeholderColor;
        } else {
            text = null;
        }
        //</editor-fold>

        //<editor-fold desc="Draw the text">
        if (font != null && !text.isEmpty()) {
            switch (horizontalAlign) { //Get the x position of the text
                case left:
                    textPos.x = 4;
                    break;
                case centre:
                    textPos.x = width / 2 - font.getWidth(text) / 2;
                    break;
                case right:
                    textPos.x = width - font.getWidth(text) / 2 - 4;
                    break;
            }

            switch (verticalAlign) { //Get the y position of the text
                case top:
                    textPos.y = 0;
                    break;
                case centre:
                    textPos.y = height / 2 - font.getLineHeight() / 2;
                    break;
                case bottom:
                    textPos.y = height - font.getLineHeight();
            }

            UIUtils.drawString(font, textPos.add(topLeftPx), text, textColor);
        }
        //</editor-fold>

        if (cursorFadingOut) {
            cursorColor.a = Math.max(cursorColor.a - UIUtils.getDelta(), 0.2);
            if (cursorColor.a <= 0.2) {
                cursorFadingOut = false;
            }
        } else {
            cursorColor.a = Math.min(cursorColor.a + UIUtils.getDelta(), 1);
            if (cursorColor.a >= 1) {
                cursorFadingOut = true;
            }
        }

        if (isSelected && font != null && text != null) {
            double cursorX = font.getWidth(text.substring(0, cursorPos));

            UIUtils.drawQuad(
                    new PosXY(cursorX + topLeftPx.x + 4, topLeftPx.y + 2),
                    new PosXY(cursorX + topLeftPx.x + 6, bottomRightPx.y - 2),
                    cursorColor);
        }
    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setValue(@NotNull String value) {
        this.value = value;
    }

    public void setPlaceholder(@Nullable String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void onClick(int button, PosXY mousePos) {
        super.onClick(button, mousePos);

        if (button == 0) { //If LMB
            setSelected(true);
            Keyboard.enableRepeatEvents(true); //Enable key repeats
        }
    }

    @Override
    public void onClickAnywhere(int button, PosXY mousePos) {
        super.onClickAnywhere(button, mousePos);
        setSelected(false);
        Keyboard.enableRepeatEvents(false); //Disable key repeats
    }

    @Override
    public void onKey(int key, char keyChar) {
        super.onKey(key, keyChar);

        if (isSelected) {
            if (key == Keyboard.KEY_BACK) { //Backspace pressed
                if (cursorPos > 0) { //If the cursor is not at the beginning
                    if (cursorPos == value.length()) {
                        value = value.substring(0, value.length() - 1);
                    } else {
                        value = new StringBuilder(value).deleteCharAt(cursorPos - 1).toString();
                    }
                    cursorPos--;
                }
            } else if (key == Keyboard.KEY_TAB) {
                if (onTabAction != null) { //If an onTabAction is set
                    onTabAction.execute(); //Execute it
                }
            } else if (key == Keyboard.KEY_RETURN) {
                if (onReturnAction != null) { //If an onReturnAction is set
                    onReturnAction.execute(); //Execute ot
                }
            } else if (Pattern.matches("[A-Za-z0-9\\s_\\+\\-\\.,!@#\\$%\\^&\\*\\(\\);\\\\/\\|<>\"'\\[\\]\\?=:]", String.valueOf(keyChar))) {
                //A normal character was entered
                if (inputRegexCheck == null || Pattern.matches(inputRegexCheck, String.valueOf(keyChar))) {
                    value = new StringBuilder(value).insert(cursorPos, keyChar).toString();
                    cursorPos++;
                }
            }
        }
    }

    public void setOnTabAction(@Nullable UIAction onTabAction) {
        this.onTabAction = onTabAction;
    }

    public void setOnReturnAction(@Nullable UIAction onReturnAction) {
        this.onReturnAction = onReturnAction;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setInputRegexCheck(@Nullable String inputRegexCheck) {
        this.inputRegexCheck = inputRegexCheck;
    }
}
