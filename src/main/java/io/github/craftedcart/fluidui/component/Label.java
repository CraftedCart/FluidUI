package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.uiaction.UIAction;
import io.github.craftedcart.fluidui.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.UnicodeFont;

import java.util.ArrayList;
import java.util.List;

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
                String[] lines = text.split("\\r?\\n");
                List<String> wrappedLines = new ArrayList<>();

                int wrapLines = 1; //The number of lines it takes to display the text

                for (String line : lines) {
                    int subStrOffset = 0;
                    int i = 0;
                    while (i < line.length()) {
                        int length = font.getWidth(line.substring(subStrOffset, i));

                        if (length > width) {
                            if (i > 0) {
                                line = new StringBuilder(line).insert(i - 1, '\n').toString();
                                wrappedLines.add(line.substring(subStrOffset, i - 1));
                                subStrOffset = i;

                                i++;
                            } else {
                                break;
                            }
                        }

                        i++;
                    }

                    wrappedLines.add(line.substring(subStrOffset));
                }

                wrapLines = wrappedLines.size();
                if (this.wrapLines != wrapLines) {
                    this.wrapLines = wrapLines;
                    if (onWrappingChangedAction != null) {
                        onWrappingChangedAction.execute();
                    }
                }

                //Join all lines together
                displayText = "";
                int i = 0;
                for (String line : wrappedLines) {
                    displayText = displayText + line + '\n';
                    i++;
                }
                if (i > 0) {
                    displayText = displayText.substring(0, displayText.length() - 1);
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
