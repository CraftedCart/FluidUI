package io.github.craftedcart.fluidui.theme;

/**
 * @author CraftedCart
 * Created on 05/03/2016 (DD/MM/YYYY)
 */
public class ThemeManager {

    public static UITheme currentTheme = new UITheme();

    public static void setCurrentTheme(UITheme currentTheme) {
        ThemeManager.currentTheme = currentTheme;
    }

}
