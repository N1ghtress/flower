package dragonfly;

import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameController;
import dragonfly.gui.FlowGameGUIView;
import dragonfly.gui.FlowGameMouseController;
import dragonfly.gui.FlowGameSwingView;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        FlowGame model = new FlowGame("./levels/level1.flw");
        FlowGameGUIView view = new FlowGameSwingView(model);
        FlowGameController controller = new FlowGameMouseController(model, view);
    }
}
