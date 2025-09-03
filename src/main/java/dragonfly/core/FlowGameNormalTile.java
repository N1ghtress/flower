package dragonfly.core;

public class FlowGameNormalTile extends FlowGameTile {

    public FlowGameNormalTile(FlowGame game) {
        super(game, Path.EMPTY, 0);
    }

    @Override
    public void reset() {
        setPath(Path.EMPTY);
        value = 0;
    }

    @Override
    public boolean isEntrable(FlowGameTile from) {
        boolean neighbours = game.getTiles().areNeighbours(this, from);
        boolean notInAPath = !game.hasPath(this);
        return neighbours && notInAPath;
    }

}
