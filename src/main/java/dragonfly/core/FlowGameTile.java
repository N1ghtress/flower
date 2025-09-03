package dragonfly.core;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class FlowGameTile {
    protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    protected FlowGame game;
    private Path path;
    protected int value;

    public FlowGameTile(FlowGame game, Path p, int value) {
        this.game = game;
        this.path = p;
        this.value = value;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path p) {
        Path oldPath = path;
        path = p;
        pcs.firePropertyChange("path", oldPath, path);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public abstract void reset();

    public abstract boolean isEntrable(FlowGameTile from);

    public void setFollowPath(FlowGameTile follower) {
        Path direction;
        int dx = game.getTiles().getX(this) - game.getTiles().getX(follower);
        int dy = game.getTiles().getY(this) - game.getTiles().getY(follower);
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

    public boolean isEmpty() {
        return this.path == Path.EMPTY;
    }

    public boolean isSymbol() {
        return this instanceof FlowGameSymbolTile;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public String toString() {
        return "(" + game.getTiles().getX(this) + "," + game.getTiles().getY(this) + ")";
    }
}
