package abacus.editor.imageprovider;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;

import abacus.editor.TileMap;

public interface TileImageProvider {

    void getImages(TileMap map, int x, int y, int layer, BufferedImage images[]);
    
    int tilesWide();
    int tilesHigh();
    int getMeta(int tileX, int tileY);
    
    void getDisplayImages(int tileX, int tileY, BufferedImage images[]);
    
    void saveData(PrintWriter out);
}
