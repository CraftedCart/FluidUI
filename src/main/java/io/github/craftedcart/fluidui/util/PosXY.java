package io.github.craftedcart.fluidui.util;

/**
 * Created by CraftedCart on 25/01/2015 (DD/MM/YYYY)
 */
public class PosXY {

    public double x;
    public double y;

    public PosXY() {} //Used for Kryonet

    public PosXY(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public PosXY add(PosXY pos) {
        return new PosXY(this.x + pos.x, this.y + pos.y);
    }

    public PosXY add(double x, double y) {
        return new PosXY(this.x + x, this.y + y);
    }

    public PosXY subtract(PosXY pos) {
        return new PosXY(this.x - pos.x, this.y - pos.y);
    }

    public PosXY subtract(double x, double y) {
        return new PosXY(this.x - x, this.y - y);
    }

    public PosXY multiply(double factor) {
        return new PosXY(x * factor, y * factor);
    }

    public PosXY divide(double factor) {
        return new PosXY(x / factor, y / factor);
    }

    public PosXYInt floor() {
        return new PosXYInt((int) Math.floor(x), (int) Math.floor(y));
    }

    public PosXY lerpTo(PosXY targetPos, double f) {
        return new PosXY(MathUtils.lerp(x, targetPos.x, f), MathUtils.lerp(y, targetPos.y, f));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PosXY) {
            PosXY posObj = (PosXY) obj;
            return posObj.x == x && posObj.y == y;
        } else {
            return false;
        }
    }
}
