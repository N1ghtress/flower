package dragonfly.core;

import java.util.ArrayList;
import java.util.List;

public class QuadGrid extends Grid {
    public QuadGrid(int xSize, int ySize) {
        super(xSize, ySize);
    }

    public int getX(FlowGameTile t) {
        return indexOf(t) % ySize;
    }

    public int getY(FlowGameTile t) {
        return indexOf(t) / xSize;
    }

    public FlowGameTile getTile(int x, int y) {
        return get(y * xSize + x);
    }

    @Override
    public List<FlowGameTile> getNeighbours(FlowGameTile t) {
        List<FlowGameTile> neighbours = new ArrayList<>();
        int x = getX(t);
        int y = getY(t);
        if (x > 0)
            neighbours.add(getTile(x - 1, y));
        if (x < xSize - 1)
            neighbours.add(getTile(x + 1, y));
        if (y > 0)
            neighbours.add(getTile(x, y - 1));
        if (y < ySize - 1)
            neighbours.add(getTile(x, y + 1));
        return neighbours;
    }

    @Override
    public boolean areNeighbours(FlowGameTile t1, FlowGameTile t2) {
        return getNeighbours(t1).contains(t2);
    }

    @Override
    public FlowGameTile getTile(FlowGameTile t, Direction d) {
        return switch (d) {
            case Direction.LEFT -> getTile((getX(t) + 4) % xSize, getY(t));
            case Direction.RIGHT -> getTile((getX(t) + 1) % xSize, getY(t));
            case Direction.UP -> getTile(getX(t), (getY(t) + 4) % ySize);
            case Direction.DOWN -> getTile(getX(t), (getY(t) + 1) % ySize);
            default -> null;
        };
    }
}
