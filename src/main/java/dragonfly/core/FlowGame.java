package dragonfly.core;

import java.lang.Exception;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collection;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class FlowGame {
    private Grid tiles;
    private List<List<FlowGameTile>> paths;
    private List<FlowGameTile> path;

    public Grid getTiles() {
        return tiles;
    }

    public FlowGame(int xSize, int ySize) {
        this.tiles = new QuadGrid(xSize, ySize);
        this.paths = new ArrayList<>();
        this.paths.add(new ArrayList<>());
        this.path = paths.getLast();

        for (int i = 0; i < xSize * ySize; i++) {
            FlowGameTile t = new FlowGameNormalTile(this);
            this.tiles.add(t);
        }

        this.tiles.set(0, new FlowGameSymbolTile(this, 1));
        this.tiles.set(this.tiles.size() - 1, new FlowGameSymbolTile(this, 1));
    }

    public FlowGame(String filepath) {
        try {
            String read;
            FileInputStream fis = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            Map<Integer, Integer> symbols = new HashMap<Integer, Integer>();

            read = br.readLine();
            String[] dimensions = read.split("x");
            this.tiles = new QuadGrid(Integer.valueOf(dimensions[0]), Integer.valueOf(dimensions[1]));
            this.paths = new ArrayList<>();
            this.paths.add(new ArrayList<>());
            this.path = paths.getLast();

            while ((read = br.readLine()) != null) {
                for (int i = 0; i < read.length(); i++) {
                    int value = Character.getNumericValue(read.charAt(i));
                    if (value != 0) {
                        tiles.add(new FlowGameSymbolTile(this, value));
                        symbols.merge(value, 1, (v1, v2) -> v1 + v2);
                    } else
                        tiles.add(new FlowGameNormalTile(this));
                }
            }

            symbols.values().forEach(i -> {
                try {
                    if (i != 2)
                        throw new Exception("Invalid symbol count: " + i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasPath(FlowGameTile t) {
        return paths.stream()
                .flatMap(Collection::stream)
                .anyMatch(e -> e == t);
    }

    public List<FlowGameTile> getGamePath(FlowGameTile t) {
        return paths
                .stream()
                .filter(e -> e.contains(t))
                .findFirst()
                .orElse(null);
    }

    public void start(FlowGameTile t) {
        if (hasPath(t)) {
            List<FlowGameTile> path = getGamePath(t);
            paths.remove(path);
            path.stream().forEach(e -> e.reset());
        }

        if (t.isSymbol()) {
            path.add(t);
        }
    }

    public boolean isValidNext(FlowGameTile next, FlowGameTile last) {
        return next.isEntrable(path.get(0));
    }

    public void removeLast() {
        FlowGameTile last = path.getLast();
        if (!last.isSymbol())
            path.getLast().setPath(Path.EMPTY);
        path.removeLast();
        FlowGameTile tile = path.getLast();
        if (path.size() > 1)
            path.get(path.size() - 2).setFollowPath(tile);
        else
            tile.reset();
    }

    public void addToPath(FlowGameTile tile) {
        this.path.add(tile);
        tile.setValue(path.getFirst().getValue());

        FlowGameTile prev = this.path.get(this.path.size() - 2);
        prev.setFollowPath(tile);
        if (tile.isSymbol())
            this.resolvePath();
    }

    public void enter(FlowGameTile next) {
        if (path.size() == 0)
            return;

        FlowGameTile last = path.getLast();
        FlowGameTile prev = path.size() >= 2 ? path.get(path.size() - 2) : null;

        if (next.equals(prev)) {
            removeLast();
        } else if (next.isEntrable(last)) {
            addToPath(next);
        }
    }

    public void stop() {
        if (!(path.size() == 0)) {
            path.stream().forEach(t -> t.reset());
            paths.removeLast();
            paths.add(new ArrayList<>());
            path = paths.getLast();
        }
    }

    public void resolvePath() {
        paths.add(new ArrayList<>());
        path = paths.getLast();
        if (this.isFull())
            endGame();
    }

    public boolean isFull() {
        return paths.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList())
                .size() == this.tiles.size();
    }

    // TODO: This doesn't follow MVC at all, fix !
    public void endGame() {
        System.out.println("Congratz, you won !");
        System.exit(0);
    }
}
