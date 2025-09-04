package dragonfly.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Grid extends ArrayList<FlowGameTile> {

    protected final int xSize;
    protected final int ySize;

    public Grid(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public abstract int getX(FlowGameTile t);

    public abstract int getY(FlowGameTile t);

    public abstract FlowGameTile getTile(int x, int y);

    public abstract FlowGameTile getTile(FlowGameTile t, Direction d);

    public abstract List<FlowGameTile> getNeighbours(FlowGameTile t);

    public abstract boolean areNeighbours(FlowGameTile t1, FlowGameTile t2);
}
