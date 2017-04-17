package io.github.craftedcart.fluidui.util;

import org.lwjgl.opengl.GL11;

/**
 * @author CraftedCart
 * Created on 29/12/2015 (DD/MM/YYYY)
 */
public class UIColor {

    public double r;
    public double g;
    public double b;
    public double a = 1;

    public UIColor(double r, double g, double b) {
        this.r = r / 255;
        this.g = g / 255;
        this.b = b / 255;
    }

    public UIColor(double r, double g, double b, double a) {
        this.r = r / 255;
        this.g = g / 255;
        this.b = b / 255;
        this.a = a;
    }

    public void bindColor() {
        GL11.glColor4d(r, g, b, a);
    }

    public void bindClearColor() {
        GL11.glClearColor((float) r, (float) g, (float) b, (float) a);
    }

    public UIColor alpha(double a) {
        return new UIColor(r * 255, g * 255, b * 255, a);
    }

    public static UIColor transparent() {
        return new UIColor(0, 0, 0, 0);
    }

    public static UIColor pureWhite(double a) {
        return new UIColor(255, 255, 255, a);
    }

    public static UIColor pureWhite() {
        return pureWhite(1);
    }

    public static UIColor pureBlack() {
        return pureBlack(1);
    }

    public static UIColor pureBlack(double a) {
        return new UIColor(0, 0, 0, a);
    }

    public static UIColor matBlue(double a) {
        return new UIColor(33, 150, 243, a);
    }

    public static UIColor matBlue() {
        return matBlue(1);
    }

    public static UIColor matBlue700(double a) {
        return new UIColor(25, 118, 210, a);
    }

    public static UIColor matBlue700() {
        return matBlue700(1);
    }

    public static UIColor matBlue900(double a) {
        return new UIColor(13, 71, 161, a);
    }

    public static UIColor matBlue900() {
        return matBlue900(1);
    }

    public static UIColor matLightBlue(double a) {
        return new UIColor(3, 169, 244);
    }

    public static UIColor matLightBlue() {
        return matLightBlue(1);
    }

    public static UIColor matWhite(double a) {
        return new UIColor(250, 250, 250, a);
    }

    public static UIColor matWhite() {
        return matWhite(1);
    }

    public static UIColor matGrey300(double a) {
        return new UIColor(244, 244, 244, a);
    }

    public static UIColor matGrey300() {
        return matGrey300(1);
    }

    public static UIColor matGrey400(double a) {
        return new UIColor(189, 189, 189, a);
    }

    public static UIColor matGrey400() {
        return matGrey400(1);
    }

    public static UIColor matGrey(double a) {
        return new UIColor(158, 158, 158, a);
    }

    public static UIColor matGrey() {
        return matGrey(1);
    }

    public static UIColor matGrey800(double a) {
        return new UIColor(66, 66, 66, a);
    }

    public static UIColor matGrey800() {
        return matGrey800(1);
    }

    public static UIColor matGrey900(double a) {
        return new UIColor(33, 33, 33, a);
    }

    public static UIColor matGrey900() {
        return matGrey900(1);
    }

    public static UIColor matBlueGrey(double a) {
        return new UIColor(96, 125, 139, a);
    }

    public static UIColor matBlueGrey() {
        return matBlueGrey(1);
    }

    public static UIColor matBlueGrey300(double a) {
        return new UIColor(144, 164, 174, a);
    }

    public static UIColor matBlueGrey300() {
        return matBlueGrey300(1);
    }

    public static UIColor matBlueGrey700(double a) {
        return new UIColor(69, 90, 100, a);
    }

    public static UIColor matBlueGrey700() {
        return matBlueGrey700(1);
    }

    public static UIColor matRed(double a) {
        return new UIColor(244, 67, 54, a);
    }

    public static UIColor matRed() {
        return matRed(1);
    }

    public static UIColor matRed900(double a) {
        return new UIColor(183, 28, 28, a);
    }

    public static UIColor matRed900() {
        return matRed900(1);
    }

    public static UIColor matOrange(double a) {
        return new UIColor(255, 152, 0, a);
    }

    public static UIColor matOrange() {
        return matOrange(1);
    }

    public static UIColor matDeepOrange(double a) {
        return new UIColor(255, 87, 24, a);
    }

    public static UIColor matDeepOrange() {
        return matDeepOrange(1);
    }

    public static UIColor matGreen(double a) {
        return new UIColor(76, 175, 80, a);
    }

    public static UIColor matGreen() {
        return matGreen(1);
    }

    public static UIColor matGreen900(double a) {
        return new UIColor(27, 94, 32, a);
    }

    public static UIColor matGreen900() {
        return matGreen900(1);
    }

    public static UIColor matLightGreen(double a) {
        return new UIColor(139, 195, 74, a);
    }

    public static UIColor matLightGreen() {
        return matLightGreen(1);
    }

    public static UIColor matLime(double a) {
        return new UIColor(205, 220, 57, a);
    }

    public static UIColor matLime() {
        return matLime(1);
    }

    public static UIColor matYellow(double a) {
        return new UIColor(255, 235, 1, a);
    }

    public static UIColor matYellow() {
        return matYellow(1);
    }

    public static UIColor matAmber(double a) {
        return new UIColor(255, 193, 7, a);
    }

    public static UIColor matAmber() {
        return matAmber(1);
    }

    public static UIColor matIndigo(double a) {
        return new UIColor(63, 81, 181, a);
    }

    public static UIColor matIndigo() {
        return matIndigo(1);
    }

    public static UIColor matTeal(double a) {
        return new UIColor(0, 150, 136, a);
    }

    public static UIColor matTeal() {
        return matTeal(1);
    }

    public static UIColor matPurple300(double a) {
        return new UIColor(186, 104, 200, a);
    }

    public static UIColor matPurple300() {
        return matPurple300(1);
    }

    public static UIColor matPurple(double a) {
        return new UIColor(156, 39, 176, a);
    }

    public static UIColor matPurple() {
        return matPurple(1);
    }

    public static UIColor matPurple700(double a) {
        return new UIColor(123, 31, 162, a);
    }

    public static UIColor matPurple700() {
        return matPurple700(1);
    }

    public static UIColor matPurple900(double a) {
        return new UIColor(74, 20, 140, a);
    }

    public static UIColor matPurple900() {
        return matPurple900(1);
    }

    public static UIColor matDeepPurple(double a) {
        return new UIColor(103, 58, 183, a);
    }

    public static UIColor matDeepPurple() {
        return matDeepPurple(1);
    }

    public static UIColor matDeepPurple900(double a) {
        return new UIColor(49, 27, 146, a);
    }

    public static UIColor matDeepPurple900() {
        return matDeepPurple900(1);
    }

    public static UIColor matPink(double a) {
        return new UIColor(233, 30, 99, a);
    }

    public static UIColor matPink() {
        return matPink(1);
    }

    public static UIColor matPink900(double a) {
        return new UIColor(136, 14, 79, a);
    }

    public static UIColor matPink900() {
        return matPink900(1);
    }

    public static UIColor matCyan(double a) {
        return new UIColor(0, 188, 212, a);
    }

    public static UIColor matCyan() {
        return matCyan(1);
    }

    public static UIColor matBrown(double a) {
        return new UIColor(121, 85, 72, a);
    }

    public static UIColor matBrown() {
        return matBrown(1);
    }

}
