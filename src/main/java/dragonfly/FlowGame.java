package dragonfly;

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
                this.tiles[y][x] = new Tile(this, x, y, TileType.EMPTY);
            }
        }

        this.tiles[0][0].setType(TileType.S1);
        this.tiles[this.ySize - 1][this.xSize - 1].setType(TileType.S1);
    }

    public FlowGame(String filepath) {
        try {
            String read;
            int cur = 0;
            FileInputStream fis = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            Map<TileType, Integer> symbols = new HashMap<TileType, Integer>();

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
                    int index = Character.getNumericValue(read.charAt(i));
                    TileType type = TileType.values()[index];
                    tiles[cur][i] = new Tile(this, i, cur, type);

                    if (type.isSymbol()) {
                        symbols.merge(type, 1, (v1, v2) -> v1 + v2);
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
            List<Tile> path = tile.getPath();
            paths.remove(path);
            path.stream().forEach(t -> {
                if (!t.isSymbol())
                    t.setType(TileType.EMPTY);
            });
        }

        if (tile.isSymbol()) {
            paths.add(new ArrayList<>());
            path = paths.getLast();
            path.add(tile);
        }
    }

    public boolean isValidNext(Tile cur, Tile prev) {
        boolean entrable = cur.getType().isEntrable(path.get(0).getType());
        boolean neighbour = cur.isNeighbourWith(prev);
        boolean noPath = !cur.hasPath();
        // System.out.println("entrable: " + entrable + ", neighbour: " + neighbour + ",
        // noPath: " + noPath);
        return entrable && neighbour && noPath;
    }

    public void removeLast() {
        Tile last = path.getLast();
        if (!last.isSymbol())
            path.getLast().setType(TileType.EMPTY);
        path.removeLast();
        Tile tile = path.getLast();
        tile.setType(tile.getCurrentType());
    }

    public void addToPath(Tile tile) {
        this.path.add(tile);

        Tile previousTile = this.path.get(this.path.size() - 2);

        tile.setType(tile.getCurrentType());
        previousTile.setType(tile.getPreviousType());

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
        for (Tile tile : path) {
            if (!tile.isSymbol())
                tile.setType(TileType.EMPTY);
        }

        this.path = new ArrayList<>();
    }

    public void stop() {
        if (!hasNoPath())
            resetPath();
    }

    public void resolvePath(Tile last) {
        if (last.getType() == path.get(0).getType()) {
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
