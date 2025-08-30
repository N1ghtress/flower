package dragonfly.gui;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

import dragonfly.core.FlowGame;

public class FlowGameSwingView extends FlowGameGUIView {
    protected final JFrame frame;

    public FlowGameSwingView(FlowGame model) {
        super(model);
        this.frame = new JFrame("Flow Game");
        int cols = model.getxSize();
        int rows = model.getySize();

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(rows, cols));
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                this.views[y][x] = new TileSwingView(this.model.getTile(x, y));
                contentPane.add(views[y][x]);
            }
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
