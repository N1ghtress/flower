package dragonfly;

import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameController;
import dragonfly.gui.FlowGameMouse;
import dragonfly.gui.FlowGameGUI;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        FlowGame model = new FlowGame("./levels/level1.flw");
        FlowGameGUI view = new FlowGameGUI(model);
        FlowGameController controller = new FlowGameMouse(model, view);
    }
}
