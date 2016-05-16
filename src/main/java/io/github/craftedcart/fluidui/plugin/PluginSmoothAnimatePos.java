package io.github.craftedcart.fluidui.plugin;

import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.Nullable;

/**
 * @author CraftedCart
 * Created on 05/04/2016 (DD/MM/YYYY)
 */
public class PluginSmoothAnimatePos extends AbstractComponentPlugin {

    @Nullable public PosXY targetTopLeftPos;
    @Nullable public PosXY targetBottomRightPos;
    /**
     * Must be a value more than 0
     */
    public double animateSpeed = 16;

    @Override
    public void onPreDraw() {
        if (targetTopLeftPos != null && linkedComponent.topLeftPos != null) {
            linkedComponent.topLeftPos = linkedComponent.topLeftPos.lerpTo(targetTopLeftPos, Math.min(animateSpeed * UIUtils.getDelta(), 1));

            if (linkedComponent.topLeftPos.x < targetTopLeftPos.x + 1 &&
                    linkedComponent.topLeftPos.x > targetTopLeftPos.x - 1 &&
                    linkedComponent.topLeftPos.y < targetTopLeftPos.y + 1 &&
                    linkedComponent.topLeftPos.y > targetTopLeftPos.y- 1) { //It's less than 1px off of the target position
                linkedComponent.topLeftPos = targetTopLeftPos;
                targetTopLeftPos = null;
            }
        }
        
        if (targetBottomRightPos != null && linkedComponent.bottomRightPos != null) {
            linkedComponent.bottomRightPos = linkedComponent.bottomRightPos.lerpTo(targetBottomRightPos, Math.min(animateSpeed * UIUtils.getDelta(), 1));

            if (linkedComponent.bottomRightPos.x < targetBottomRightPos.x + 1 &&
                    linkedComponent.bottomRightPos.x > targetBottomRightPos.x - 1 &&
                    linkedComponent.bottomRightPos.y < targetBottomRightPos.y + 1 &&
                    linkedComponent.bottomRightPos.y > targetBottomRightPos.y - 1) { //It's less than 1px off of the target position
                linkedComponent.bottomRightPos = targetBottomRightPos;
                targetBottomRightPos = null;
            }
        }
    }

    @Override
    public void onTopLeftPosChanged(PosXY newPos) {
        targetTopLeftPos = null;
    }

    @Override
    public void onBottomRightPosChanged(PosXY newPos) {
        targetBottomRightPos = null;
    }

    public void setTargetTopLeftPos(@Nullable PosXY targetTopLeftPos) {
        this.targetTopLeftPos = targetTopLeftPos;
    }

    public void setTargetTopLeftPos(double x, double y) {
        setTargetTopLeftPos(new PosXY(x, y));
    }

    public void setTargetBottomRightPos(@Nullable PosXY targetBottomRightPos) {
        this.targetBottomRightPos = targetBottomRightPos;
    }

    public void setTargetBottomRightPos(double x, double y) {
        setTargetBottomRightPos(new PosXY(x, y));
    }

    public void setAnimateSpeed(double animateSpeed) {
        this.animateSpeed = animateSpeed;
    }

}
