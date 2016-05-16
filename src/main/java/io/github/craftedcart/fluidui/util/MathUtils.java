package io.github.craftedcart.fluidui.util;

/**
 * @author CraftedCart
 * Created on 05/04/2016 (DD/MM/YYYY)
 */
public class MathUtils {

    public static double lerp(double a, double b, double f) {
        return a + f * (b - a);
    }

    public static double clamp(double val, double min, double max) {
        return Math.max(min, Math.min(max, val));
    }

    /**
     * @param string The string to parse
     * @return The integer if the string was an integer, or null if it wasn't
     */
    public static Integer stringToInteger(String string) {
        try {
            //noinspection ResultOfMethodCallIgnored
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return null;
        }
    }

}
