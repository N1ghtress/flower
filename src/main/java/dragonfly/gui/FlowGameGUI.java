package dragonfly.gui;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JFrame;

import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameView;

public class FlowGameGUI implements FlowGameView {
    protected final JFrame frame;

    protected final FlowGame model;
    protected final Container[][] views;

    public Container[][] getViews() {
        return views;
    }

    public FlowGameGUI(FlowGame model) {
        this.model = model;
        int cols = model.getxSize();
        int rows = model.getySize();
        this.views = new TileSwingView[rows][cols];
        this.frame = new JFrame("Flow Game");

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
