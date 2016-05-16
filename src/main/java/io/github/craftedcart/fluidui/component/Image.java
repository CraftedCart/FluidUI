package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

/**
 * @author CraftedCart
 * Created on 03/03/2016 (DD/MM/YYYY)
 */
public class Image extends Component {

    @Nullable private Texture texture;
    @NotNull public UIColor color = UIColor.pureWhite();

    public Image() {
        postInit();
    }

    @Override
    public void draw() {
        preDraw();
        componentDraw();
        postDraw();
    }

    public void componentDraw() {
        if (texture != null) {
            double texRatio = texture.getHeight() / (double) texture.getWidth(); //Get the aspect ratio of the texture
            double height;
            double width;
            if (Display.getHeight() / (double) Display.getWidth() > texRatio) {
                height = this.height;
                width = (int) (height / texRatio);
            } else {
                width = this.width;
                height = (int) (width * texRatio);
            }

            GL11.glEnable(GL11.GL_TEXTURE_2D);
            color.bindColor();
            UIUtils.drawTexturedQuad(
                    new PosXY(topLeftPx.x + this.width / 2 - width / 2, topLeftPx.y + this.height / 2 - height / 2),
                    new PosXY(topLeftPx.x + this.width / 2 + width / 2, topLeftPx.y + this.height / 2 + height / 2),
                    texture);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
        }
    }

    public void setTexture(@Nullable Texture texture) {
        this.texture = texture;
    }

    @Nullable
    public Texture getTexture() {
        return texture;
    }

    public void setColor(@NotNull UIColor color) {
        this.color = color;
    }
}
