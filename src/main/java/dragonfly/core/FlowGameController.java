package dragonfly.core;

public abstract class FlowGameController {

    protected final FlowGame model;
    protected final FlowGameView view;

    protected FlowGameController(FlowGame model, FlowGameView view) {
        this.model = model;
        this.view = view;
    }

    protected void start(Tile tile) {
        model.start(tile);
    }

    protected void enter(Tile tile) {
        model.enter(tile);
    }

    protected void stop() {
        model.stop();
    }
}
