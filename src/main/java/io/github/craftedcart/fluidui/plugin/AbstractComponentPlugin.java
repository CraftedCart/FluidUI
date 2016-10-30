package io.github.craftedcart.fluidui.plugin;

import io.github.craftedcart.fluidui.component.Component;
import io.github.craftedcart.fluidui.util.AnchorPoint;
import io.github.craftedcart.fluidui.util.PosXY;

/**
 * @author CraftedCart
 * Created on 05/04/2016 (DD/MM/YYYY)
 */
public abstract class AbstractComponentPlugin {

    public Component linkedComponent;

    public void onPostInit() {}

    public void onPreDraw() {}
    public void onPostDraw() {}

    public void onClick(int button, PosXY mousePos) {}
    public void onClickChildComponent(int button, PosXY mousePos) {}

    public void onTopLeftPosChanged(PosXY newPos) {}
    public void onBottomRightPosChanged(PosXY newPos) {}

    public void onTopLeftAnchorChanged(AnchorPoint newPoint) {}
    public void onBottomRightAnchorChanged(AnchorPoint newPoint) {}

}
