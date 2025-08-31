package dragonfly.core;

import java.util.List;
import java.util.Observable;

public class Tile extends Observable {
    private static int nextSymbolValue = 1;
    private FlowGame game;
    private int x;
    private int y;
    private Path path;
    private Type type;
    private int value;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
        setChanged();
        notifyObservers();
    }

    public Type getType() {
        return type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Tile(FlowGame game, int x, int y, Path path, Type type, int value) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.path = path;
        this.type = type;
        this.value = value;
        if (type == Type.SYMBOL)
            Tile.nextSymbolValue++;
    }

    public void changeInto(Type type) {
        switch (type) {
            case Type.NORMAL -> {
                path = Path.EMPTY;
                type = Type.NORMAL;
            }
            case Type.SYMBOL -> {
                path = Path.SYMBOL;
                type = Type.SYMBOL;
                value = ++Tile.nextSymbolValue / 2;
            }
        }
    }

    public boolean isEntrable(Tile other) {
        return (type == Type.SYMBOL && value == other.getValue() && this != other) || path == Path.EMPTY;
    }

    public static void setFollowingType(Tile followee, Tile follower) {
        Path followeeType;
        Path followerType;

        int dx = followee.getX() - follower.getX();
        int dy = followee.getY() - follower.getY();

        followerType = dx == 0
                ? dy == 1 ? Path.TOP : Path.BOTTOM
                : dx == 1 ? Path.LEFT : Path.RIGHT;

        // TODO: Determine followeeType
        follower.setPath(followerType);
        followee.setPath(Path.EMPTY);
    }

    public Path getCurrentType() {
        if (this.isSymbol())
            return this.path;

        List<Tile> path = this.getGamePath();
        Tile previousTile = path.get(path.size() - 2);

        int deltaX = this.getX() - previousTile.getX();
        int deltaY = this.getY() - previousTile.getY();

        return deltaX == 0
                ? deltaY == 1 ? Path.TOP : Path.BOTTOM
                : deltaX == 1 ? Path.LEFT : Path.RIGHT;
    }

    public Path getPreviousType() {
        List<Tile> path = this.getGamePath();
        Tile prev = path.get(path.size() - 2);
        Path prevType = prev.path;

        if (prev.isSymbol())
            return prevType;

        Tile befPrev = path.get(path.size() - 3);

        int deltaX = this.getX() - prev.getX();
        int deltaY = this.getY() - prev.getY();

        if (deltaX == 0)
            prevType = Path.HORIZONTAL;
        if (deltaY == 0)
            prevType = Path.VERTICAL;

        int prevDeltaX = prev.getX() - befPrev.getX();
        int prevDeltaY = prev.getY() - befPrev.getY();

        if ((deltaX == -1 && prevDeltaY == -1) || (deltaY == 1 && prevDeltaX == 1))
            prevType = Path.LEFT_TO_BOTTOM;
        else if ((deltaX == -1 && prevDeltaY == 1) || (deltaY == -1 && prevDeltaX == 1))
            prevType = Path.LEFT_TO_TOP;
        else if ((deltaX == 1 && prevDeltaY == -1) || (deltaY == 1 && prevDeltaX == -1))
            prevType = Path.RIGHT_TO_BOTTOM;
        else if ((deltaX == 1 && prevDeltaY == 1) || (deltaY == -1 && prevDeltaX == -1))
            prevType = Path.RIGHT_TO_TOP;

        return prevType;
    }

    public boolean hasPath() {
        return game.tilesInPath().contains(this);
    }

    public List<Tile> getGamePath() {
        if (game.getPath().contains(this))
            return game.getPath();
        return game.getPaths()
                .stream()
                .filter(e -> e.contains(this))
                .findFirst()
                .orElse(null);
    }

    public boolean isEmpty() {
        return this.path == Path.EMPTY;
    }

    public boolean isSymbol() {
        return this.type == Type.SYMBOL;
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
