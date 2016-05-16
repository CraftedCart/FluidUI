package io.github.craftedcart.fluidui.component;

import io.github.craftedcart.fluidui.plugin.AbstractComponentPlugin;
import io.github.craftedcart.fluidui.util.AnchorPoint;
import io.github.craftedcart.fluidui.util.PosXY;
import io.github.craftedcart.fluidui.uiaction.UIAction;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author CraftedCart
 * Created on 03/03/2016 (DD/MM/YYYY)
 */
public class Component {

    @NotNull public ListOrderedMap<String, Component> childComponents = new ListOrderedMap<String, Component>();
    @Nullable public Component parentComponent;
    @NotNull public List<AbstractComponentPlugin> plugins = new ArrayList<AbstractComponentPlugin>();

    @NotNull public String name = "";

    public boolean visible = true;

    /**
     * The top left point of the component, relative to the {@link Component#topLeftAnchor}
     */
    @Nullable public PosXY topLeftPos;
    /**
     * The bottom right point of the component, relative to the {@link Component#bottomRightAnchor}
     */
    @Nullable public PosXY bottomRightPos;
    /**
     * The x and y percentage along the parent component that the {@link Component#topLeftPos} should be given relative to
     */
    @Nullable public AnchorPoint topLeftAnchor = new AnchorPoint(0, 0);
    /**
     * The x and y percentage along the parent component that the {@link Component#bottomRightPos} should be given relative to
     */
    @Nullable public AnchorPoint bottomRightAnchor = new AnchorPoint(0, 0);
    /**
     * The top left point of the component, given as a coordinate on the screen
     */
    @NotNull public PosXY topLeftPx = new PosXY(0, 0);
    /**
     * The bottom right point of the component, given as a coordinate on the screen
     */
    @NotNull public PosXY bottomRightPx = new PosXY(0, 0);
    /**
     * The width of this component in pixels
     */
    public double width;
    /**
     * The height of this component in pixels
     */
    public double height;

    @Nullable public PosXY mousePos;
    public boolean mouseOver = false;

    @Nullable public UIAction onInitAction;
    @Nullable public UIAction onLMBAction;

