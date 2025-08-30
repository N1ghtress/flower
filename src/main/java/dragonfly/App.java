package dragonfly;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        FlowGame model = new FlowGame("./levels/level1.flw");
        FlowGameMouseController controller = new FlowGameMouseController(model);
        FlowGameSwingView view = new FlowGameSwingView(model, controller);
        view.setVisible(true);
    }
}
