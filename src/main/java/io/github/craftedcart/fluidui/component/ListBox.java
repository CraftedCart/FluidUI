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
import java.util.HashMap;
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

    public double scrollbarThickness;
    public UIColor scrollbarBG;
    public UIColor scrollbarFG;
    public double mouseSensitivity;
    public double scrollSmoothing;

    public ListBox() {
        init();
        postInit();
    }

    @Override
    public void setParentComponent(@Nullable Component parentComponent) {
        super.setParentComponent(parentComponent);

        if (parentComponent != null) {
            setTheme(parentComponent.theme);
        }
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

    @Override
    public void postDraw() {
        GL11.glPushMatrix();
        GL11.glTranslated(0, smoothedScrollOffset, 0);

        for (AbstractComponentPlugin plugin : plugins) { //Plugins
            plugin.onPostDraw();
        }

        Map<String, Component> childComponentsClone = new HashMap<>(childComponents);

        for (Map.Entry<String, Component> entry : childComponentsClone.entrySet()) { //Draw children
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
        UIUtils.drawQuad(new PosXY(bottomRightPx.x - scrollbarThickness, topLeftPx.y), bottomRightPx, scrollbarBG);
        double scrollPercent = smoothedScrollOffset / (heightOfAllChildren - height);
        double scrollbarHeight = Math.min(Math.max(24, height / heightOfAllChildren * height), height);
        UIUtils.drawQuad(new PosXY(bottomRightPx.x - scrollbarThickness, topLeftPx.y - (height - scrollbarHeight) * scrollPercent),
                new PosXY(bottomRightPx.x, topLeftPx.y - (height - scrollbarHeight) * scrollPercent + scrollbarHeight), scrollbarFG);
    }

    private void detectScroll() {
        if (mouseOver) {
            scrollOffset += UIUtils.getMouseDWheel() * mouseSensitivity;
        }

        if (heightOfAllChildren > height) {
            scrollOffset = MathUtils.clamp(scrollOffset, -(heightOfAllChildren - height), 0);
        } else {
            scrollOffset = 0;
        }

        smoothedScrollOffset = MathUtils.lerp(smoothedScrollOffset, scrollOffset, Math.min(UIUtils.getDelta() * scrollSmoothing, 1));
    }

    public void setBackgroundColor(@NotNull UIColor backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public double getSizeOfChildren(int maxComponentIndex) {
        double childrenHeight = 0;
        for (int i = 0; i < maxComponentIndex; i++) { //Loop over components
            Component component = childComponents.get(childComponentOrder.get(i));
            if (component.isVisible()) { //If the component is visible
                childrenHeight += component.height + childComponentSpacing; //Add the height
            }
        }
        return childrenHeight;
    }

    public double getSizeOfChildren() {
        return getSizeOfChildren(childComponentOrder.size());
    }

    @Override
    public void addChildComponent(@NotNull Component component) {
        super.addChildComponent(component);
        component.preDraw();

        double lastHeight = heightOfAllChildren;

        childComponentOrder.add(component.name);

        component.setTopLeftPos(0, lastHeight);
        component.setBottomRightPos(-scrollbarThickness, lastHeight + component.height);
        component.setTopLeftAnchor(0, 0);
        component.setBottomRightAnchor(1, 0);

        heightOfAllChildren = getSizeOfChildren();
    }

    @Override
    public void addChildComponent(@NotNull Component component, int index) {
        super.addChildComponent(component, index);
        component.preDraw();

        double lastHeight = getSizeOfChildren(index);

        childComponentOrder.add(index, component.name);

        component.setTopLeftPos(0, lastHeight);
        component.setBottomRightPos(-scrollbarThickness, lastHeight + component.height);
        component.setTopLeftAnchor(0, 0);
        component.setBottomRightAnchor(1, 0);

        for (int i = index + 1; i < childComponentOrder.size(); i++) {
            Component iterComponent = childComponents.get(childComponentOrder.get(i));
            //noinspection ConstantConditions
            iterComponent.setTopLeftPos(0, iterComponent.topLeftPos.y + component.height + childComponentSpacing);
            //noinspection ConstantConditions
            iterComponent.setBottomRightPos(-scrollbarThickness, iterComponent.bottomRightPos.y + component.height + childComponentSpacing);
        }

        heightOfAllChildren = getSizeOfChildren();
    }

    public void reorganizeChildComponents() {
        double childrenHeight = 0;
        for (int i = 0; i < childComponents.size(); i++) { //Loop over components
            Component component = childComponents.get(childComponentOrder.get(i));
            if (component.isVisible()) { //If the component is visible
                double componentHeight = component.height;

                component.setTopLeftPos(0, childrenHeight);
                component.setBottomRightPos(-scrollbarThickness, childrenHeight + componentHeight);

                childrenHeight += componentHeight + childComponentSpacing; //Add the height
            }
        }

        heightOfAllChildren = childrenHeight;
    }

    public void setChildComponentSpacing(double childComponentSpacing) {
        this.childComponentSpacing = childComponentSpacing;
    }

    public void clearChildComponents() {
        childComponents.clear();
        childComponentOrder.clear();
        heightOfAllChildren = 0;
    }

}
