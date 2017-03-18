package abacus.editor;

import abacus.editor.gui.EditorWindow;

public class LevelEditor {

    private EditorTileMap map;
    private EditorWindow window;
    private int curLayer;
    
    public LevelEditor(EditorWindow window) {
        curLayer = 0;
        this.window = window;
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
    
}
