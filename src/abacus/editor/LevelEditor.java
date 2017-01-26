package abacus.editor;

import java.util.ArrayList;

public class LevelEditor {

    private EditorTileMap map;
    private ArrayList<LevelListener> listeners;
    
    public LevelEditor() {
        listeners = new ArrayList<>();
    }
    
    public void addListener(LevelListener ll) {
        listeners.add(ll);
    }
    
    public void newMap(int width, int height) {
        map = new EditorTileMap(width, height);
        
        for (LevelListener ll : listeners) ll.onNewMap();
    }
    
}
