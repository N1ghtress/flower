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
        int cols = model.getTiles().getxSize();
        int rows = model.getTiles().getySize();
        this.views = new TileGUIView[rows][cols];
        this.frame = new JFrame("Flow Game");

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new GridLayout(rows, cols));
        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                this.views[x][y] = new TileGUIView(this.model, this.model.getTiles().getTile(x, y));
                contentPane.add(views[x][y]);
            }
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setContentPane(contentPane);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
