package dragonfly.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JPanel;

import dragonfly.core.FlowGame;
import dragonfly.core.FlowGameTile;
import dragonfly.core.TileView;

public class TileGUIView extends JPanel implements TileView, PropertyChangeListener {
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
    private FlowGame game;
    private FlowGameTile model;

    public FlowGameTile getModel() {
        return model;
    }

    public TileGUIView(FlowGame game, FlowGameTile model) {
        this.game = game;
        this.model = model;
        this.setPreferredSize(new Dimension(INITIAL_WIDTH, INITIAL_HEIGHT));
        this.model.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        repaint();
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

        List<FlowGameTile> path = game.getGamePath(model);
        int index = path == null ? 0 : path.getFirst().getValue();
        g.setColor(COLORS[index]);
        g2d.setStroke(pathStroke);
        switch (model.getPath()) {
            case SYMBOL -> {
                drawSymbol(g, width, height);
            }
            case SYMBOL_LEFT -> {
                drawSymbol(g, width, height);
                g.drawLine(0, height / 2, width / 2, height / 2);
            }
            case SYMBOL_RIGHT -> {
                drawSymbol(g, width, height);
                g.drawLine(width, height / 2, width / 2, height / 2);
            }
            case SYMBOL_TOP -> {
                drawSymbol(g, width, height);
                g.drawLine(width / 2, 0, width / 2, height / 2);
            }
            case SYMBOL_BOTTOM -> {
                drawSymbol(g, width, height);
                g.drawLine(width / 2, height, width / 2, height / 2);
            }
            case LEFT -> {
                g.drawLine(0, height / 2, width / 2, height / 2);
                break;
            }
            case RIGHT -> {
                g.drawLine(width / 2, height / 2, width, height / 2);
                break;
            }
            case TOP -> {
                g.drawLine(width / 2, height / 2, width / 2, 0);
                break;
            }
            case BOTTOM -> {
                g.drawLine(width / 2, height / 2, width / 2, height);
                break;
            }
            case VERTICAL -> {
                g.drawLine(width / 2, height, width / 2, 0);
                break;
            }
            case HORIZONTAL -> {
                g.drawLine(0, height / 2, width, height / 2);
                break;
            }
            case LEFT_TO_TOP -> {
                g.drawLine(0, height / 2, width / 2, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, 0);
                break;
            }
            case LEFT_TO_BOTTOM -> {
                g.drawLine(0, height / 2, width / 2, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, height);
                break;
            }
            case RIGHT_TO_TOP -> {
                g.drawLine(width / 2, height / 2, width, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, 0);
                break;
            }
            case RIGHT_TO_BOTTOM -> {
                g.drawLine(width / 2, height / 2, width, height / 2);
                g.drawLine(width / 2, height / 2, width / 2, height);
                break;
            }
            default -> {
            }
        }
    }

    private void drawSymbol(Graphics g, int width, int height) {
        g.setColor(COLORS[model.getValue()]);
        double upLeft = 0.1;
        double size = 1 - 2 * upLeft;
        g.fillOval((int) (width * upLeft), (int) (height * upLeft), (int) (width * size),
                (int) (height * size));
    }

    public FlowGameTile getmodel() {
        return model;
    }
}
