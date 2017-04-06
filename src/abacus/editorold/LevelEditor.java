package abacus.editorold;

import java.io.IOException;

import abacus.editorold.gui.EditorWindow;
import abacus.editorold.tileset.EditorBasicTileSet;
import abacus.editorold.tileset.TileSet;

public class LevelEditor {

    private EditorTileMap map;
    private EditorWindow window;
    private int curLayer;
    private TileSet tilesets[];
    private int tileSize;
    
    public LevelEditor(EditorWindow window) {
        curLayer = 0;
        this.window = window;
        tilesets = new TileSet[256];
        tileSize = 64;
        
        try {
            SpriteSheet sheet = new SpriteSheet("res/tileset_01.png", 16, 16);
            setTileSet(0, new EditorBasicTileSet(sheet));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    public EditorWindow getWindow() {
        return window;
    }
    
    public void newMap(int width, int height) {
        map = new EditorTileMap(width, height);
        
        window.draw();
    }
    
    public EditorTileMap getMap() {
        return map;
    }
    
    public void setCurLayer(int layer) {
        curLayer = layer;
    }
    
    public int getCurLayer() {
        return curLayer;
    }
    
    public TileSet getTileSet(int index) {
        return tilesets[index];
    }
    
    public void setTileSet(int index, TileSet set) {
        tilesets[index] = set;
    }
    
    public int getTileSize() {
        return tileSize;
    }
    
    public void setTileSize(int size) {
        tileSize = size;
    }
    
}
