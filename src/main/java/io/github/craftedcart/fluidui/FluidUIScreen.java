package io.github.craftedcart.fluidui;

import io.github.craftedcart.fluidui.component.Component;
import io.github.craftedcart.fluidui.util.PosXY;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;

/**
 * @author CraftedCart
 * Created on 03/03/2016 (DD/MM/YYYY)
 */
public abstract class FluidUIScreen extends Component implements IUIScreen {

    @Nullable public IUIScreen overlayUiScreen;

    @Override
    public void draw() {
        topLeftPos = new PosXY(0, 0);
        topLeftPx = new PosXY(0, 0);
        bottomRightPos = new PosXY(Display.getWidth(), Display.getHeight());
        bottomRightPx = new PosXY(Display.getWidth(), Display.getHeight());

        if (overlayUiScreen == null) {
            getParentMousePos();
        } else {
            mousePos = null;
        }

        preDraw();
        postDraw();

        if (overlayUiScreen != null) {
            overlayUiScreen.draw();
        }
    }

    @Override
    protected void getParentMousePos() {
        mousePos = new PosXY(Mouse.getX(), Display.getHeight() - Mouse.getY());
    }

    @Override
    protected boolean checkMouseOver() {
        try {
            if (Display.isCreated() && Display.isCurrent()) {
                FloatBuffer mat = BufferUtils.createFloatBuffer(16);
                GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, mat);

                return mousePos != null && topLeftPos != null && bottomRightPos != null &&
                        mousePos.x > topLeftPx.x + mat.get(12) && mousePos.y > topLeftPx.y + mat.get(13) &&
                        mousePos.x <= bottomRightPx.x + mat.get(12) && mousePos.y <= bottomRightPx.y + mat.get(13);
            } else {
                return false;
            }
        } catch (LWJGLException e) {
            return false;
        }
    }

    /**
     * If you're overriding this, you should probably check if overlayUiScreen == null
     *
     * @param button The mouse button clicked
     * @param mousePos The position of the mouse
     */
    @Override
    public void onClick(int button, PosXY mousePos) {
        if (overlayUiScreen == null) {
            super.onClick(button, mousePos);
        } else {
            overlayUiScreen.onClick(button, mousePos);
        }
    }

    /**
     * If you're overriding this, you should probably check if overlayUiScreen == null
     *
     * @param button The mouse button clicked
     * @param mousePos The position of the mouse
     */
    @Override
    public void onClickReleased(int button, PosXY mousePos) {
        if (overlayUiScreen == null) {
            super.onClickReleased(button, mousePos);
        } else {
            overlayUiScreen.onClickReleased(button, mousePos);
        }
    }

    /**
     * If you're overriding this, you should probably check if overlayUiScreen == null
     *
     * @param key The key ID pressed
     */
    @Override
    public void onKeyDown(int key, char keyChar) {
        if (overlayUiScreen != null) {
            overlayUiScreen.onKeyDown(key, keyChar);
        } else {
            super.onKeyDown(key, keyChar);
        }
    }

    /**
     * If you're overriding this, you should probably check if overlayUiScreen == null
     *
     * @param key The key ID pressed
     */
    @Override
    public void onKeyReleased(int key, char keyChar) {
        if (overlayUiScreen != null) {
            overlayUiScreen.onKeyReleased(key, keyChar);
        } else {
            super.onKeyReleased(key, keyChar);
        }
    }

    public void setOverlayUiScreen(@Nullable IUIScreen overlayUiScreen) {
        this.overlayUiScreen = overlayUiScreen;
        if (overlayUiScreen instanceof FluidUIScreen) {
            ((FluidUIScreen) overlayUiScreen).setParentComponent(this);
        }
    }

}
