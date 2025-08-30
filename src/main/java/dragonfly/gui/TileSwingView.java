package dragonfly.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import dragonfly.core.Tile;
import dragonfly.core.TileType;
import dragonfly.core.TileView;

public class TileSwingView extends JPanel implements TileView, Observer {
    public final static int INITIAL_WIDTH = 80;
    public final static int INITIAL_HEIGHT = 80;
    public final static float HUE_INCREMENT = 0.15f;

    public final static Color[] COLORS = {
            Color.getHSBColor(0, 0, 0.7f),
            Color.getHSBColor(HUE_INCREMENT * 0, 1.0f, 1.0f),
            Color.getHSBColor(HUE_INCREMENT * 1, 1.0f, 1.0f),
            Color.getHSBColor(HUE_INCREMENT * 2, 1.0f, 1.0f),
            Color.getHSBColor(HUE_INCREMENT * 3, 1.0f, 1.0f),
            Color.getHSBColor(HUE_INCREMENT * 4, 1.0f, 1.0f) };

    private BasicStroke gridStroke;
    private BasicStroke pathStroke;
    private Tile model;

    public Tile getModel() {
        return model;
    }

    public TileSwingView(Tile model) {
        this.model = model;
        this.setPreferredSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        this.model.addObserver(this);
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        gridStroke = new BasicStroke((int) (getWidth() * 0.04));
        pathStroke = new BasicStroke((int) (getWidth() * 0.4), BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2d.setStroke(gridStroke);
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);
        g.setColor(COLORS[0]);
        g.drawRect(0, 0, width, height);

        TileType colorType = model.hasPath() ? model.getPath().getFirst().getType() : model.getType();

        g.setColor(COLORS[colorType.getOrdinal()]);
        switch (model.getType()) {
            case S1, S2, S3, S4, S5 -> {
                g.setColor(COLORS[model.getType().getOrdinal()]);
                double upLeft = 0.1;
                double size = 1 - 2 * upLeft;
                g.fillOval((int) (width * upLeft), (int) (height * upLeft), (int) (width * size),
                        (int) (height * size));
            }
            case LEFT_TO_TOP -> {
                g2d.setStroke(pathStroke);
                g.drawLine(0, height / 2, width / 2, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, 0);
                break;
            }
            case LEFT_TO_BOTTOM -> {
                g2d.setStroke(pathStroke);
                g.drawLine(0, height / 2, width / 2, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, height);
                break;
            }
            case RIGHT_TO_TOP -> {
                g2d.setStroke(pathStroke);
                g.drawLine(width / 2, height / 2, width, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, 0);
                break;
            }
            case RIGHT_TO_BOTTOM -> {
                g2d.setStroke(pathStroke);
                g.drawLine(width / 2, height / 2, width, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, height);
                break;
            }
            case LEFT -> {
                g2d.setStroke(pathStroke);
                g.drawLine(0, height / 2, width / 2, height / 2);
                break;
            }
            case RIGHT -> {
                g2d.setStroke(pathStroke);
                g.drawLine(width / 2, height / 2, width, height / 2);
                break;
            }
            case TOP -> {
                g2d.setStroke(pathStroke);
                g.drawLine(width / 2, height / 2, width / 2, 0);
                break;
            }
            case BOTTOM -> {
                g2d.setStroke(pathStroke);
                g.drawLine(width / 2, height / 2, width / 2, height);
                break;
            }
            case HORIZONTAL -> {
                g2d.setStroke(pathStroke);
                g.drawLine(width / 2, height / 2, width / 2, 0);
                g.drawLine(width / 2, height / 2, width / 2, height);
                break;
            }
            case VERTICAL -> {
                g2d.setStroke(pathStroke);
                g.drawLine(width / 2, height / 2, width, height / 2);
                g.drawLine(0, height / 2, width / 2, height / 2);
                break;
            }
            default -> {
            }
        }
    }

    public Tile getmodel() {
        return model;
    }
}
