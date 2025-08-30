package dragonfly.gui;

import java.awt.Container;

import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameView;

public abstract class FlowGameGUIView implements FlowGameView {
    protected final FlowGame model;
    protected final Container[][] views;

    public Container[][] getViews() {
        return views;
    }

    protected FlowGameGUIView(FlowGame model) {
        this.model = model;
        int cols = model.getxSize();
        int rows = model.getySize();
        this.views = new TileSwingView[rows][cols];
    }
}
