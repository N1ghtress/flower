package dragonfly.core;

public enum TileType {
    EMPTY(0, true),
    S1(1, true),
    S2(2, true),
    S3(3, true),
    S4(4, true),
    S5(6, true),
    HORIZONTAL(0, false),
    VERTICAL(0, false),
    LEFT(0, false),
    RIGHT(0, false),
    TOP(0, false),
    BOTTOM(0, false),
    LEFT_TO_TOP(0, false),
    LEFT_TO_BOTTOM(0, false),
    RIGHT_TO_TOP(0, false),
    RIGHT_TO_BOTTOM(0, false);

    private int ordinal = -1;
    private boolean entrable = false;

    public int getOrdinal() {
        return ordinal;
    }

    TileType(int ordinal, boolean entrable) {
        this.ordinal = ordinal;
        this.entrable = entrable;
    }

    public boolean isEntrable(TileType o) {
        if (this.isSymbol())
            return this == o;
        return this.entrable;
    }

    public boolean isSymbol() {
        return this.ordinal > 0;
    }

    public String toString() {
        return this.name() + ": " + this.ordinal + " " + this.entrable;
    }
}
