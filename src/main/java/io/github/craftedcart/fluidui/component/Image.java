package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.util.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.newdawn.slick.opengl.Texture;

/**
 * @author CraftedCart
 * Created on 03/03/2016 (DD/MM/YYYY)
 */
public class Image extends Component {

    @Nullable public Texture texture;
    @Nullable public Slice9PosXY textureSlice9;
    @NotNull public UIColor color = UIColor.pureWhite();
    @NotNull public EnumImageScaling imageScaling = EnumImageScaling.scale;

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
            double height = 0;
            double width = 0;

            if (imageScaling == EnumImageScaling.scale) { //Scale
                if (this.height / this.width > texRatio) {
                    height = this.height;
                    width = (int) (height / texRatio);
                } else {
                    width = this.width;
                    height = (int) (width * texRatio);
                }
            } else if (imageScaling == EnumImageScaling.fit) { //Fit
                if (this.height / this.width < texRatio) {
                    height = this.height;
                    width = (int) (height / texRatio);
                } else {
                    width = this.width;
                    height = (int) (width * texRatio);
                }
            } else if (imageScaling == EnumImageScaling.stretch) { //Stretch
                width = this.width * texture.getWidth();
                height = this.height * texture.getHeight();
            }

            if (textureSlice9 == null) {
                UIUtils.setupStencilMask();
                UIUtils.drawQuad(topLeftPx, bottomRightPx, UIColor.pureWhite());
                UIUtils.setupStencilDraw();
                color.bindColor();
                UIUtils.drawTexturedQuad(
                        new PosXY(topLeftPx.x + this.width / 2 - width / 2, topLeftPx.y + this.height / 2 - height / 2),
                        new PosXY(topLeftPx.x + this.width / 2 + width / 2, topLeftPx.y + this.height / 2 + height / 2),
                        texture);
                UIUtils.setupStencilEnd();
            } else {
                color.bindColor();
                UIUtils.drawTexturedQuad(
                        topLeftPx,
                        bottomRightPx,
                        texture,
                        textureSlice9);
            }

        }
    }

    public void setTexture(@Nullable Texture texture) {
        this.texture = texture;
    }

    public void setTextureSlice9(@Nullable Slice9PosXY textureSlice9) {
        this.textureSlice9 = textureSlice9;
    }

    public void setColor(@NotNull UIColor color) {
        this.color = color;
    }

    public void setImageScaling(@NotNull EnumImageScaling imageScaling) {
        this.imageScaling = imageScaling;
    }
}
