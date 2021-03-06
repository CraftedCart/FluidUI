package io.github.craftedcart.fluidui;

import io.github.craftedcart.fluidui.util.PosXY;

/**
 * @author CraftedCart
 * Created on 02/04/2016 (DD/MM/YYYY)
 */
public interface IUIScreen {

    void draw();

    default void onClick(int button, PosXY mousePos) {
        //No-Op
    }

    default void onClickReleased(int button, PosXY mousePos) {
        //No-Op
    }

    default void onClickAnywhere(int button, PosXY mousePos) {
        //No-Op
    }

    default void onKeyDown(int key, char keyChar) {
        //No-Op
    }

    default void onKeyReleased(int key, char keyChar) {
        //No-Op
    }

}
