package dragonfly.core;

import java.lang.Exception;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.stream.Collectors;

public class FlowGame {

    private int xSize;
    private int ySize;
    private Tile[][] tiles;
    private List<Tile> path;
    private List<List<Tile>> paths;

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public List<Tile> getPath() {
        return path;
    }

    public List<List<Tile>> getPaths() {
        return paths;
    }

    public FlowGame(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.tiles = new Tile[this.ySize][this.xSize];
        this.paths = new ArrayList<>();

        for (int x = 0; x < this.ySize; x++) {
            for (int y = 0; y < this.xSize; y++) {
                this.tiles[y][x] = new Tile(this, x, y, Path.EMPTY, Type.NORMAL, 0);
            }
        }

        this.tiles[0][0].changeInto(Type.SYMBOL);
        this.tiles[this.ySize - 1][this.xSize - 1].changeInto(Type.SYMBOL);
    }

    public FlowGame(String filepath) {
        try {
            String read;
            int cur = 0;
            FileInputStream fis = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            Map<Integer, Integer> symbols = new HashMap<Integer, Integer>();

            read = br.readLine();
            String[] dimensions = read.split("x");
            this.xSize = Integer.valueOf(dimensions[0]);
            this.ySize = Integer.valueOf(dimensions[1]);
            this.tiles = new Tile[ySize][xSize];
            this.paths = new ArrayList<>();
            this.paths.add(new ArrayList<>());
            this.path = paths.getLast();

            while ((read = br.readLine()) != null) {
                for (int i = 0; i < read.length(); i++) {
                    char c = read.charAt(i);
                    Type type = c == '0' ? Type.NORMAL : Type.SYMBOL;
                    Path path = type == Type.NORMAL ? Path.EMPTY : Path.SYMBOL;
                    int value = Character.getNumericValue(c);
                    tiles[cur][i] = new Tile(this, i, cur, path, type, value);

                    if (type == Type.SYMBOL) {
                        symbols.merge(tiles[cur][i].getValue(), 1, (v1, v2) -> v1 + v2);
                    }
                }

                cur++;
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

    public void start(Tile tile) {
        if (tile.hasPath()) {
            List<Tile> path = tile.getGamePath();
            paths.remove(path);
            path.stream().forEach(t -> t.resetPath());
        }

        if (tile.isSymbol()) {
            paths.add(new ArrayList<>());
            path = paths.getLast();
            path.add(tile);
        }
    }

    public boolean isValidNext(Tile cur, Tile prev) {
        boolean entrable = cur.isEntrable(path.get(0));
        boolean neighbour = cur.isNeighbourWith(prev);
        boolean noPath = !cur.hasPath();
        return entrable && neighbour && noPath;
    }

    public void removeLast() {
        Tile last = path.getLast();
        if (!last.isSymbol())
            path.getLast().setPath(Path.EMPTY);
        path.removeLast();
        Tile tile = path.getLast();
        if (path.size() > 1)
            path.get(path.size() - 2).setFollowPath(tile);
    }

    public void addToPath(Tile tile) {
        this.path.add(tile);

        Tile prev = this.path.get(this.path.size() - 2);
        prev.setFollowPath(tile);
        if (tile.isSymbol())
            this.resolvePath(tile);
    }

    public void enter(Tile next) {
        if (hasNoPath())
            return;

        List<Tile> path = getPath();
        Tile last = path.getLast();
        Tile prev = path.size() >= 2 ? path.get(path.size() - 2) : null;

        if (next.equals(prev)) {
            removeLast();
        } else if (isValidNext(next, last)) {
            addToPath(next);
        }
    }

    public void resetPath() {
        path.stream().forEach(t -> t.resetPath());
        this.path = new ArrayList<>();
    }

    public void stop() {
        if (!hasNoPath())
            resetPath();
    }

    public void resolvePath(Tile last) {
        if (last.getGamePath() == path.get(0).getGamePath()) {
            paths.add(new ArrayList<>());
            this.path = paths.getLast();
        }
        if (this.isFull()) {
            endGame();
        }
    }

    public boolean hasNoPath() {
        return this.path.size() == 0;
    }

    public boolean isFull() {
        boolean isWon = true;

        for (Tile[] tiles : this.tiles) {
            if (!isWon)
                break;

            for (Tile tile : tiles) {
                if (!tile.hasPath()) {
                    isWon = false;
                    break;
                }
            }
        }

        return isWon;
    }

    public void endGame() {
        System.out.println("Congratz, you won !");
        System.exit(0);
    }

    public List<Tile> tilesInPath() {
        List<Tile> tiles = paths
                .stream()
                .flatMap(e -> e.stream())
                .collect(Collectors.toList());
        return tiles;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();

        sb.append("GridModel :\nSize: ");
        sb.append(this.xSize);
        sb.append("\n");
        for (int x = 0; x < this.xSize; x++) {
            for (int y = 0; y < this.xSize; y++) {
                sb.append(this.tiles[x][y]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
