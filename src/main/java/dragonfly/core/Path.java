package dragonfly.core;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

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
    RIGHT_TO_BOTTOM;

    private static final List<Path> DIRECTION = Arrays.asList(Path.LEFT, Path.TOP, Path.RIGHT, Path.BOTTOM);

    private static final List<Path> COMPLETE = Arrays.asList(Path.SYMBOL, Path.LEFT_TO_TOP, Path.LEFT_TO_BOTTOM,
            Path.RIGHT_TO_TOP,
            Path.RIGHT_TO_BOTTOM, Path.HORIZONTAL, Path.VERTICAL);

    private Path opposite() {
        int index = this.ordinal() + (this.ordinal() % 2 == 0 ? 1 : -1);
        return Path.values()[index];
    }

    public boolean isComplete() {
        return COMPLETE.contains(this);
    }

    /**
     * @param p1 should be either a SYMBOL, or a DIRECTION.
     * @param p2 should be a DIRECTION.
     * @return path that will replace p1 in a tile followed by newly created path.
     */
    public static Path combine(Path p1, Path p2) {
        assert Path.DIRECTION.contains(p1) || Path.SYMBOL == p1
                : "Invalid p1: " + p1 + ", should be either DIRECTION or SYMBOL.";
        assert Path.DIRECTION.contains(p2) : "Invalid p2: " + p2 + ", should be a DIRECTION.";
        if (p1 == Path.SYMBOL)
            return Path.SYMBOL; // SYMBOL should never change. Ever.
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
