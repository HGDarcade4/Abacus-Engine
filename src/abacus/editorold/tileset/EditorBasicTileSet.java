package abacus.editorold.tileset;

import java.awt.image.BufferedImage;

import abacus.editorold.EditorTileMap;
import abacus.editorold.SpriteSheet;

public class EditorBasicTileSet extends TileSet {

    public EditorBasicTileSet(SpriteSheet sheet) {
        super(sheet, false);
    }

    @Override
    public BufferedImage getSprite(EditorTileMap map, int x, int y, int layer) {
        int meta = map.getLayer(layer).getTile(x, y).tileMeta;
        
        return sheet.get(meta);
    }

    @Override
    public BufferedImage[] getQuadSprite(EditorTileMap map, int x, int y, int layer) {
        return null;
    }

}
