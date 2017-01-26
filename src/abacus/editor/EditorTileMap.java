package abacus.editor;

import java.util.ArrayList;
import java.util.List;

public class EditorTileMap {

    private int width, height;
    private List<Layer> layers;
    
    public EditorTileMap(int width, int height) {
        this.width = width;
        this.height = height;
        layers = new ArrayList<>();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
}
