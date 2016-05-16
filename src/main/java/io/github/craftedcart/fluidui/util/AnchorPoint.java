package io.github.craftedcart.fluidui.util;

/**
 * @author CraftedCart
 * Created on 09/01/2016 (DD/MM/YYYY)
 */
public class AnchorPoint {

    public double xPercent;
    public double yPercent;

    public AnchorPoint(double xPercent, double yPercent) {
        this.xPercent = xPercent;
        this.yPercent = yPercent;
    }

    public AnchorPoint lerpTo(AnchorPoint targetAnchor, double f) {
        return new AnchorPoint(MathUtils.lerp(xPercent, targetAnchor.xPercent, f), MathUtils.lerp(yPercent, targetAnchor.yPercent, f));
    }

    public AnchorPoint add(AnchorPoint toAdd) {
        return new AnchorPoint(xPercent + toAdd.xPercent, yPercent + toAdd.yPercent);
    }

    public AnchorPoint add(double xPercent, double yPercent) {
        return new AnchorPoint(this.xPercent + xPercent, this.yPercent + yPercent);
    }

    public AnchorPoint subtract(AnchorPoint toAdd) {
        return new AnchorPoint(xPercent - toAdd.xPercent, yPercent - toAdd.yPercent);
    }

    public AnchorPoint subtract(double xPercent, double yPercent) {
        return new AnchorPoint(this.xPercent - xPercent, this.yPercent - yPercent);
    }

}
