package abacus.editor;

import java.awt.image.BufferedImage;

public interface TileImageProvider {

    BufferedImage[] getImages(TileMap map, int x, int y, int layer);
    
}
