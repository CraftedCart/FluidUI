package io.github.craftedcart.fluidui.theme;

import io.github.craftedcart.fluidui.util.EnumHAlignment;
import io.github.craftedcart.fluidui.util.EnumVAlignment;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.UnicodeFont;

/**
 * @author CraftedCart
 * Created on 05/03/2016 (DD/MM/YYYY)
 */
public class UITheme {

    //Panel
    @NotNull public UIColor panelBackgroundColor = UIColor.matGrey900();

    //Gradient Panel
    @NotNull public UIColor gradientPanelBackgroundColorFrom = UIColor.matGrey900();
    @NotNull public UIColor gradientPanelBackgroundColorTo = UIColor.matGrey900(0);

    //Button
    @NotNull public UIColor buttonBackgroundIdleColor = UIColor.matBlue();
    @NotNull public UIColor buttonBackgroundActiveColor = UIColor.matBlueGrey();
    @NotNull public UIColor buttonBackgroundHitColor = UIColor.matBlueGrey700();
    @NotNull public UIColor buttonBackgroundDisabledColor = UIColor.matGrey();

    //TextButton
    @NotNull public UIColor buttonTextColor = UIColor.matWhite();
    @NotNull public EnumHAlignment buttonTextHAlign = EnumHAlignment.centre;
    @NotNull public EnumVAlignment buttonTextVAlign = EnumVAlignment.centre;

    //CheckBox
    @NotNull public UIColor checkBoxUncheckedColor = UIColor.matGrey900();
    @NotNull public UIColor checkBoxCheckedColor = UIColor.matBlue();

    //Label
    @Nullable public UnicodeFont labelFont;
    @NotNull public UIColor labelTextColor = UIColor.matGrey900();

    //ListBox
    @NotNull public UIColor listBoxBackgroundColor = UIColor.matWhite();

    //TextField
    @NotNull public UIColor textFieldBackgroundColor = UIColor.matGrey900();
    @NotNull public UIColor textFieldBackgroundDisabledColor = UIColor.matGrey();
    @NotNull public UIColor textFieldValueColor = UIColor.matWhite();
    @NotNull public UIColor textFieldPlaceholderColor = UIColor.matWhite(0.5);
    @NotNull public UIColor textFieldCursorColor = UIColor.matBlue();

    //Slider
    @NotNull public UIColor sliderBackgroundColor = UIColor.matGrey900();
    @NotNull public UIColor sliderHandleColor = UIColor.matBlue();
    public double sliderThickness = 4;
    public double handleThickness = 4;
    public double sliderHandleHeight = 24;

    //Progress Bar
    @NotNull public UIColor progressBarBackgroundColor = UIColor.matGrey900();
    @NotNull public UIColor progressBarForegroundColor = UIColor.matBlue();

    //Tooltips
    @Nullable public UnicodeFont tooltipFont;
    @NotNull public UIColor tooltipColor = UIColor.matGrey900(0.5);
    @NotNull public UIColor tooltipTextColor = UIColor.matWhite();
    @NotNull public PosXY tooltipMouseOffset = new PosXY(24, 24);

    //General
    public double scrollSmoothing = 24; //Higher values = quicker scrolling
    public double mouseSensitivity = 0.35; //Higher values = greater sensitivity
    @NotNull public UIColor scrollbarBG = UIColor.matBlueGrey700();
    @NotNull public UIColor scrollbarFG = UIColor.matBlueGrey300();
    public double scrollbarThickness = 12;

}
