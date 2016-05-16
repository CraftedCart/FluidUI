package io.github.craftedcart.fluidui;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author CraftedCart
 * Created on 03/04/2016 (DD/MM/YYYY)
 *
 * Used to cache {@link org.newdawn.slick.UnicodeFont}s for quick use later
 */
public class FontCache {

    private static Map<FontIdentifier, UnicodeFont> unicodeFontCache = new HashMap<FontIdentifier, UnicodeFont>();
    private static Map<String, Font> awtFontCache = new HashMap<String, Font>();

    /**
     * This will register a UnicodeFont with the font cache. If you don't want to create the UnicodeFont yourself, use registerUnicodeFont(some parameters...).
     * This assumes that you have added any effects that you want and have loaded glyphs already.
     *
     * @param name The name of the font to be registered
     * @param font The UnicodeFont to be registered
     */
    public static UnicodeFont registerUnicodeFont(@NotNull String name, @NotNull UnicodeFont font) {
        unicodeFontCache.put(new FontIdentifier(name, font.getFont().getSize()), font);
        return font;
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load glyphs continuously.
     * If a font has already been registered, this won't try to reload the font unless if the overwriteUnicode parameter is true.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param fontInputStream A TrueTypeFont inputStream. The stream will be closed automatically, there is no need to do so yourself.
     * @param size The size of the font
     * @param bold Whether the font should be bold
     * @param italic Whether the font should be italicised
     * @param overwriteUnicode If the {@link org.newdawn.slick.UnicodeFont} exists already, should it be overwritten?
     * @param overwriteAWT If the {@link java.awt.Font} exists already, should it be overwritten?
     * @throws IOException
     * @throws FontFormatException
     * @throws SlickException
     */
    public static UnicodeFont registerUnicodeFont(@NotNull String name, @Nullable InputStream fontInputStream, int size, boolean bold, boolean italic,
                                           boolean overwriteUnicode, boolean overwriteAWT)
            throws IOException, FontFormatException, SlickException {
        if (!isUnicodeFontRegistered(name, size) || overwriteUnicode) {

            UnicodeFont font;
            if (getAWTFont(name) == null || overwriteAWT) {
                if (fontInputStream != null) {
                    font = new UnicodeFont(registerAWTFont(name, fontInputStream, overwriteAWT).deriveFont((float) size), size, bold, italic);
                } else {
                    throw new RuntimeException(String.format("Couldn't find registered AWT font with the name \"%s\", and no fontInputStream was given", name));
                }
            } else {
                //noinspection ConstantConditions
                font = new UnicodeFont(getAWTFont(name).deriveFont((float) size), size, bold, italic);
            }

            if (fontInputStream != null) {
                fontInputStream.close();
            }

            //noinspection unchecked
            font.getEffects().add(new ColorEffect(Color.white));
            font.addAsciiGlyphs();
            font.loadGlyphs();

            return registerUnicodeFont(name, font);
        } else {
            return getUnicodeFont(name, size);
        }
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load glyphs continuously.
     * If a font has already been registered, this won't try to reload the font unless if the overwriteUnicode parameter is true.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param size The size of the font
     * @param bold Whether the font should be bold
     * @param italic Whether the font should be italicised
     * @param overwriteUnicode If the {@link org.newdawn.slick.UnicodeFont} exists already, should it be overwritten?
     * @throws IOException
     * @throws FontFormatException
     * @throws SlickException
     */
    public static UnicodeFont registerUnicodeFont(@NotNull String name, int size, boolean bold, boolean italic, boolean overwriteUnicode)
            throws IOException, FontFormatException, SlickException {
        return registerUnicodeFont(name, null, size, bold, italic, overwriteUnicode, false);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load glyphs continuously.
     * If a font has already been registered, this won't try to reload the font.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param fontInputStream A TrueTypeFont inputStream. The stream will be closed automatically, there is no need to do so yourself.
     * @param size The size of the font
     * @param bold Whether the font should be bold
     * @param italic Whether the font should be italicised
     * @throws IOException
     * @throws FontFormatException
     * @throws SlickException
     */
    public static UnicodeFont registerUnicodeFont(@NotNull String name, @Nullable InputStream fontInputStream,
                                           int size, boolean bold, boolean italic) throws IOException, FontFormatException, SlickException {
        return registerUnicodeFont(name, fontInputStream, size, bold, italic, false, false);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load glyphs continuously.
     * If a font has already been registered, this won't try to reload the font.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param fontInputStream A TrueTypeFont inputStream. The stream will be closed automatically, there is no need to do so yourself.
     * @param size The size of the font
     * @throws FontFormatException
     * @throws SlickException
     * @throws IOException
     */
    public static UnicodeFont registerUnicodeFont(@NotNull String name, @Nullable InputStream fontInputStream, int size) throws FontFormatException, SlickException, IOException {
        return registerUnicodeFont(name, fontInputStream, size, false, false);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load glyphs continuously.
     * If a font has already been registered, this won't try to reload the font.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param size The size of the font
     * @throws FontFormatException
     * @throws SlickException
     * @throws IOException
     */
    public static UnicodeFont registerUnicodeFont(@NotNull String name, int size) throws FontFormatException, SlickException, IOException {
        return registerUnicodeFont(name, null, size, false, false);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load from a file continuously.
     * If a font has already been registered, this won't try to reload the font unless if the overwrite parameter is true.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param font The font to be registered.
     * @param overwrite If the font has already been registered, should it be overwritten?
     */
    public static Font registerAWTFont(@NotNull String name, @NotNull Font font, boolean overwrite) {
        if (!isAWTFontRegistered(name) || overwrite) {
            awtFontCache.put(name, font);
            return font;
        } else {
            return getAWTFont(name);
        }
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load from a file continuously.
     * If a font has already been registered, this won't try to reload the font.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param font The font to be registered.
     */
    public static Font registerAWTFont(@NotNull String name, @NotNull Font font) {
        return registerAWTFont(name, font, false);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load from a file continuously.
     * If a font has already been registered, this won't try to reload the font.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param inputStream The InputStream of the font being registered. The font format must be TTF (TrueType Font).
     * @throws IOException
     * @throws FontFormatException
     */
    public static Font registerAWTFont(@NotNull String name, @NotNull InputStream inputStream) throws IOException, FontFormatException {
        return registerAWTFont(name, Font.createFont(Font.TRUETYPE_FONT, inputStream), false);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load from a file continuously.
     * If a font has already been registered, this won't try to reload the font unless if the overwrite parameter is true.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param inputStream The InputStream of the font being registered. The font format must be TTF (TrueType Font).
     * @param overwrite If the font has already been registered, should it be overwritten?
     * @throws IOException
     * @throws FontFormatException
     */
    public static Font registerAWTFont(@NotNull String name, @NotNull InputStream inputStream, boolean overwrite) throws IOException, FontFormatException {
        return registerAWTFont(name, Font.createFont(Font.TRUETYPE_FONT, inputStream), overwrite);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load from a file continuously.
     * If a font has already been registered, this won't try to reload the font.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param inputStream The InputStream of the font being registered. The font format must be whatever is specified in the fontType parameter.
     * @param fontType The type of font (EgL Font.TRUETYPE_FONT)
     * @throws IOException
     * @throws FontFormatException
     */
    public static Font registerAWTFont(@NotNull String name, @NotNull InputStream inputStream, int fontType) throws IOException, FontFormatException {
        return registerAWTFont(name, Font.createFont(fontType, inputStream), false);
    }

    /**
     * This will register a font with the font cache. This allows you to get fonts without having to load from a file continuously.
     * If a font has already been registered, this won't try to reload the font unless if the overwrite parameter is true.
     *
     * @param name The name of the font. This is used to identify which font to retrieve when getting a font.
     * @param inputStream The InputStream of the font being registered. The font format must be whatever is specified in the fontType parameter.
     * @param fontType The type of font (EgL Font.TRUETYPE_FONT)
     * @param overwrite If the font has already been registered, should it be overwritten?
     * @throws IOException
     * @throws FontFormatException
     */
    public static Font registerAWTFont(@NotNull String name, @NotNull InputStream inputStream, int fontType, boolean overwrite) throws IOException, FontFormatException {
        return registerAWTFont(name, Font.createFont(fontType, inputStream), overwrite);
    }

    /**
     * @param fontIdentifier The identifier of the possibly registered font
     * @return True if the {@link org.newdawn.slick.UnicodeFont} has been cached, false otherwise
     */
    public static boolean isUnicodeFontRegistered(@NotNull FontIdentifier fontIdentifier) {
        return unicodeFontCache.containsKey(fontIdentifier);
    }

    /**
     * @param name The name of the possibly registered font
     * @param size The size of the possibly registered font
     * @return True if the {@link org.newdawn.slick.UnicodeFont} has been cached, false otherwise
     */
    public static boolean isUnicodeFontRegistered(@NotNull String name, int size) {
        return isUnicodeFontRegistered(new FontIdentifier(name, size));
    }

    /**
     * @param font The possibly registered font
     * @return True if the {@link org.newdawn.slick.UnicodeFont} has been cached, false otherwise
     */
    public static boolean isUnicodeFontRegistered(@NotNull UnicodeFont font) {
        return unicodeFontCache.containsValue(font);
    }

    /**
     * @param name The name of the possibly registered font
     * @return True if the {@link java.awt.Font} has been cached, false otherwise
     */
    public static boolean isAWTFontRegistered(@NotNull String name) {
        return awtFontCache.containsKey(name);
    }

    /**
     * @param font The the possibly registered font
     * @return True if the {@link java.awt.Font} has been cached, false otherwise
     */
    public static boolean isAWTFontRegistered(@NotNull Font font) {
        return awtFontCache.containsValue(font);
    }

    /**
     * @param fontIdentifier The identifier of the possibly registered font
     * @return The UnicodeFont corresponding to the fontIdentifier, or null if the font hasn't been registered.
     */
    @Nullable
    public static UnicodeFont getUnicodeFont(@NotNull FontIdentifier fontIdentifier) {
        if (isUnicodeFontRegistered(fontIdentifier)) {
            return unicodeFontCache.get(fontIdentifier);
        } else {
            return null;
        }
    }

    /**
     * @param name The name of the possibly registered font
     * @param size The size of the possibly registered font
     * @return The UnicodeFont corresponding to the name and size given, or null if the font hasn't been registered.
     */
    @Nullable
    public static UnicodeFont getUnicodeFont(@NotNull String name, int size) {
        return getUnicodeFont(new FontIdentifier(name, size));
    }

    @Nullable
    public static Font getAWTFont(@NotNull String name) {
        if (isAWTFontRegistered(name)) {
            return awtFontCache.get(name);
        } else {
            return null;
        }
    }

    /**
     * Used to identify fonts by their name and size
     */
    public static class FontIdentifier {
        /**
         * The name of the font
         */
        public String name;
        /**
         * The size of the font
         */
        public int size;

        /**
         * @param name The name of the font
         * @param size The size of the font
         */
        public FontIdentifier(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof FontIdentifier) {
                FontIdentifier fontIdentifier = (FontIdentifier) obj;
                return fontIdentifier.name.equals(name) && fontIdentifier.size == size;
            } else {
                return false;
            }
        }

        @Override
        public int hashCode() {
            int toReturn = 0;
            for (byte b : name.getBytes()) {
                toReturn += b;
            }
            return toReturn + size;
        }
    }

}
