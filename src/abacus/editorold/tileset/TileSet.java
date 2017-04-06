package abacus.editorold.tileset;

import java.awt.image.BufferedImage;

import abacus.editorold.EditorTileMap;
import abacus.editorold.SpriteSheet;

public abstract class TileSet {

    protected SpriteSheet sheet;
    private boolean quad;
    
    public TileSet(SpriteSheet sheet, boolean quad) {
        this.sheet = sheet;
        this.quad = quad;
    }
    
    public boolean quadImage() { return quad; }
    
    public abstract BufferedImage getSprite(EditorTileMap map, int x, int y, int layer);
    
    public abstract BufferedImage[] getQuadSprite(EditorTileMap map, int x, int y, int layer);
    
    public SpriteSheet getSpriteSheet() {
        return sheet;
    }
    
}
