package abacus.tile;

import java.util.ArrayList;
import java.util.List;

import abacus.graphics.WorldRenderer;

public class TileMap {

    private int width, height;
    private List<TileMapLayer> layers;
    private boolean[] collision;
    
    private TileRegistry tiles;
    
    public TileMap(int width, int height, int layers) {
        this.width = width;
        this.height = height;
        this.layers = new ArrayList<>();
        for (int i = 0; i < layers; i++) {
            TileMapLayer layer = new TileMapLayer(width, height, i);
            this.layers.add(layer);
        }
        collision = new boolean[width * height];
        tiles = new TileRegistry();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    // animation updating
    public void update() {
        for (int i = 0; i < tiles.numTiles(); i++) {
            tiles.getTile(i).update();
        }
    }
    
    public TileMapLayer getTileMapLayer(int layer) {
        if (layer >= 0 && layer < layers.size()) {
            return layers.get(layer);
        }
        return null;
    }
    
    public void render(WorldRenderer r) {
        // TODO check camera bounds
        int minY = (int)Math.floor(r.getMinY()) - 4;
        int maxY = (int)Math.ceil(r.getMaxY()) + 1;
        int minX = (int)Math.floor(r.getMinX());
        int maxX = (int)Math.ceil(r.getMaxX()) + 1;
        
        for (int layer = 0; layer < layers.size(); layer++) {
            // draw from the top down, although it shouldn't actually matter for tiles, just characters
            for (int y = maxY; y >= minY; y--) {
                for (int x = minX; x < maxX; x++) {
                    r.setLayer(layer);
                    if (!getCollision(x, y)) getTile(x, y, layer).render(r, this, x, y, layer);
                }
            }
        }
    }
    
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    public boolean inBounds(int x, int y, int layer) {
        return x >= 0 && x < width && y >= 0 && y < height && layer >= 0 && layer < layers.size();
    }
    
    public int numLayers() {
        return layers.size();
    }
    
    public TileRegistry getTileRegistry() {
        return tiles;
    }
    
    public Tile getTile(int x, int y, int layer) {
        if (inBounds(x, y, layer)) {
            return tiles.getTile(layers.get(layer).getTileId(x, y));
        }
        return TileRegistry.NULL_TILE;
    }
    
    public int getTileId(int x, int y, int layer) {
        if (inBounds(x, y, layer)) {
            return layers.get(layer).getTileId(x, y);
        }
        return 0;
    }
    
    public int getTileMetaData(int x, int y, int layer) {
        if (inBounds(x, y, layer)) {
            return layers.get(layer).getTileMetaData(x, y);
        }
        return 0;
    }
    
    public void setTile(int x, int y, int layer, int tile, int metadata) {
        if (inBounds(x, y, layer)) {
            layers.get(layer).setTileId(x, y, tile);
            layers.get(layer).setTileMetaData(x, y, metadata);
        }
    }
    
    public void fillLayer(int layer, int tile, int metadata) {
        if (layer >= 0 && layer < layers.size()) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    layers.get(layer).setTileId(x, y, tile);
                    layers.get(layer).setTileMetaData(x, y, metadata);
                }
            }
        }
    }
    
    public boolean getCollision(int x, int y) {
        if (inBounds(x, y)) {
            return collision[x + y * width];
        }
        return true;
    }
    
    public void setCollision(int x, int y, boolean solid) {
        if (inBounds(x, y)) {
            collision[x + y * width] = solid;
        }
    }
}
