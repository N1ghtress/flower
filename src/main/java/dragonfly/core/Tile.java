package dragonfly.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public class Tile {
    private static int nextSymbolValue = 1;

    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
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

    public void setPath(Path p) {
        Path oldPath = path;
        path = p;
        pcs.firePropertyChange("path", oldPath, path);
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

    public void resetPath() {
        if (isSymbol())
            setPath(Path.SYMBOL);
        else
            setPath(Path.EMPTY);
    }

    public boolean isEntrable(Tile other) {
        return (type == Type.SYMBOL && value == other.getValue() && this != other) || path == Path.EMPTY;
    }

    public void setFollowPath(Tile follower) {
        Path direction;
        int dx = getX() - follower.getX();
        int dy = getY() - follower.getY();
        // Follower path must be TOP, BOTTOM, LEFT, RIGHT or SYMBOL
        direction = dx == 0
                ? dy == -1 ? Path.TOP : Path.BOTTOM
                : dx == -1 ? Path.LEFT : Path.RIGHT;
        Path followerPath = direction;
        if (follower.getPath() == Path.SYMBOL) {
            followerPath = Path.combine(Path.SYMBOL, direction.opposite());
        }
        follower.setPath(followerPath);

        // Should only occur in case of forward
        if (!path.isComplete()) {
            Path followeePath;
            followeePath = Path.combine(getPath(), direction);

            setPath(followeePath);
        }
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
        return "(" + x + "," + y + ") " + path;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }
}
