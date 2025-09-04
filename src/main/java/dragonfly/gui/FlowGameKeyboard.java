package dragonfly.gui;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import dragonfly.core.Direction;
import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameController;
import dragonfly.core.FlowGameTile;

public class FlowGameKeyboard extends FlowGameController implements KeyListener {

    public FlowGameKeyboard(FlowGame model, FlowGameGUI view) {
        super(model, view);
        view.frame.addKeyListener(this);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        FlowGameTile tile = model.getCurrent();
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (model.getPath().size() == 0)
                start(model.getCurrent());
            else
                stop();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            enter(model.getTiles().getTile(tile, Direction.LEFT));
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            enter(model.getTiles().getTile(tile, Direction.RIGHT));
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            enter(model.getTiles().getTile(tile, Direction.UP));
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            enter(model.getTiles().getTile(tile, Direction.DOWN));
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
