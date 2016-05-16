package io.github.craftedcart.fluidui.plugin;

import io.github.craftedcart.fluidui.component.Panel;
import io.github.craftedcart.fluidui.util.MathUtils;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author CraftedCart
 * Created on 11/05/2016 (DD/MM/YYYY)
 */
public class PluginSmoothAnimatePanelBackgroundColor extends AbstractComponentPlugin {

    @Nullable public UIColor targetBackgroundColor;
    /**
     * Must be a value more than 0
     */
    public double animateSpeed = 8;

    @Override
    public void onPreDraw() {
        Panel linkedComponent = (Panel) this.linkedComponent;

        if (targetBackgroundColor != null) {
            linkedComponent.backgroundColor.r = MathUtils.lerp(linkedComponent.backgroundColor.r, targetBackgroundColor.r, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.backgroundColor.g = MathUtils.lerp(linkedComponent.backgroundColor.g, targetBackgroundColor.g, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.backgroundColor.b = MathUtils.lerp(linkedComponent.backgroundColor.b, targetBackgroundColor.b, Math.min(animateSpeed * UIUtils.getDelta(), 1));
            linkedComponent.backgroundColor.a = MathUtils.lerp(linkedComponent.backgroundColor.a, targetBackgroundColor.a, Math.min(animateSpeed * UIUtils.getDelta(), 1));

            if (linkedComponent.backgroundColor.r < targetBackgroundColor.r + 0.001 &&
                    linkedComponent.backgroundColor.r > targetBackgroundColor.r - 0.001 &&
                    linkedComponent.backgroundColor.g < targetBackgroundColor.g + 0.001 &&
                    linkedComponent.backgroundColor.g > targetBackgroundColor.g - 0.001 &&
                    linkedComponent.backgroundColor.b < targetBackgroundColor.b + 0.001 &&
                    linkedComponent.backgroundColor.b > targetBackgroundColor.b - 0.001 &&
                    linkedComponent.backgroundColor.a < targetBackgroundColor.a + 0.001 &&
                    linkedComponent.backgroundColor.a > targetBackgroundColor.a - 0.001) { //The current background color is within 0.001 of the target
                linkedComponent.setBackgroundColor(targetBackgroundColor);
                targetBackgroundColor = null;
            }
        }
    }

    public void setTargetBackgroundColor(@Nullable UIColor targetBackgroundColor) {
        this.targetBackgroundColor = targetBackgroundColor;
    }

    public void setAnimateSpeed(double animateSpeed) {
        this.animateSpeed = animateSpeed;
    }

}
