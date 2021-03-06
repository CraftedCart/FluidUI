package io.github.craftedcart.fluidui.util;

import io.github.craftedcart.fluidui.uiaction.UIAction;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;

import java.nio.FloatBuffer;

/**
 * @author CraftedCart
 * Created on 29/12/2015 (DD/MM/YYYY)
 */
public class UIUtils {

    private static long lastFrame = 0;
    private static long delta = 0;
    private static int mouseDWheel = 0;
    private static int mouseDX = 0;
    private static int mouseDY = 0;

    private static boolean cursorFadingOut = true;
    private static double cursorAlpha = 1;

    /**
     * Sets the projection to an orthographic one<br>
     * Optionally clears the color and depth buffers<br>
     * Enables blending<br>
     * Disables Texture2D<br>
     * Sets BlendFuncSeparate<br>
     * Sets the OpenGL bound color to pure white<br>
     * Unbinds any bound Texture2Ds
     *
     * @param clearBuffer Should the color and depth buffers be cleared?
     */
    public static void setup(boolean clearBuffer) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, 10000, -10000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        if (clearBuffer) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        }

        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);

        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4d(1, 1, 1, 1);
        TextureImpl.bindNone();
    }

    /**
     * Should be called every frame
     */
    public static void calcStuff() {
        calcDelta();
        calcMouseDelta();
        calcMouseDWheel();
        calcCursorAlpha();
    }

    public static void calcDelta() {
        long time = Sys.getTime();
        delta = (time - lastFrame);
        lastFrame = time;
    }

    public static double getDelta() {
        return delta / 1000d;
    }

    public static void calcMouseDelta() {
        mouseDX = Mouse.getDX();
        mouseDY = -Mouse.getDY();
    }

    public static PosXYInt getMouseDelta() {
        return new PosXYInt(mouseDX, mouseDY);
    }

    public static void calcMouseDWheel() {
        mouseDWheel = Mouse.getDWheel();
    }

    public static int getMouseDWheel() {
        return mouseDWheel;
    }

    public static void calcCursorAlpha() {
        if (cursorFadingOut) {
            cursorAlpha = Math.max(cursorAlpha - UIUtils.getDelta(), 0.2);
            if (cursorAlpha <= 0.5) {
                cursorFadingOut = false;
            }
        } else {
            cursorAlpha = Math.min(cursorAlpha + UIUtils.getDelta(), 1);
            if (cursorAlpha >= 1) {
                cursorFadingOut = true;
            }
        }
    }

    public static double getCursorAlpha() {
        return cursorAlpha;
    }

    public static void drawQuad(PosXY p1, PosXY p2, PosXY p3, PosXY p4, UIColor col) {
        GL11.glColor4d(col.r, col.g, col.b, col.a);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
            GL11.glVertex2d(p3.x, p3.y);
            GL11.glVertex2d(p4.x, p4.y);
        }
        GL11.glEnd();
    }

    public static void drawQuad(PosXY p1, PosXY p2, PosXY p3, PosXY p4) {
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
            GL11.glVertex2d(p3.x, p3.y);
            GL11.glVertex2d(p4.x, p4.y);
        }
        GL11.glEnd();
    }

    public static void drawQuadGradient(PosXY p1, PosXY p2, PosXY p3, PosXY p4, UIColor colFrom, UIColor colTo) {
        GL11.glColor4d(colFrom.r, colFrom.g, colFrom.b, colFrom.a);
        GL11.glBegin(GL11.GL_QUADS);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
            GL11.glColor4d(colTo.r, colTo.g, colTo.b, colTo.a);
            GL11.glVertex2d(p3.x, p3.y);
            GL11.glVertex2d(p4.x, p4.y);
        }
        GL11.glEnd();
    }

    public static void drawQuad(PosXY p1, PosXY p2, UIColor col) {
        drawQuad(
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                new PosXY(p2.x, p1.y),
                col
        );
    }

    public static void drawQuad(PosXY p1, PosXY p2) {
        drawQuad(
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                new PosXY(p2.x, p1.y)
        );
    }

    public static void drawTexturedQuad(PosXY p1, PosXY p2, Texture texture) {
        texture.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(GL11.GL_QUADS);
        {
            vertTexturedQuad(p1, p2, texture.getWidth(), texture.getHeight());
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static void drawTexturedQuad(PosXY p1, PosXY p2, Texture texture, Slice9PosXY textureSlice9) {
        texture.bind();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBegin(GL11.GL_QUADS);
        {
            //Top Left
            vertTexturedQuad(p1, p1.add(textureSlice9.topLeftPos),
                    textureSlice9.topLeftPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.topLeftPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()));

            //Top Middle
            vertTexturedQuad(p1.add(textureSlice9.topLeftPos.x, 0), new PosXY(p2.x, p1.y).subtract(texture.getImageWidth() - textureSlice9.bottomRightPos.x, -textureSlice9.topLeftPos.y),
                    textureSlice9.topLeftPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.bottomRightPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    0,
                    textureSlice9.topLeftPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()));

            //Top Right
            vertTexturedQuad(new PosXY(p2.x, p1.y).subtract(texture.getImageWidth() - textureSlice9.bottomRightPos.x, 0), new PosXY(p2.x, p1.y).add(0, textureSlice9.topLeftPos.y),
                    textureSlice9.bottomRightPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    texture.getWidth(),
                    0,
                    textureSlice9.topLeftPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()));

            //Middle Left
            vertTexturedQuad(p1.add(0, textureSlice9.topLeftPos.y), new PosXY(p1.x, p2.y).add(textureSlice9.topLeftPos.x, -(texture.getImageHeight() - textureSlice9.bottomRightPos.y)),
                    0,
                    textureSlice9.topLeftPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.topLeftPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()),
                    textureSlice9.bottomRightPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()));

            //Middle Middle
            vertTexturedQuad(p1.add(textureSlice9.topLeftPos),
                    p2.subtract(texture.getImageWidth() - textureSlice9.bottomRightPos.x, texture.getImageHeight() - textureSlice9.bottomRightPos.y),
                    textureSlice9.topLeftPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.bottomRightPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.topLeftPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()),
                    textureSlice9.bottomRightPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()));

            //Middle Right
            vertTexturedQuad(new PosXY(p2.x, p1.y).subtract(texture.getImageWidth() - textureSlice9.bottomRightPos.x, -textureSlice9.topLeftPos.y),
                    p2.subtract(0, texture.getImageHeight() - textureSlice9.bottomRightPos.y),
                    textureSlice9.bottomRightPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    texture.getWidth(),
                    textureSlice9.topLeftPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()),
                    textureSlice9.bottomRightPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()));

            //Bottom Left
            vertTexturedQuad(new PosXY(p1.x, p2.y).subtract(0, texture.getImageHeight() - textureSlice9.bottomRightPos.y),
                    new PosXY(p1.x, p2.y).add(textureSlice9.topLeftPos.x, 0),
                    0,
                    textureSlice9.topLeftPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.bottomRightPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()),
                    texture.getHeight());

            //Bottom Left
            vertTexturedQuad(new PosXY(p1.x, p2.y).subtract(-textureSlice9.topLeftPos.x, texture.getImageHeight() - textureSlice9.bottomRightPos.y),
                    p2.subtract(texture.getImageWidth() - textureSlice9.bottomRightPos.x, 0),
                    textureSlice9.topLeftPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.bottomRightPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    textureSlice9.bottomRightPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()),
                    texture.getHeight());

            //Bottom Right
            vertTexturedQuad(p2.subtract(texture.getImageWidth() - textureSlice9.bottomRightPos.x, texture.getImageHeight() - textureSlice9.bottomRightPos.y),
                    p2,
                    textureSlice9.bottomRightPos.x / texture.getImageWidth() * ((double) texture.getImageWidth() / texture.getTextureWidth()),
                    texture.getWidth(),
                    textureSlice9.bottomRightPos.y / texture.getImageHeight() * ((double) texture.getImageHeight() / texture.getTextureHeight()),
                    texture.getHeight());
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static void vertTexturedQuad(PosXY p1, PosXY p2, double texWidth, double texHeight) {
        GL11.glTexCoord2d(0, 0);
        GL11.glVertex2d(p1.x, p1.y);
        GL11.glTexCoord2d(0, texHeight);
        GL11.glVertex2d(p1.x, p2.y);
        GL11.glTexCoord2d(texWidth, texHeight);
        GL11.glVertex2d(p2.x, p2.y);
        GL11.glTexCoord2d(texWidth, 0);
        GL11.glVertex2d(p2.x, p1.y);
    }

    public static void vertTexturedQuad(PosXY p1, PosXY p2, double texX1, double texX2, double texY1, double texY2) {
        GL11.glTexCoord2d(texX1, texY1);
        GL11.glVertex2d(p1.x, p1.y);
        GL11.glTexCoord2d(texX1, texY2);
        GL11.glVertex2d(p1.x, p2.y);
        GL11.glTexCoord2d(texX2, texY2);
        GL11.glVertex2d(p2.x, p2.y);
        GL11.glTexCoord2d(texX2, texY1);
        GL11.glVertex2d(p2.x, p1.y);
    }

    public static void bufferTexturedQuad(PosXY p1, PosXY p2, float texWidth, float texHeight, FloatBuffer vertBuffer, FloatBuffer texBuffer) {
        texBuffer.put(new float[]{0, 0, 0, texHeight, texWidth, texHeight, texWidth, 0});

        float f1x = (float) p1.x;
        float f1y = (float) p1.y;
        float f2x = (float) p2.x;
        float f2y = (float) p2.y;

        vertBuffer.put(new float[]{f1x, f1y, f1x, f2y, f2x, f2y, f2x, f1y});
    }

    public static void drawQuadGradientHorizontal(PosXY p1, PosXY p2, UIColor colFrom, UIColor colTo) {
        drawQuadGradient(
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                new PosXY(p2.x, p1.y),
                colFrom, colTo
        );
    }

    public static void drawQuadGradientVertical(PosXY p1, PosXY p2, UIColor colFrom, UIColor colTo) {
        drawQuadGradient(
                new PosXY(p2.x, p1.y),
                new PosXY(p1.x, p1.y),
                new PosXY(p1.x, p2.y),
                new PosXY(p2.x, p2.y),
                colFrom, colTo
        );
    }

    public static void drawString(UnicodeFont font, int x, int y, String string, UIColor col) {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        font.drawString(x, y, string, new Color((int) (col.r * 255), (int) (col.g * 255), (int) (col.b * 255), (int) (col.a * 255)));
        GL11.glDisable(GL11.GL_TEXTURE_2D);
    }

    public static void drawString(UnicodeFont font, PosXY pos, String string, UIColor col) {
        drawString(font, (int) pos.x, (int) pos.y, string, col);
    }

    public static String wrapString(UnicodeFont font, int width, String string) {
        String currentString = "";
        int lastSubstringPos = 0;
        char[] charArray = string.toCharArray();

        for (int i = 0; i < charArray.length; i++) {

            int stringWidth = font.getWidth(string.substring(lastSubstringPos, i));

            if (charArray[i] == '\n') {
                stringWidth = 0;
                currentString = currentString + string.substring(lastSubstringPos, i) +'\n';
                lastSubstringPos = i + 1;
            }

            if (stringWidth > width) {
                i--;
                currentString = currentString + string.substring(lastSubstringPos, i) + '\n';
                lastSubstringPos = i;
            }


        }

        return currentString + string.substring(lastSubstringPos);
    }

    public static void drawLine(PosXY p1, PosXY p2, UIColor col) {
        GL11.glColor4d(col.r, col.g, col.b, col.a);
        GL11.glBegin(GL11.GL_LINES);
        {
            GL11.glVertex2d(p1.x, p1.y);
            GL11.glVertex2d(p2.x, p2.y);
        }
        GL11.glEnd();
    }

    public static void drawWithStencil(UIAction drawStencilShape, UIAction drawScene) {
        setupStencilMask();
        drawStencilShape.execute();
        setupStencilDraw();
        drawScene.execute();
        setupStencilEnd();
    }

    public static void drawWithStencilOutside(UIAction drawStencilShape, UIAction drawScene) {
        setupStencilMask();
        drawStencilShape.execute();
        setupStencilDrawOutside();
        drawScene.execute();
        setupStencilEnd();
    }

    public static void setupStencilMask() {
        GL11.glEnable(GL11.GL_STENCIL_TEST);
        GL11.glColorMask(false, false, false, false);
        GL11.glDepthMask(false);
        GL11.glStencilFunc(GL11.GL_NEVER, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_REPLACE, GL11.GL_KEEP, GL11.GL_KEEP);  // draw 1s on test fail (always)

        // draw stencil pattern
        GL11.glStencilMask(0xFF);
        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);  // needs mask=0xFF
    }

    public static void setupStencilDraw() {
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilMask(0x00);
        // draw only where stencil's value is 1
        GL11.glStencilFunc(GL11.GL_EQUAL, 1, 0xFF);
    }

    public static void setupStencilDrawOutside() {
        GL11.glColorMask(true, true, true, true);
        GL11.glDepthMask(true);
        GL11.glStencilMask(0x00);
        // draw only where stencil's value is 1
        GL11.glStencilFunc(GL11.GL_NOTEQUAL, 1, 0xFF);
    }

    public static void setupStencilEnd() {
        GL11.glDisable(GL11.GL_STENCIL_TEST);
    }

}
