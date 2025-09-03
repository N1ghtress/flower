package dragonfly.core;

public class FlowGameSymbolTile extends FlowGameTile {
    public FlowGameSymbolTile(FlowGame game, int value) {
        super(game, Path.SYMBOL, value);
    }

    @Override
    public void reset() {
        setPath(Path.SYMBOL);
    }

    @Override
    public boolean isEntrable(FlowGameTile from) {
        FlowGameTile fgt = game.getGamePath(from).getFirst();

        assert fgt instanceof FlowGameSymbolTile : "First tile isn't a symbol " + fgt;

        FlowGameSymbolTile symbol = (FlowGameSymbolTile) fgt;
        boolean sameSymbol = value == symbol.getValue();
        boolean neighbours = game.getTiles().areNeighbours(this, from);
        boolean notInAPath = !game.hasPath(this);
        return sameSymbol && neighbours && notInAPath;
    }
}
