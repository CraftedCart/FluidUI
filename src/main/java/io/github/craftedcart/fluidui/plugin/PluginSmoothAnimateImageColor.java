package io.github.craftedcart.fluidui.plugin;

import io.github.craftedcart.fluidui.component.Image;
import io.github.craftedcart.fluidui.component.Panel;
import io.github.craftedcart.fluidui.util.MathUtils;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author CraftedCart
 * Created on 11/05/2016 (DD/MM/YYYY)
 */
public class PluginSmoothAnimateImageColor extends AbstractComponentPlugin {

    @Nullable public UIColor targetColor;
    /**
     * Must be a value more than 0
     */
    public double animateSpeed = 8;

    @Override
    public void onPreDraw() {
        Image linkedComponent = (Image) this.linkedComponent;

        if (targetColor != null) {
            linkedComponent.color.r = MathUtils.lerp(linkedComponent.color.r, targetColor.r, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.color.g = MathUtils.lerp(linkedComponent.color.g, targetColor.g, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.color.b = MathUtils.lerp(linkedComponent.color.b, targetColor.b, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.color.a = MathUtils.lerp(linkedComponent.color.a, targetColor.a, Math.min(animateSpeed * UIUtils.getDelta(), 1));

            if (linkedComponent.color.r < targetColor.r + 0.001 &&
                    linkedComponent.color.r > targetColor.r - 0.001 &&
                    linkedComponent.color.g < targetColor.g + 0.001 &&
                    linkedComponent.color.g > targetColor.g - 0.001 &&
                    linkedComponent.color.b < targetColor.b + 0.001 &&
                    linkedComponent.color.b > targetColor.b - 0.001 &&
                    linkedComponent.color.a < targetColor.a + 0.001 &&
                    linkedComponent.color.a > targetColor.a - 0.001) { //The current background color is within 0.001 of the target
                linkedComponent.setColor(targetColor);
                targetColor = null;
            }
        }
    }

    public void setTargetColor(@Nullable UIColor targetColor) {
        this.targetColor = targetColor;
    }

    public void setAnimateSpeed(double animateSpeed) {
        this.animateSpeed = animateSpeed;
    }

}