    /**
     * Other components should call this after init()
     */
    public void postInit() {
        if (onInitAction != null) {
            onInitAction.execute();
        }

        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onPostInit();
        }
    }

    public void draw() {
        preDraw();
        postDraw();

    }

    public void preDraw() {
        if (topLeftPos != null && bottomRightPos != null &&
                topLeftAnchor != null && bottomRightAnchor != null &&
                parentComponent != null) {
            topLeftPx = new PosXY(
                    parentComponent.topLeftPx.x + parentComponent.width * topLeftAnchor.xPercent + topLeftPos.x,
                    parentComponent.topLeftPx.y + parentComponent.height * topLeftAnchor.yPercent + topLeftPos.y);
            bottomRightPx = new PosXY(
                    parentComponent.topLeftPx.x + parentComponent.width * bottomRightAnchor.xPercent + bottomRightPos.x,
                    parentComponent.topLeftPx.y + parentComponent.height * bottomRightAnchor.yPercent + bottomRightPos.y);
        }

        if (topLeftPos != null && bottomRightPos != null) {
            width = bottomRightPx.x - topLeftPx.x;
            height = bottomRightPx.y - topLeftPx.y;
        }

        if (parentComponent != null && parentComponent.mousePos != null) {
            getParentMousePos();
        }

        mouseOver = checkMouseOver();

        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onPreDraw();
        }
    }

    public void postDraw() {
        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onPostDraw();
        }

        for (Map.Entry<String, Component> entry : childComponents.entrySet()) {
            Component childComponent = entry.getValue();
            if (childComponent.isVisible()) {
                childComponent.draw();
            }
        }
    }

    protected void getParentMousePos() {
        assert parentComponent != null;
        mousePos = parentComponent.mousePos;
    }

    @Nullable
    public Component getChildComponentByName(String name) {
        if (childComponents.containsKey(name)) {
            return childComponents.get(name);
        } else {
            return null;
        }
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setParentComponent(@Nullable Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    /**
     * Add a component and set that component's name
     *
     * @param name The name of the component to be added
     * @param component The component to be added
     */
    public void addChildComponent(@NotNull String name, @NotNull Component component) {
        component.setName(name);
        addChildComponent(component);
    }

    /**
     * Add a component at a specific position and set that component's name
     *
     * @param name The name of the component to be added
     * @param component The component to be added
     * @param index The layer position to add the component at
     */
    public void addChildComponent(@NotNull String name, @NotNull Component component, int index) {
        component.setName(name);
        addChildComponent(component, index);
    }

    /**
     * Add a component under the name that has been set for the child component
     *
     * @param component The component to be added
     */
    public void addChildComponent(@NotNull Component component) {
        childComponents.put(component.name, component);
        component.setParentComponent(this);
        component.postInit();
    }

    /**
     * Add a component at a specific position under the name that has been set for the child component
     *
     * @param component The component to be added
     * @param index The layer position to add the component at
     */
    public void addChildComponent(@NotNull Component component, int index) {
        childComponents.put(index, component.name, component);
        component.setParentComponent(this);
        component.postInit();
    }

    /**
     * @return The number of child components that this component has
     */
    public int getChildComponentCount() {
        return childComponents.size();
    }

    public void setTopLeftPos(@Nullable PosXY topLeftPos) {
        this.topLeftPos = topLeftPos;

        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onTopLeftPosChanged(topLeftPos);
        }
    }

    public void setTopLeftPos(double x, double y) {
        setTopLeftPos(new PosXY(x, y));
    }

    public void setBottomRightPos(@Nullable PosXY bottomRightPos) {
        this.bottomRightPos = bottomRightPos;
        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onBottomRightPosChanged(bottomRightPos);
        }
    }

    public void setBottomRightPos(double x, double y) {
        setBottomRightPos(new PosXY(x, y));
    }

    public void setTopLeftAnchor(@Nullable AnchorPoint topLeftAnchor) {
        this.topLeftAnchor = topLeftAnchor;

        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onTopLeftAnchorChanged(topLeftAnchor);
        }
    }

    public void setTopLeftAnchor(double xPercent, double yPercent) {
        setTopLeftAnchor(new AnchorPoint(xPercent, yPercent));
    }

    public void setBottomRightAnchor(@Nullable AnchorPoint bottomRightAnchor) {
        this.bottomRightAnchor = bottomRightAnchor;

        for (AbstractComponentPlugin plugin : plugins) {
            plugin.onBottomRightAnchorChanged(bottomRightAnchor);
        }
    }

    public void setBottomRightAnchor(double xPercent, double yPercent) {
        setBottomRightAnchor(new AnchorPoint(xPercent, yPercent));
    }

    protected boolean checkMouseOver() {
        FloatBuffer mat = BufferUtils.createFloatBuffer(16);
        GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, mat);

        return mousePos != null && topLeftPos != null && bottomRightPos != null &&
                mousePos.x > topLeftPx.x + mat.get(12) && mousePos.y > topLeftPx.y + mat.get(13) &&
                mousePos.x <= bottomRightPx.x + mat.get(12) && mousePos.y <= bottomRightPx.y + mat.get(13) &&
                (parentComponent == null || parentComponent.mouseOver);
    }

    /**
     * @param onInitAction Put your initialization code for the component in a {@link UIAction} in here
     */
    public void setOnInitAction(@Nullable UIAction onInitAction) {
        this.onInitAction = onInitAction;
    }

    /**
     * @param onLMBAction The execute() method of the UIAction will be called if this component is clicked on with the left mouse button
     */
    public void setOnLMBAction(@Nullable UIAction onLMBAction) {
        this.onLMBAction = onLMBAction;
    }

    public void onClick(int button, PosXY mousePos) {
        onClickAnywhere(button, mousePos);

        boolean hitChildComponent = false;
        for (Map.Entry<String, Component> entry : childComponents.entrySet()) {
            Component childComponent = entry.getValue();

            if (childComponent.mouseOver) {
                hitChildComponent = true;
                childComponent.onClick(button, mousePos);
            }
        }

        if (!hitChildComponent) {
            for (AbstractComponentPlugin plugin : plugins) {
                plugin.onClick(button, mousePos);
            }

            if (onLMBAction != null) {
                onLMBAction.execute();
            }
        }
    }

    public void onClickAnywhere(int button, PosXY mousePos) {
        for (Map.Entry<String, Component> entry : childComponents.entrySet()) {
            Component childComponent = entry.getValue();
            childComponent.onClickAnywhere(button, mousePos);
        }
    }

    public void onKey(int key, char keyChar) {
        for (Map.Entry<String, Component> entry : childComponents.entrySet()) {
            Component childComponent = entry.getValue();
            childComponent.onKey(key, keyChar);
        }
    }

    public void addPlugin(AbstractComponentPlugin plugin) {
        plugin.linkedComponent = this;
        plugins.add(plugin);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible() {
        return visible;
    }

}
