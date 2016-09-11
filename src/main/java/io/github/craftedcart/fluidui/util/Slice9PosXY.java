package io.github.craftedcart.fluidui.util;

/**
 * @author CraftedCart
 * Created on 19/05/2015 (DD/MM/YYYY)
 */
public class Slice9PosXY {

    public PosXY topLeftPos;
    public PosXY bottomRightPos;

    public Slice9PosXY(double topLeftX, double topLeftY, double bottomRightX, double bottomRightY) {
        topLeftPos = new PosXY(topLeftX, topLeftY);
        bottomRightPos = new PosXY(bottomRightX, bottomRightY);
    }

    public Slice9PosXY(PosXY topLeftPos, PosXY bottomRightPos) {
        this.topLeftPos = topLeftPos;
        this.bottomRightPos = bottomRightPos;
    }

}
