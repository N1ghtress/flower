package dragonfly.core;

import java.util.ArrayList;
import java.util.List;

public abstract class Grid extends ArrayList<FlowGameTile> {

    protected final int xSize;
    protected final int ySize;

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public Grid(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public abstract int getX(FlowGameTile t);

    public abstract int getY(FlowGameTile t);

    public abstract FlowGameTile getTile(int x, int y);

    public abstract List<FlowGameTile> getNeighbours(FlowGameTile t);

    public abstract boolean areNeighbours(FlowGameTile t1, FlowGameTile t2);
}
