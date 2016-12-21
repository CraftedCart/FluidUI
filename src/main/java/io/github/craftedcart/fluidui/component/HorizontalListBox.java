package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.plugin.AbstractComponentPlugin;
import io.github.craftedcart.fluidui.theme.UITheme;
import io.github.craftedcart.fluidui.util.MathUtils;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.util.UIColor;
import io.github.craftedcart.fluidui.util.UIUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CraftedCart
 * Created on 26/07/2016 (DD/MM/YYYY)
 */
public class HorizontalListBox extends Panel {

    public List<String> childComponentOrder = new ArrayList<String>();
    public double childComponentSpacing = 2;
    public double scrollOffset = 0;
    public double smoothedScrollOffset = 0;
    public double widthOfAllChildren = 0;

    public double scrollbarThickness;
    public UIColor scrollbarBG;
    public UIColor scrollbarFG;
    public double mouseSensitivity;
    public double scrollSmoothing;

    public HorizontalListBox() {
        init();
        postInit();
    }

    @Override
    public void setTheme(@NotNull UITheme theme) {
        super.setTheme(theme);

        backgroundColor = theme.listBoxBackgroundColor;
        scrollbarThickness = theme.scrollbarThickness;
        scrollbarBG = theme.scrollbarBG;
        scrollbarFG = theme.scrollbarFG;
        mouseSensitivity = theme.mouseSensitivity;
        scrollSmoothing = theme.scrollSmoothing;
    }

    public void init() {
        if (parentComponent != null) {
            setTheme(parentComponent.theme);
        }
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

    private void detectScroll() {
        if (mouseOver) {
            scrollOffset += UIUtils.getMouseDWheel() * mouseSensitivity;
        }

        if (widthOfAllChildren > width) {
            scrollOffset = MathUtils.clamp(scrollOffset, -(widthOfAllChildren - width), 0);
        } else {
            scrollOffset = 0;
        }

        smoothedScrollOffset = MathUtils.lerp(smoothedScrollOffset, scrollOffset, Math.min(UIUtils.getDelta() * scrollSmoothing, 1));
    }

    public double getSizeOfChildren(int maxComponentIndex) {
        double childrenWidth = 0;
        for (int i = 0; i < maxComponentIndex; i++) { //Loop over components
            Component component = childComponents.get(childComponentOrder.get(i));
            if (component.isVisible()) { //If the component is visible
                childrenWidth += component.width + childComponentSpacing; //Add the height
            }
        }
        return childrenWidth;
    }

    public double getSizeOfChildren() {
        return getSizeOfChildren(childComponentOrder.size());
    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public void postDraw() {
        GL11.glPushMatrix();
        GL11.glTranslated(smoothedScrollOffset, 0, 0);

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

        checkDrawTooltips();

        GL11.glPopMatrix();

        //Scroll bar
        UIUtils.drawQuad(new PosXY(topLeftPx.x, bottomRightPx.y - scrollbarThickness), bottomRightPx, scrollbarBG);
        double scrollPercent = smoothedScrollOffset / (widthOfAllChildren - width);
        double scrollbarHeight = Math.min(Math.max(24, width / widthOfAllChildren * width), width);
        UIUtils.drawQuad(new PosXY(topLeftPx.x, bottomRightPx.y  - scrollbarThickness - (width - scrollbarHeight) * scrollPercent),
                new PosXY(topLeftPx.x - (width - scrollbarHeight) * scrollPercent + scrollbarHeight, bottomRightPx.y), scrollbarFG);
    }

    @Override
    public void addChildComponent(@NotNull Component component) {
        super.addChildComponent(component);
        component.preDraw();

        double lastWidth = widthOfAllChildren;

        childComponentOrder.add(component.name);

        component.setTopLeftPos(lastWidth, 0);
        component.setBottomRightPos(lastWidth + component.width, -scrollbarThickness);
        component.setTopLeftAnchor(0, 0);
        component.setBottomRightAnchor(0, 1);

        widthOfAllChildren = getSizeOfChildren();
    }

    @Override
    public void addChildComponent(@NotNull Component component, int index) {
        super.addChildComponent(component, index);
        component.preDraw();

        double lastWidth = getSizeOfChildren(index);

        childComponentOrder.add(index, component.name);

        component.setTopLeftPos(lastWidth, 0);
        component.setBottomRightPos(lastWidth + component.width, -scrollbarThickness);
        component.setTopLeftAnchor(0, 0);
        component.setBottomRightAnchor(0, 1);

        for (int i = index + 1; i < childComponentOrder.size(); i++) {
            Component iterComponent = childComponents.get(childComponentOrder.get(i));
            //noinspection ConstantConditions
            iterComponent.setTopLeftPos(iterComponent.topLeftPos.x + component.width + childComponentSpacing, 0);
            //noinspection ConstantConditions
            iterComponent.setBottomRightPos(iterComponent.bottomRightPos.x + component.width + childComponentSpacing, -scrollbarThickness);
        }

        widthOfAllChildren = getSizeOfChildren();
    }

    public void reorganizeChildComponents() {
        double childrenWidth = 0;
        for (int i = 0; i < childComponents.size(); i++) { //Loop over components
            Component component = childComponents.get(childComponentOrder.get(i));
            if (component.isVisible()) { //If the component is visible
                double componentWidth = component.width;

                component.setTopLeftPos(childrenWidth, 0);
                component.setBottomRightPos(childrenWidth + componentWidth, -scrollbarThickness);

                childrenWidth += componentWidth + childComponentSpacing; //Add the height
            }
        }

        widthOfAllChildren = childrenWidth;
    }

    public void setChildComponentSpacing(double childComponentSpacing) {
        this.childComponentSpacing = childComponentSpacing;
    }

}
