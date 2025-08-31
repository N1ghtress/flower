package dragonfly.core;

import java.util.Arrays;
import java.util.List;

public enum Path {
    EMPTY,
    SYMBOL,
    LEFT,
    RIGHT,
    TOP,
    BOTTOM,
    HORIZONTAL,
    VERTICAL,
    LEFT_TO_BOTTOM,
    RIGHT_TO_TOP,
    LEFT_TO_TOP,
    RIGHT_TO_BOTTOM,
    SYMBOL_LEFT,
    SYMBOL_RIGHT,
    SYMBOL_TOP,
    SYMBOL_BOTTOM;

    private static final List<Path> SYMBOLS = Arrays.asList(Path.SYMBOL, Path.SYMBOL_LEFT, Path.SYMBOL_TOP,
            Path.SYMBOL_RIGHT, Path.SYMBOL_BOTTOM);

    private static final List<Path> DIRECTIONS = Arrays.asList(Path.LEFT, Path.TOP, Path.RIGHT, Path.BOTTOM);

    private static final List<Path> COMPLETES = Arrays.asList(Path.SYMBOL_LEFT, Path.SYMBOL_TOP,
            Path.SYMBOL_RIGHT, Path.SYMBOL_BOTTOM, Path.LEFT_TO_TOP, Path.LEFT_TO_BOTTOM, Path.RIGHT_TO_TOP,
            Path.RIGHT_TO_BOTTOM, Path.HORIZONTAL, Path.VERTICAL);

    public Path opposite() {
        int index = this.ordinal() + (this.ordinal() % 2 == 0 ? 1 : -1);
        return Path.values()[index];
    }

    public boolean isSymbol() {
        return SYMBOLS.contains(this);
    }

    public boolean isDirection() {
        return DIRECTIONS.contains(this);
    }

    public boolean isComplete() {
        return COMPLETES.contains(this);
    }

    /**
     * @param p1 should be either a SYMBOL, or a DIRECTION.
     * @param p2 should be a DIRECTION.
     * @return path that will replace p1 in a tile followed by newly created path.
     */
    public static Path combine(Path p1, Path p2) {
        assert p1.isDirection() || p1.isSymbol()
                : "Invalid p1: " + p1 + ", should be a DIRECTION or a SYMBOL.";
        assert p2.isDirection() : "Invalid p2: " + p2 + ", should be a DIRECTION.";

        if (p1 == SYMBOL && p2 == Path.LEFT)
            return Path.SYMBOL_RIGHT;
        else if (p1 == SYMBOL && p2 == Path.RIGHT)
            return Path.SYMBOL_LEFT;
        else if (p1 == SYMBOL && p2 == Path.TOP)
            return Path.SYMBOL_BOTTOM;
        else if (p1 == SYMBOL && p2 == Path.BOTTOM)
            return Path.SYMBOL_TOP;
        else if (p1 == Path.TOP && p2 == Path.TOP)
            return Path.VERTICAL;
        else if (p1 == Path.LEFT && p2 == Path.LEFT)
            return Path.HORIZONTAL;
        else if (p1 == Path.LEFT && p2 == TOP)
            return Path.LEFT_TO_BOTTOM;
        else if (p1 == Path.LEFT && p2 == Path.BOTTOM)
            return Path.LEFT_TO_TOP;
        else if (p1 == Path.BOTTOM && p2 == Path.LEFT)
            return Path.RIGHT_TO_BOTTOM;
        else if (p1 == Path.TOP && p2 == Path.LEFT)
            return Path.RIGHT_TO_TOP;
        else
            // Here, combine(p1, p2) == combine(p2.opposite(), p1.opposite()), do the
            // drawing if you're not convinced.
            return combine(p2.opposite(), p1.opposite());

    }
}
