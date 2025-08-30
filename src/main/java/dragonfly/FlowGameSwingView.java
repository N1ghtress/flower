package dragonfly;

import javax.swing.JFrame;
import java.awt.Container;
import java.awt.GridLayout;

public class FlowGameSwingView extends JFrame {
    private FlowGame model;
    private Container[][] views;

    public FlowGameSwingView(FlowGame model, FlowGameMouseController controller) {
        this.model = model;
        int cols = this.model.getxSize();
        int rows = this.model.getySize();

        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(rows, cols));
        views = new TileSwingView[rows][cols];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                views[y][x] = new TileSwingView(this.model.getTile(x, y));
                contentPane.add(views[y][x]);

                views[y][x].addMouseListener(controller);
            }
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setContentPane(contentPane);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
