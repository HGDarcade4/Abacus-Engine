package abacus.editor;

import java.util.ArrayList;
import java.util.List;

public class TileMap {

    private int width, height;
    private List<Layer> layers;
    
    public TileMap(int width, int height) {
        this.width = width;
        this.height = height;
        layers = new ArrayList<>();
        addLayers(1);
    }
    
    public Layer getLayer(int layer) {
        return layers.get(layer);
    }
    
    public void addLayers(int amt) {
        for (int i = 0; i < amt; i++) {
            layers.add(new Layer(width, height));
        }
    }
    
    public int getLayerCount() {
        return layers.size();
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }

	public boolean inBounds(int x, int y, int layer) {
		return x >= 0 && x < width &&
			   y >= 0 && y < height && 
			   layer >= 0 && layer < layers.size();
	}
    
}
