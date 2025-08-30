package dragonfly;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FlowGameMouseController extends MouseAdapter {

    private FlowGame model;

    public FlowGameMouseController(FlowGame model) {
        this.model = model;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Tile clicked = ((TileSwingView) e.getSource()).getTile();
        model.start(clicked);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Tile entered = ((TileSwingView) e.getSource()).getTile();
        model.enter(entered);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        model.stop();
    }
}
