package abacus.editor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import abacus.editor.LevelEditor;
import abacus.editor.Tile;
import abacus.editor.TileMap;

public class TileMapPanel implements GuiComponent {

    public static final Color BACKGROUND = new Color(0xDDDDDD);
    
    private LevelEditor editor;
    private JPanel panel;
    private TileMap map = null;
    private int tileSize = 64;
    
    @SuppressWarnings("serial")
    public TileMapPanel(LevelEditor editor) {
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                draw(g);
            }
        };
        this.editor = editor;
    }
    
    public void draw(Graphics g) {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        
        if (map == null) return;
        
        int yStart = panel.getHeight() - tileSize;
        
        for (int layer = 0; layer < map.getLayerCount(); layer++) {
            for (int x = 0; x < map.getWidth(); x++) {
                for (int y = 0; y < map.getHeight(); y++) {
                    Tile tile = map.getLayer(layer).getTile(x, y);
                    if (editor.tileTypes[tile.tileId] != null) {
                        BufferedImage[] images = editor.tileTypes[tile.tileId].getImages(map, x, y, layer);
                        if (images != null) {
                            g.drawImage(
                                    images[0], 
                                    tileSize * x,
                                    yStart - (tileSize * y), 
                                    tileSize/2, 
                                    tileSize/2, 
                                    panel);
                            g.drawImage(
                                    images[1], 
                                    tileSize * x + tileSize/2,
                                    yStart - (tileSize * y), 
                                    tileSize/2 - 1, 
                                    tileSize/2, 
                                    panel);
                            g.drawImage(
                                    images[2], 
                                    tileSize * x,
                                    yStart - (tileSize * y) + tileSize/2, 
                                    tileSize/2, 
                                    tileSize/2 - 1, 
                                    panel);
                            g.drawImage(
                                    images[3], 
                                    tileSize * x + tileSize/2,
                                    yStart - (tileSize * y) + tileSize/2, 
                                    tileSize/2 - 1, 
                                    tileSize/2 - 1, 
                                    panel);
                        }
                    }
                    else {
                        g.setColor(Color.BLUE);
                        g.fillRect(
                                tileSize * x, 
                                tileSize * y, 
                                tileSize - 1, 
                                tileSize - 1);
                    }
                }
            }
        }
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }

    public void setMap(TileMap map) {
        this.map = map;
        updateSize();
    }
    
    public void updateSize() {
        if (map != null) {
            panel.setPreferredSize(new Dimension(
                    map.getWidth() * tileSize, 
                    map.getHeight() * tileSize));
            panel.revalidate();
        }
    }
    
}
