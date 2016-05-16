package io.github.craftedcart.fluidui.plugin;

import io.github.craftedcart.fluidui.util.AnchorPoint;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author CraftedCart
 * Created on 05/04/2016 (DD/MM/YYYY)
 */
public class PluginSmoothAnimateAnchor extends AbstractComponentPlugin {

    @Nullable public AnchorPoint targetTopLeftAnchor;
    @Nullable public AnchorPoint targetBottomRightAnchor;
    /**
     * Must be a value more than 0
     */
    public double animateSpeed = 16;

    @Override
    public void onPreDraw() {
        if (targetTopLeftAnchor != null && linkedComponent.topLeftAnchor != null) {
            linkedComponent.topLeftAnchor = linkedComponent.topLeftAnchor.lerpTo(targetTopLeftAnchor, Math.min(animateSpeed * UIUtils.getDelta(), 1));

            if (linkedComponent.topLeftAnchor.xPercent < targetTopLeftAnchor.xPercent + 0.001 &&
                    linkedComponent.topLeftAnchor.xPercent > targetTopLeftAnchor.xPercent - 0.001 &&
                    linkedComponent.topLeftAnchor.yPercent < targetTopLeftAnchor.yPercent + 0.001 &&
                    linkedComponent.topLeftAnchor.yPercent > targetTopLeftAnchor.yPercent - 0.001) { //It's less than 0.1% off of the target position
                linkedComponent.topLeftAnchor = targetTopLeftAnchor;
                targetTopLeftAnchor = null;
            }
        }
        
        if (targetBottomRightAnchor != null && linkedComponent.bottomRightAnchor != null) {
            linkedComponent.bottomRightAnchor = linkedComponent.bottomRightAnchor.lerpTo(targetBottomRightAnchor, Math.min(animateSpeed * UIUtils.getDelta(), 1));

            if (linkedComponent.bottomRightAnchor.xPercent < targetBottomRightAnchor.xPercent + 0.001 &&
                    linkedComponent.bottomRightAnchor.xPercent > targetBottomRightAnchor.xPercent - 0.001 &&
                    linkedComponent.bottomRightAnchor.yPercent < targetBottomRightAnchor.yPercent + 0.001 &&
                    linkedComponent.bottomRightAnchor.yPercent > targetBottomRightAnchor.yPercent - 0.001) { //It's less than 0.1% off of the target position
                linkedComponent.bottomRightAnchor = targetBottomRightAnchor;
                targetBottomRightAnchor = null;
            }
        }
    }

    @Override
    public void onTopLeftAnchorChanged(AnchorPoint newAnchor) {
        targetTopLeftAnchor = null;
    }

    @Override
    public void onBottomRightAnchorChanged(AnchorPoint newAnchor) {
        targetBottomRightAnchor = null;
    }

    public void setTargetTopLeftAnchor(@Nullable AnchorPoint targetTopLeftAnchor) {
        this.targetTopLeftAnchor = targetTopLeftAnchor;
    }

    public void setTargetTopLeftAnchor(double x, double y) {
        setTargetTopLeftAnchor(new AnchorPoint(x, y));
    }

    public void setTargetBottomRightAnchor(@Nullable AnchorPoint targetBottomRightAnchor) {
        this.targetBottomRightAnchor = targetBottomRightAnchor;
    }

    public void setTargetBottomRightAnchor(double x, double y) {
        setTargetBottomRightAnchor(new AnchorPoint(x, y));
    }

    public void setAnimateSpeed(double animateSpeed) {
        this.animateSpeed = animateSpeed;
    }

}
