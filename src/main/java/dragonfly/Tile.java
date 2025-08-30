package dragonfly;

import java.util.List;
import java.util.Observable;

public class Tile extends Observable {
    private FlowGame game;
    private int x;
    private int y;
    private TileType type;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
        setChanged();
        notifyObservers();
    }

    public Tile(FlowGame game, int x, int y, TileType type) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public static void setFollowingType(Tile followee, Tile follower) {
        TileType followeeType;
        TileType followerType;

        int dx = followee.getX() - follower.getX();
        int dy = followee.getY() - follower.getY();

        followerType = dx == 0
                ? dy == 1 ? TileType.TOP : TileType.BOTTOM
                : dx == 1 ? TileType.LEFT : TileType.RIGHT;

        // TODO: Determine followeeType
        follower.setType(followerType);
        followee.setType(TileType.EMPTY);
    }

    public TileType getCurrentType() {
        if (this.isSymbol())
            return this.type;

        List<Tile> path = this.getPath();
        Tile previousTile = path.get(path.size() - 2);

        int deltaX = this.getX() - previousTile.getX();
        int deltaY = this.getY() - previousTile.getY();

        return deltaX == 0
                ? deltaY == 1 ? TileType.TOP : TileType.BOTTOM
                : deltaX == 1 ? TileType.LEFT : TileType.RIGHT;
    }

    public TileType getPreviousType() {
        List<Tile> path = this.getPath();
        Tile prev = path.get(path.size() - 2);
        TileType prevType = prev.type;

        if (prev.isSymbol())
            return prevType;

        Tile befPrev = path.get(path.size() - 3);

        int deltaX = this.getX() - prev.getX();
        int deltaY = this.getY() - prev.getY();

        if (deltaX == 0)
            prevType = TileType.HORIZONTAL;
        if (deltaY == 0)
            prevType = TileType.VERTICAL;

        int prevDeltaX = prev.getX() - befPrev.getX();
        int prevDeltaY = prev.getY() - befPrev.getY();

        if ((deltaX == -1 && prevDeltaY == -1) || (deltaY == 1 && prevDeltaX == 1))
            prevType = TileType.LEFT_TO_BOTTOM;
        else if ((deltaX == -1 && prevDeltaY == 1) || (deltaY == -1 && prevDeltaX == 1))
            prevType = TileType.LEFT_TO_TOP;
        else if ((deltaX == 1 && prevDeltaY == -1) || (deltaY == 1 && prevDeltaX == -1))
            prevType = TileType.RIGHT_TO_BOTTOM;
        else if ((deltaX == 1 && prevDeltaY == 1) || (deltaY == -1 && prevDeltaX == -1))
            prevType = TileType.RIGHT_TO_TOP;

        return prevType;
    }

    public boolean hasPath() {
        return game.tilesInPath().contains(this);
    }

    public List<Tile> getPath() {
        if (game.getPath().contains(this))
            return game.getPath();
        return game.getPaths()
                .stream()
                .filter(e -> e.contains(this))
                .findFirst()
                .orElse(null);
    }

    public boolean isEmpty() {
        return this.getType() == TileType.EMPTY;
    }

    public boolean isSymbol() {
        return type.isSymbol();
    }

    public boolean isNeighbourWith(Tile tile) {
        return Math.abs(x - tile.getX()) + Math.abs(y - tile.getY()) == 1;
    }

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null)
            return false;
        if (getClass() != o.getClass())
            return false;
        Tile other = (Tile) o;
        return x == other.getX() && y == other.getY();
    }

    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
