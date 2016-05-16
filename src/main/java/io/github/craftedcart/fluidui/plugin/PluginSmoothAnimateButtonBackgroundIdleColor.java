package io.github.craftedcart.fluidui.plugin;

import io.github.craftedcart.fluidui.component.TextButton;
import io.github.craftedcart.fluidui.util.MathUtils;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author CraftedCart
 * Created on 05/04/2016 (DD/MM/YYYY)
 */
public class PluginSmoothAnimateButtonBackgroundIdleColor extends AbstractComponentPlugin {

    @Nullable public UIColor targetBackgroundIdleColor;
    /**
     * Must be a value more than 0
     */
    public double animateSpeed = 8;

    @Override
    public void onPreDraw() {
        TextButton linkedComponent = (TextButton) this.linkedComponent;

        if (targetBackgroundIdleColor != null) {
            linkedComponent.backgroundIdleColor.r = MathUtils.lerp(linkedComponent.backgroundIdleColor.r, targetBackgroundIdleColor.r, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.backgroundIdleColor.g = MathUtils.lerp(linkedComponent.backgroundIdleColor.g, targetBackgroundIdleColor.g, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.backgroundIdleColor.b = MathUtils.lerp(linkedComponent.backgroundIdleColor.b, targetBackgroundIdleColor.b, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.backgroundIdleColor.a = MathUtils.lerp(linkedComponent.backgroundIdleColor.a, targetBackgroundIdleColor.a, Math.min(animateSpeed * UIUtils.getDelta(), 1));

            if (linkedComponent.backgroundIdleColor.r < targetBackgroundIdleColor.r + 0.001 &&
                    linkedComponent.backgroundIdleColor.r > targetBackgroundIdleColor.r - 0.001 &&
                    linkedComponent.backgroundIdleColor.g < targetBackgroundIdleColor.g + 0.001 &&
                    linkedComponent.backgroundIdleColor.g > targetBackgroundIdleColor.g - 0.001 &&
                    linkedComponent.backgroundIdleColor.b < targetBackgroundIdleColor.b + 0.001 &&
                    linkedComponent.backgroundIdleColor.b > targetBackgroundIdleColor.b - 0.001 &&
                    linkedComponent.backgroundIdleColor.a < targetBackgroundIdleColor.a + 0.001 &&
                    linkedComponent.backgroundIdleColor.a > targetBackgroundIdleColor.a - 0.001) { //The current background idle color is within 0.001 of the target
                linkedComponent.setBackgroundIdleColor(targetBackgroundIdleColor);
                targetBackgroundIdleColor = null;
            }
        }
    }

    public void setTargetBackgroundIdleColor(@Nullable UIColor targetBackgroundIdleColor) {
        this.targetBackgroundIdleColor = targetBackgroundIdleColor;
    }

    public void setAnimateSpeed(double animateSpeed) {
        this.animateSpeed = animateSpeed;
    }

}
