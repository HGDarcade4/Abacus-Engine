package abacus.editorold;

public class EditorLayer {

    private EditorTile[] tiles;
    private int width, height;
    
    public EditorLayer(int w, int h) {
        width = w;
        height = h;
        tiles = new EditorTile[width * height];
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new EditorTile();
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    public EditorTile getTile(int x, int y) {
        if (inBounds(x, y)) {
            return tiles[x + y * width];
        }
        
        return null;
    }
    
//    public void setTile(int x, int y, EditorTile tile) {
//        if (inBounds(x, y)) {
//            tiles[x + y * width] = tile;
//        }
//    }
    
}
