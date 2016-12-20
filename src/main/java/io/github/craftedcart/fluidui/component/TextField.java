package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.uiaction.UIAction;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author CraftedCart
 * Created on 11/05/2016 (DD/MM/YYYY)
 */
public class TextField extends Label {

    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor backgroundDisabledColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor valueColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor placeholderColor;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor cursorColor;

    @NotNull public String value = "";
    @NotNull public String prevValue = "";
    @Nullable public String placeholder;
    @Nullable public String inputRegexCheck;

    public boolean isSelected = false;
    public int cursorPos = 0;

    @Nullable public UIAction onTabAction;
    @Nullable public UIAction onReturnAction;
    @Nullable public UIAction onValueChangedAction;
    @Nullable public UIAction onValueConfirmedAction;
    @Nullable public UIAction onSelectedAction;
    @Nullable public UIAction onNextFrameAction;

    public boolean isEnabled = true;

    public TextField() {
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

        backgroundColor = theme.textFieldBackgroundColor;
        backgroundDisabledColor = theme.textFieldBackgroundDisabledColor;
        valueColor = theme.textFieldValueColor;
        placeholderColor = theme.textFieldPlaceholderColor;
        cursorColor = theme.textFieldCursorColor;
    }

    @Override
    public void preDraw() {
        if (onNextFrameAction != null) { //If an onReturnAction is set
            onNextFrameAction.execute(); //Execute it
            onNextFrameAction = null;
        }

        super.preDraw();
    }

    @Override
    public void componentDraw() {
        if (isEnabled) {
            UIUtils.drawQuad(topLeftPx, bottomRightPx, backgroundColor);
        } else {
            UIUtils.drawQuad(topLeftPx, bottomRightPx, backgroundDisabledColor);
        }

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
        if (font != null && text != null && !text.isEmpty()) {
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

        cursorColor.a = UIUtils.getCursorAlpha();

        //<editor-fold desc="Draw the cursor">
        if (isSelected && font != null && text != null) {
            if (cursorPos > text.length()) {
                cursorPos = text.length();
            } else if (cursorPos < 0) {
                cursorPos = 0;
            }

            double cursorX = font.getWidth(text.substring(0, cursorPos));

            UIUtils.drawQuad(
                    new PosXY(cursorX + topLeftPx.x + 4, topLeftPx.y + 2),
                    new PosXY(cursorX + topLeftPx.x + 6, bottomRightPx.y - 2),
                    cursorColor);
        } else if (text == null) {
            double cursorX = 0;
            UIUtils.drawQuad(
                    new PosXY(cursorX + topLeftPx.x + 4, topLeftPx.y + 2),
                    new PosXY(cursorX + topLeftPx.x + 6, bottomRightPx.y - 2),
                    cursorColor);
        }
        //</editor-fold>
    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setValue(@NotNull String value) {
        this.value = value;
        if (cursorPos > value.length()) {
            cursorPos = value.length();
        }
    }

    public void setPlaceholder(@Nullable String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void onClick(int button, PosXY mousePos) {
        super.onClick(button, mousePos);

        if (enableClicking) {
            if (button == 0) { //If LMB
                cursorPos = value.length();
                setSelected(true);
            }
        }
    }

    @Override
    public void onClickAnywhere(int button, PosXY mousePos) {
        super.onClickAnywhere(button, mousePos);
        setSelected(false);
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

                    if (onValueChangedAction != null) {
                        onValueChangedAction.execute();
                    }
                }
            } else if (key == Keyboard.KEY_DELETE) {
                if (cursorPos < value.length()) { //If the cursor is not at the end
                    value = new StringBuilder(value).deleteCharAt(cursorPos).toString();

                    if (onValueChangedAction != null) {
                        onValueChangedAction.execute();
                    }
                }
            } else if (key == Keyboard.KEY_TAB) {
                onNextFrameAction = onTabAction;
            } else if (key == Keyboard.KEY_RETURN) {
                onNextFrameAction = onReturnAction;
            } else if (key == Keyboard.KEY_LEFT) {
                if (cursorPos > 0) cursorPos--;
            } else if (key == Keyboard.KEY_RIGHT) {
                if (cursorPos < value.length()) cursorPos++;
            } else if (Pattern.matches("[A-Za-z0-9\\s_\\+\\-\\.,!@#\\$%\\^&\\*\\(\\);\\\\/\\|<>\"'\\[\\]\\?=:]", String.valueOf(keyChar))) {
                //A normal character was entered
                if (inputRegexCheck == null || Pattern.matches(inputRegexCheck, String.valueOf(keyChar))) {
                    value = new StringBuilder(value).insert(cursorPos, keyChar).toString();
                    cursorPos++;

                    if (onValueChangedAction != null) {
                        onValueChangedAction.execute();
                    }
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

    public void setOnValueChangedAction(@Nullable UIAction onValueChangedAction) {
        this.onValueChangedAction = onValueChangedAction;
    }

    public void setOnValueConfirmedAction(@Nullable UIAction onValueConfirmedAction) {
        this.onValueConfirmedAction = onValueConfirmedAction;
    }

    public void setOnSelectedAction(@Nullable UIAction onSelectedAction) {
        this.onSelectedAction = onSelectedAction;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;

        if (!enabled) {
            isSelected = false;
        }
    }

    public void setSelected(boolean selected) {
        if (isSelected && !selected && onValueConfirmedAction != null && !Objects.equals(prevValue, value) && isEnabled) {
            onValueConfirmedAction.execute();
        } else if (selected && isEnabled) {
            prevValue = value;
        }

        if (isEnabled && !isSelected && selected && onSelectedAction != null) {
            onSelectedAction.execute();
        }

        isSelected = isEnabled && selected;

//        if (isSelected) {
//            Keyboard.enableRepeatEvents(true); //Enable key repeats
//        } else {
//            Keyboard.enableRepeatEvents(false); //Disable key repeats
//        }
    }

    public void setInputRegexCheck(@Nullable String inputRegexCheck) {
        this.inputRegexCheck = inputRegexCheck;
    }
}
