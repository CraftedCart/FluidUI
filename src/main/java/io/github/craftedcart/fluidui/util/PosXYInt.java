package io.github.craftedcart.fluidui.util;

/**
 * Created by CraftedCart on 28/03/2016 (DD/MM/YYYY)
 */
public class PosXYInt {

    public int x;
    public int y;

    @SuppressWarnings("unused")
    public PosXYInt() {} //Used for Kryonet

    public PosXYInt(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public PosXYInt add(PosXYInt toAdd) {
        return new PosXYInt(x + toAdd.x, y + toAdd.y);
    }

    public PosXYInt add(int x, int y) {
        return add(new PosXYInt(x, y));
    }

    public PosXYInt multiply(int factor) {
        return new PosXYInt(x * factor, y * factor);
    }

    public PosXY toPosXY() {
        return new PosXY(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PosXYInt) {
            PosXYInt posXYInt = (PosXYInt) obj;
            return x == posXYInt.x && y == posXYInt.y;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return x * 31 + y;
    }

}
