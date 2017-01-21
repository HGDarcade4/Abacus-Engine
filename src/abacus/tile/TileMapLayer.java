package abacus.tile;

public class TileMapLayer {

    private int width, height;
    private int[] tiles;
    private float layer;
    
    public TileMapLayer(int width, int height, float layer) {
        this.width = width;
        this.height = height;
        tiles = new int[width * height * 2];
        setLayer(layer);
    }
    
    public float getLayer() {
        return layer;
    }
    
    public void setLayer(float layer) {
        this.layer = layer;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public int getTileId(int x, int y) {
        return tiles[x + y * width];
    }
    
    public void setTileId(int x, int y, int id) {
        tiles[x + y * width] = id;
    }
    
    public int getTileMetaData(int x, int y) {
        return tiles[x + y * width + width * height];
    }
    
    public void setTileMetaData(int x, int y, int meta) {
        tiles[x + y * width + width * height] = meta;
    }
    
}
