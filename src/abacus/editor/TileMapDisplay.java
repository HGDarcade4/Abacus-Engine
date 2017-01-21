package abacus.editor;

import java.awt.Graphics;

import javax.swing.JPanel;

import abacus.tile.TileMap;

@SuppressWarnings("serial")
public class TileMapDisplay extends JPanel {

    private TileMap map;
    private int tileSize;
    
    public TileMapDisplay(TileMap map) {
        setTileMap(map);
        setTileSize(64);
    }
    
    public TileMap getTileMap() {
        return map;
    }
    
    public void setTileMap(TileMap map) {
        this.map = map;
    }
    
    public int getTileSize() {
        return tileSize;
    }
    
    public void setTileSize(int size) {
        tileSize = size;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        
    }
    
}
