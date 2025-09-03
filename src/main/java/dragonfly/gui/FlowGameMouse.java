package dragonfly.gui;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameController;
import dragonfly.core.FlowGameTile;

public class FlowGameMouse extends FlowGameController implements MouseListener {

    public FlowGameMouse(FlowGame model, FlowGameGUI view) {
        super(model, view);
        for (Container[] containers : view.getViews()) {
            for (Container container : containers) {
                container.addMouseListener(this);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        FlowGameTile clicked = ((TileGUIView) e.getSource()).getModel();
        start(clicked);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        FlowGameTile entered = ((TileGUIView) e.getSource()).getModel();
        enter(entered);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        stop();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
