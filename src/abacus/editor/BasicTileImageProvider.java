package abacus.editor;

import java.awt.image.BufferedImage;

public class BasicTileImageProvider implements TileImageProvider {

    private SpriteSheet sheet;
    
    public BasicTileImageProvider(SpriteSheet sheet) {
        this.sheet = sheet;
    }
    
    @Override
    public BufferedImage[] getImages(TileMap map, int x, int y, int layer) {
        int meta = map.getLayer(layer).getTile(x, y).tileMeta;
        int sx = meta % (sheet.tilesWide() * 2);
        int sy = meta / (sheet.tilesHigh() * 2);
        return new BufferedImage[] {
                sheet.get(sx, sy), 
                sheet.get(sx + 1, sy), 
                sheet.get(sx, sy + 1), 
                sheet.get(sx + 1, sy + 1), 
        };
    }

}
