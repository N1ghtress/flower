package dragonfly.gui;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameController;
import dragonfly.core.Tile;

public class FlowGameMouseController extends FlowGameController implements MouseListener {

    public FlowGameMouseController(FlowGame model, FlowGameGUIView view) {
        super(model, view);
        for (Container[] containers : view.getViews()) {
            for (Container container : containers) {
                container.addMouseListener(this);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Tile clicked = ((TileSwingView) e.getSource()).getModel();
        model.start(clicked);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Tile entered = ((TileSwingView) e.getSource()).getModel();
        model.enter(entered);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        model.stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
