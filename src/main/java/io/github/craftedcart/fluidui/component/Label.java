package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.uiaction.UIAction;
import io.github.craftedcart.fluidui.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.UnicodeFont;

/**
 * @author CraftedCart
 * Created on 03/03/2016 (DD/MM/YYYY)
 */
public class Label extends Component {

    @Nullable public UnicodeFont font;
    @Nullable public String text;
    @Nullable private String displayText;
    @Nullable private Double prevWidth;
    @SuppressWarnings("NullableProblems") @NotNull public UIColor textColor;
    @SuppressWarnings("NullableProblems") @NotNull public EnumHAlignment horizontalAlign;
    @SuppressWarnings("NullableProblems") @NotNull public EnumVAlignment verticalAlign;
    public boolean softWrap = false;
    public int wrapLines = 1;
    public UIAction onWrappingChangedAction;
    /**
     * Don't set this! This is updated every frame (As long as a font is set).
     */
    @NotNull public PosXY textPos = new PosXY(0, 0);

    public Label() {
        init();
        postInit();
    }

    public void init() {
        if (parentComponent != null) {
            setTheme(parentComponent.theme);
        }
        horizontalAlign = EnumHAlignment.left;
        verticalAlign = EnumVAlignment.top;
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

        font = parentComponent.theme.labelFont;
        textColor = parentComponent.theme.labelTextColor;
    }

    @Override
    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {
        if (font != null && text != null) {

            if (softWrap && (prevWidth == null || prevWidth != width)) {
                displayText = text;

                int wrapLines = 1; //The number of lines it takes to display the text
                int subStrOffset = 0;
                int i = 0;
                while (i < displayText.length()) {
                    int length = font.getWidth(displayText.substring(subStrOffset, i));

                    if (length > width) {
                        displayText = new StringBuilder(displayText).insert(i - 1, '\n').toString();
                        wrapLines++;
                        subStrOffset = i;
                    }

                    i++;
                }

                if (this.wrapLines != wrapLines) {
                    this.wrapLines = wrapLines;
                    if (onWrappingChangedAction != null) {
                        onWrappingChangedAction.execute();
                    }
                }

                prevWidth = width;

            } else if (!softWrap) {
                displayText = text;
            }

            switch (horizontalAlign) { //Get the x position of the text
                case left:
                    textPos.x = 0;
                    break;
                case centre:
                    textPos.x = width / 2 - font.getWidth(text) / 2;
                    break;
                case right:
                    textPos.x = width - font.getWidth(text) / 2;
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

            UIUtils.drawString(font, textPos.add(topLeftPx), displayText, textColor);
        }
    }

    public void setFont(@Nullable UnicodeFont font) {
        this.font = font;
    }

    public void setText(@Nullable String text) {
        this.text = text;
    }

    public void setTextColor(@NotNull UIColor textColor) {
        this.textColor = textColor;
    }

    public void setHorizontalAlign(@NotNull EnumHAlignment horizontalAlign) {
        this.horizontalAlign = horizontalAlign;
    }

    public void setVerticalAlign(@NotNull EnumVAlignment verticalAlign) {
        this.verticalAlign = verticalAlign;
    }

    public void setSoftWrap(boolean softWrap) {
        this.softWrap = softWrap;
    }

    public void setOnWrappingChangedAction(UIAction onWrappingChangedAction) {
        this.onWrappingChangedAction = onWrappingChangedAction;
    }

}
