package abacus.editorold.gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import abacus.editorold.EditorTile;
import abacus.editorold.LevelEditor;
import abacus.editorold.tileset.TileSet;

@SuppressWarnings("serial")
public class TileMapPanel extends JPanel {
    
    private LevelEditor editor;
    
    public TileMapPanel(LevelEditor editor) {
        this.editor = editor;
    }
    
    public void paintComponent(Graphics g) {
        if (editor.getMap() == null) {
            return;
        }
        
        int size = editor.getTileSize();
        
        System.out.println("Tile map draw");
        
        for (int layer = 0; layer < editor.getMap().getLayerCount(); layer++) {
            for (int y = 0; y < editor.getMap().getHeight(); y++) {
                for (int x = 0; x < editor.getMap().getWidth(); x++) {
                    EditorTile tile = editor.getMap().getLayer(layer).getTile(x, y);
                    TileSet set = editor.getTileSet(tile.tileId);
                    if (set != null) {
                        if (set.quadImage()) {
                            BufferedImage images[] = set.getQuadSprite(editor.getMap(), x, y, layer);
                            if (images != null) {
                                g.drawImage(images[0], x, y, null);
                            }
                        }
                        else {
                            BufferedImage image = set.getSprite(editor.getMap(), x, y, layer);
                            if (image != null) {
                                g.drawImage(image, x * size, y * size, size - 1, size - 1, null);
                            }
                        }
                    }
                }
            }
        }
    }
    
}
