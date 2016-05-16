package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.plugin.AbstractComponentPlugin;
import io.github.craftedcart.fluidui.theme.ThemeManager;
import io.github.craftedcart.fluidui.util.MathUtils;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CraftedCart
 * Created on 03/03/2016 (DD/MM/YYYY)
 */
public class ListBox extends Panel {

    public List<String> childComponentOrder = new ArrayList<String>();
    public double childComponentSpacing = 2;
    public double scrollOffset = 0;
    public double smoothedScrollOffset = 0;
    public double heightOfAllChildren = 0;

    public ListBox() {
        init();
        postInit();
    }

    public void init() {
        backgroundColor = ThemeManager.currentTheme.listBoxBackgroundColor;
    }

    public void draw() {
        detectScroll();

        preDraw();
        componentDraw();
        UIUtils.drawWithStencil(() -> UIUtils.drawQuad(topLeftPx, bottomRightPx, UIColor.pureWhite()), this::postDraw);
    }

    public void componentDraw() {
        UIUtils.drawQuad(topLeftPx, bottomRightPx, backgroundColor);
    }

    @Override
    public void postDraw() {
        GL11.glPushMatrix();
        GL11.glTranslated(0, smoothedScrollOffset, 0);

        for (AbstractComponentPlugin plugin : plugins) { //Plugins
            plugin.onPostDraw();
        }

        for (Map.Entry<String, Component> entry : childComponents.entrySet()) { //Draw children
            Component childComponent = entry.getValue();
            childComponent.preDraw();
            if (childComponent.isVisible() &&
                    childComponent.topLeftPx.y + smoothedScrollOffset <= bottomRightPx.y &&
                    childComponent.bottomRightPx.y + smoothedScrollOffset >= topLeftPx.y) {
                childComponent.draw();
            }
        }

        GL11.glPopMatrix();

        UIUtils.drawQuad(new PosXY(bottomRightPx.x - ThemeManager.currentTheme.scrollbarThickness, topLeftPx.y), bottomRightPx, ThemeManager.currentTheme.scrollbarBG);
        double scrollPercent = smoothedScrollOffset / (heightOfAllChildren - height);
        double scrollbarHeight = Math.max(24, height / heightOfAllChildren * height);
        UIUtils.drawQuad(new PosXY(bottomRightPx.x - ThemeManager.currentTheme.scrollbarThickness, topLeftPx.y - (height - scrollbarHeight) * scrollPercent),
                new PosXY(bottomRightPx.x, topLeftPx.y - (height - scrollbarHeight) * scrollPercent + scrollbarHeight), ThemeManager.currentTheme.scrollbarFG);
    }

    private void detectScroll() {
        if (mouseOver) {
            scrollOffset += UIUtils.getMouseDWheel() * ThemeManager.currentTheme.mouseSensitivity;
        }

        if (heightOfAllChildren > height) {
            scrollOffset = MathUtils.clamp(scrollOffset, -(heightOfAllChildren - height), 0);
        } else {
            scrollOffset = 0;
        }

        smoothedScrollOffset = MathUtils.lerp(smoothedScrollOffset, scrollOffset, Math.min(UIUtils.getDelta() * ThemeManager.currentTheme.scrollSmoothing, 1));
    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public double getHeightOfChildren(int maxComponentIndex) {
        double childrenHeight = 0;
        for (int i = 0; i < maxComponentIndex; i++) { //Loop over components
            childrenHeight += childComponents.get(childComponentOrder.get(i)).height + childComponentSpacing; //Add the height
        }
        return childrenHeight;
    }

    public double getHeightOfChildren() {
        return getHeightOfChildren(childComponentOrder.size());
    }

    @Override
    public void addChildComponent(@NotNull Component component) {
        super.addChildComponent(component);
        component.preDraw();

        double lastHeight = heightOfAllChildren;

        childComponentOrder.add(component.name);

        component.setTopLeftPos(0, lastHeight);
        component.setBottomRightPos(-ThemeManager.currentTheme.scrollbarThickness, lastHeight + component.height);
        component.setTopLeftAnchor(0, 0);
        component.setBottomRightAnchor(1, 0);

        heightOfAllChildren = getHeightOfChildren();
    }

    @Override
    public void addChildComponent(@NotNull Component component, int index) {
        super.addChildComponent(component, index);
        component.preDraw();

        double lastHeight = getHeightOfChildren(index);

        childComponentOrder.add(index, component.name);

        component.setTopLeftPos(0, lastHeight);
        component.setBottomRightPos(-ThemeManager.currentTheme.scrollbarThickness, lastHeight + component.height);
        component.setTopLeftAnchor(0, 0);
        component.setBottomRightAnchor(1, 0);

        for (int i = index + 1; i < childComponentOrder.size(); i++) {
            Component iterComponent = childComponents.get(childComponentOrder.get(i));
            //noinspection ConstantConditions
            iterComponent.setTopLeftPos(0, iterComponent.topLeftPos.y + component.height + childComponentSpacing);
            //noinspection ConstantConditions
            iterComponent.setBottomRightPos(-ThemeManager.currentTheme.scrollbarThickness, iterComponent.bottomRightPos.y + component.height + childComponentSpacing);
        }

        heightOfAllChildren = getHeightOfChildren();
    }

    public void setChildComponentSpacing(double childComponentSpacing) {
        this.childComponentSpacing = childComponentSpacing;
    }
}
